package ca.tetervak.mathtrainer.data.firestore

import ca.tetervak.mathtrainer.data.firestore.docs.ProblemDoc
import ca.tetervak.mathtrainer.data.firestore.docs.QuizDoc
import ca.tetervak.mathtrainer.data.firestore.docs.fromMap
import ca.tetervak.mathtrainer.data.firestore.docs.toMap
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val currentUserId: String
        get() = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    private fun userDoc() =
        db.collection("users").document(currentUserId)

    private fun quizzesCollection() =
        userDoc().collection("quizzes")

    private fun problemsCollection() =
        userDoc().collection("problems")

    private fun newId() = db.collection("temp").document().id


    // Create Quiz: uses a monotonic counter for quizNumber to ensure uniqueness
    suspend fun createQuiz(): String {
        val quizId = newId()
        val quizRef = quizzesCollection().document(quizId)
        val userDocRef = userDoc()

        return db.runTransaction { transaction ->
            val userSnap = transaction.get(userDocRef)

            // Get the current counter; the new number is one greater.
            val quizNumberCounter = userSnap.getLong("quizNumberCount") ?: 0L
            val newQuizNumber = (quizNumberCounter + 1).toInt()

            val quiz = QuizDoc(
                id = quizId,
                quizNumber = newQuizNumber,
                createdAt = Timestamp.now()
            )

            // In one atomic transaction:
            // 1. Create the new quiz
            transaction.set(quizRef, quiz.toMap())
            // 2. Atomically increment both counters on the user document
            transaction.update(
                userDocRef,
                "quizCount", FieldValue.increment(1),
                "quizNumberCount", FieldValue.increment(1)
            )

            quizId
        }.await()
    }


    suspend fun deleteQuizWithProblems(quizId: String) {
        // Read the problems to be deleted before the transaction
        val problemsToDelete = problemsCollection()
            .whereEqualTo("quizId", quizId)
            .get()
            .await()

        val quizRef = quizzesCollection().document(quizId)
        val userDocRef = userDoc()

        db.runTransaction { transaction ->
            val userSnap = transaction.get(userDocRef)
            val quizCount = userSnap.getLong("quizCount") ?: 0L

            // Delete the quiz and all its associated problems
            transaction.delete(quizRef)
            problemsToDelete.documents.forEach { transaction.delete(it.reference) }

            // If this is the last quiz, reset the counters. Otherwise, just decrement quizCount.
            if (quizCount <= 1) {
                transaction.update(
                    userDocRef,
                    mapOf(
                        "quizCount" to 0,
                        "quizNumberCount" to 0
                    )
                )
            } else {
                transaction.update(
                    userDocRef,
                    "quizCount",
                    FieldValue.increment(-1)
                )
            }
        }.await()
    }


    suspend fun createQuizWithProblems(problems: List<ProblemDoc>): String {
        val quizId = newId()
        val quizRef = quizzesCollection().document(quizId)
        val userDocRef = userDoc()

        return db.runTransaction { transaction ->
            val userSnap = transaction.get(userDocRef)

            // Get the current counter; the new number is one greater.
            val quizNumberCounter = userSnap.getLong("quizNumberCount") ?: 0L
            val newQuizNumber = (quizNumberCounter + 1).toInt()

            val quiz = QuizDoc(
                id = quizId,
                quizNumber = newQuizNumber,
                problemCount = problems.size,
                createdAt = Timestamp.now()
            )

            // In one atomic transaction:
            // 1. Create the new quiz
            transaction.set(quizRef, quiz.toMap())
            // 2. Atomically increment both counters on the user document
            transaction.update(
                userDocRef,
                "quizCount", FieldValue.increment(1),
                "quizNumberCount", FieldValue.increment(1)
            )
            // 3. Add the problems to the new quiz
            problems.forEachIndexed { index, problem ->
                val problemId = newId()
                val problemRef = problemsCollection().document(problemId)
                val fullProblem = problem.copy(
                    id = problemId,
                    problemNumber = index + 1,  // 1-based
                    quizId = quizId,
                    userId = currentUserId,
                    updatedAt = Timestamp.now()
                )
                transaction.set(problemRef, fullProblem.toMap())
            }
            quizId
        }.await()
    }


    fun getUserQuizzesFlow(): Flow<List<QuizDoc>> =
        quizzesCollection()
            .orderBy("quizNumber", Query.Direction.ASCENDING)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.mapNotNull { doc ->
                    doc.data?.let { data ->
                        QuizDoc.fromMap(quizId = doc.id, data = data)
                    }
                }
            }


    fun getUserQuizCountFlow(): Flow<Int> =
        userDoc().snapshots().map { snapshot ->
            snapshot.getLong("quizCount")?.toInt() ?: 0
        }


    fun getQuizByIdFlow(quizId: String): Flow<QuizDoc?> =
        quizzesCollection().document(quizId).snapshots().map { snapshot ->
            snapshot.data?.let { data ->
                QuizDoc.fromMap(quizId = quizId, data = data)
            }
        }


    suspend fun getQuizNumber(quizId: String): Int =
        quizzesCollection()
            .document(quizId)
            .get()
            .await()
            .getLong("quizNumber")?.toInt() ?: 0


    suspend fun getQuizProblemCount(quizId: String): Int =
        quizzesCollection()
            .document(quizId)
            .get()
            .await()
            .getLong("problemCount")?.toInt() ?: 0

    fun getQuizProblemCountFlow(quizId: String): Flow<Int> =
        quizzesCollection()
            .document(quizId)
            .snapshots()
            .map { snapshot ->
                snapshot.getLong("problemCount")?.toInt() ?: 0
            }


    suspend fun getQuizById(quizId: String): QuizDoc? =
        quizzesCollection()
            .document(quizId)
            .get()
            .await()
            .data?.let { data ->
                QuizDoc.fromMap(quizId = quizId, data = data)
            }


    fun getQuizProblemsFlow(quizId: String): Flow<List<ProblemDoc>> =
        problemsCollection()
            .whereEqualTo("quizId", quizId)
            .orderBy("problemNumber", Query.Direction.ASCENDING)
            .snapshots().map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    doc.data?.let { data ->
                        ProblemDoc.fromMap(problemId = doc.id, data = data)
                    }
                }
            }


    fun getQuizProblemCountByStatusFlow(quizId: String, answerStatus: AnswerStatus): Flow<Int> =
        problemsCollection()
            .whereEqualTo("quizId", quizId)
            .whereEqualTo("answerStatus", answerStatus.name)
            .snapshots().map { snapshot ->
                snapshot.size()
            }


    suspend fun getQuizProblemCountByStatus(quizId: String, answerStatus: AnswerStatus): Int =
        problemsCollection()
            .whereEqualTo("quizId", quizId)
            .whereEqualTo("answerStatus", answerStatus.name)
            .get()
            .await()
            .size()


    suspend fun getProblemById(problemId: String): ProblemDoc? =
        problemsCollection()
            .document(problemId)
            .get()
            .await()
            .data?.let { data ->
                ProblemDoc.fromMap(problemId = problemId, data = data)
            }


    fun getProblemByIdFlow(problemId: String): Flow<ProblemDoc?> =
        problemsCollection()
            .document(problemId)
            .snapshots()
            .map { snapshot ->
                snapshot.data?.let { data ->
                    ProblemDoc.fromMap(problemId = problemId, data = data)
                }
            }


    suspend fun getQuizProblemByNumber(quizId: String, problemNumber: Int): ProblemDoc? =
        problemsCollection()
            .whereEqualTo("quizId", quizId)
            .whereEqualTo("problemNumber", problemNumber)
            .limit(1)
            .get()
            .await()
            .documents
            .firstOrNull()?.let { doc ->
                ProblemDoc.fromMap(problemId = doc.id, data = doc.data!!)
            }

    suspend fun updateUserAnswerAndAnswerStatus(
        problemId: String,
        userAnswer: String,
        answerStatus: AnswerStatus
    ) {
        problemsCollection().document(problemId)
            .update(
                "userAnswer", userAnswer,
                "answerStatus", answerStatus.name,
                "updatedAt", FieldValue.serverTimestamp()
            )
            .await()
    }

}
