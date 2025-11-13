package ca.tetervak.mathtrainer.data.firebase

import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    fun quizzesCollection() = db.collection("users")
        .document(currentUserId)
        .collection("quizzes")

    fun problemsCollection() = db.collection("users")
        .document(currentUserId)
        .collection("problems")

    fun getUserQuizzesFlow(): Flow<List<FirestoreQuiz>> = callbackFlow {
        val query = quizzesCollection()
            .orderBy("quizNumber", Query.Direction.ASCENDING)
        val listenerRegistration = query.addSnapshotListener { querySnapshot, firestoreException ->
            if (firestoreException != null) {
                close(firestoreException)
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                return@addSnapshotListener
            }
            val quizzes = querySnapshot.documents.mapNotNull { doc ->
                FirestoreQuiz.fromMap(quizId = doc.id, data = doc.data!!)
            }
            trySend(quizzes)
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun getQuizByIdFlow(quizId: String): Flow<FirestoreQuiz?> = callbackFlow {
        val docRef = quizzesCollection().document(quizId)
        val listenerRegistration = docRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            if (snapshot == null || !snapshot.exists()) {
                trySend(null)
                return@addSnapshotListener
            }
            trySend(FirestoreQuiz.fromMap(quizId = quizId, data = snapshot.data!!))
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun getUserQuizCountFlow(): Flow<Int> = callbackFlow {
        val query = quizzesCollection()
        val listenerRegistration = query.addSnapshotListener { querySnapshot, firestoreException ->
            if (firestoreException != null) {
                close(firestoreException)
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                trySend(0)
                return@addSnapshotListener
            }
            val count = querySnapshot.size()
            trySend(count)
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    suspend fun getQuizNumber(quizId: String): Int {
        val docSnap: DocumentSnapshot =
            quizzesCollection().document(quizId).get().await()
        return docSnap.getLong("quizNumber")?.toInt() ?: 0
    }

    suspend fun getMaxQuizNumber(): Int {
        val query = quizzesCollection()
            .orderBy("quizNumber", Query.Direction.DESCENDING)
            .limit(1)
        val querySnapshot = query.get().await()
        return if (!querySnapshot.isEmpty) {
            val docSnapshot = querySnapshot.documents.first()
            docSnapshot.getLong("quizNumber")?.toInt() ?: 0
        } else 0
    }

    suspend fun getQuizById(quizId: String): FirestoreQuiz? {
        val docSnap: DocumentSnapshot =
            quizzesCollection().document(quizId).get().await()
        return if (docSnap.exists()) {
            FirestoreQuiz.fromMap(quizId = quizId, data = docSnap.data!!)
        } else null
    }

    fun getQuizProblemsFlow(quizId: String): Flow<List<FirestoreProblem>> = callbackFlow {
        val query = problemsCollection()
            .whereEqualTo("quizId", quizId)
            .orderBy("problemNumber", Query.Direction.ASCENDING)
        val listenerRegistration = query.addSnapshotListener { querySnapshot, firestoreException ->
            if (firestoreException != null) {
                close(firestoreException)
                return@addSnapshotListener
            }
            if (querySnapshot == null) {
                return@addSnapshotListener
            }
            val problems = querySnapshot.documents.mapNotNull { doc ->
                FirestoreProblem.fromMap(problemId = doc.id, data = doc.data!!)
            }
            trySend(problems)
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    fun getQuizProblemCountByStatusFlow(quizId: String, status: AnswerStatus): Flow<Int> =
        callbackFlow {
            val query = problemsCollection()
                .whereEqualTo("quizId", quizId)
                .whereEqualTo("answerStatus", status.toString())
            val listenerRegistration =
                query.addSnapshotListener { querySnapshot, firestoreException ->
                    if (firestoreException != null) {
                        close(firestoreException)
                        return@addSnapshotListener
                    }
                    if (querySnapshot == null) {
                        trySend(0)
                        return@addSnapshotListener
                    }
                    val count = querySnapshot.size()
                    trySend(count)
                }
            awaitClose {
                listenerRegistration.remove()
            }
        }

    suspend fun getQuizProblemCountByStatus(quizId: String, status: AnswerStatus): Int {
        val aggregateQuery = problemsCollection()
            .whereEqualTo("quizId", quizId)
            .whereEqualTo("answerStatus", status.toString()).count()
        val snapshot = aggregateQuery.get(AggregateSource.SERVER).await()
        return snapshot.count.toInt()
    }

    suspend fun getProblemById(problemId: String): FirestoreProblem? {
        val docSnap: DocumentSnapshot =
            problemsCollection().document(problemId).get().await()
        return if (docSnap.exists()) {
            FirestoreProblem.fromMap(problemId = problemId, data = docSnap.data!!)
        } else null
    }

    fun getProblemByIdFlow(problemId: String): Flow<FirestoreProblem?> = callbackFlow {
        val docRef = problemsCollection().document(problemId)
        val listenerRegistration = docRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            if (snapshot == null || !snapshot.exists()) {
                trySend(null)
                return@addSnapshotListener
            }
            trySend(FirestoreProblem.fromMap(problemId = problemId, data = snapshot.data!!))
        }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    suspend fun getQuizProblemByNumber(quizId: String, problemNumber: Int): FirestoreProblem? {
        val query = problemsCollection()
            .whereEqualTo("quizId", quizId)
            .whereEqualTo("problemNumber", problemNumber)
            .limit(1)
        val querySnapshot = query.get().await()
        return if (!querySnapshot.isEmpty) {
            val docSnapshot = querySnapshot.documents.first()
            FirestoreProblem.fromMap(problemId = docSnapshot.id, data = docSnapshot.data!!)
        } else null
    }

    suspend fun insertQuiz(quiz: FirestoreQuiz): String {
        val docRef: DocumentReference = quizzesCollection().document()
        val data = quiz.toMap()
        docRef.set(data).await()
        return docRef.id
    }

    suspend fun addQuizWithProblems(problems: List<FirestoreProblem>): String {
        // --- Manual Transaction Logic ---
        // This pattern avoids the ambiguous runTransaction extension function
        // by performing the read and the batched write sequentially.

        // 1. READ PHASE: Get the data needed for the write.
        // We do this first to minimize the chance of a race condition.
        val maxQuizNumber = getMaxQuizNumber()
        val newQuizNumber = maxQuizNumber + 1

        // 2. PREPARE THE WRITES (in-memory)
        val newQuizDocRef = quizzesCollection().document()
        val newQuiz = FirestoreQuiz(
            quizNumber = newQuizNumber,
            problemCount = problems.size
        )

        // 3. WRITE PHASE: Use a WriteBatch for an atomic multi-write operation.
        // A WriteBatch is like a mini-transaction for writes only.
        db.runBatch { batch ->
            // Add the new quiz to the batch
            batch.set(newQuizDocRef, newQuiz.toMap())

            // Add all the new problems to the batch
            problems.forEach { problem ->
                val newProblemDocRef = problemsCollection().document()
                val problemWithQuizId = problem.copy(id = newQuizDocRef.id)
                batch.set(newProblemDocRef, problemWithQuizId)
            }

            // Add the user's quizCount increment to the batch
            val userDocRef = db.collection("users").document(currentUserId)
            batch.update(userDocRef, "quizCount", FieldValue.increment(1))

        }.await() // Commit the atomic batch write and wait for it to complete.

        // 4. RETURN the ID of the new quiz upon success.
        return newQuizDocRef.id
    }

    suspend fun deleteQuizWithProblems(quizId: String){
        // 1. Find all problem documents that belong to the given quizId.
        val problemsQuery = problemsCollection()
            .whereEqualTo("quizId", quizId)
            .get()
            .await()

        // 2. Use a WriteBatch to perform all deletions as a single atomic operation.
        db.runBatch { batch ->
            // Add all the found problem documents to the batch for deletion.
            for (problemDoc in problemsQuery.documents) {
                batch.delete(problemDoc.reference)
            }

            // Add the main quiz document to the batch for deletion.
            val quizDocRef = quizzesCollection().document(quizId)
            batch.delete(quizDocRef)

            // Decrement the total quiz count on the user's document.
            val userDocRef = db.collection("users").document(currentUserId)
            batch.update(userDocRef, "quizCount", FieldValue.increment(-1))

        }.await() // Commit the batch and wait for it to complete.
    }

}

