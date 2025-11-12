package ca.tetervak.mathtrainer.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreService @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val currentUserId: String
        get() = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    fun quizzesCollection() = db.collection("users")
        .document(currentUserId)
        .collection("quizzes")


    suspend fun addQuiz(quiz: FirestoreQuiz): String {
        val ref = quizzesCollection().document()
        val data = quiz.copy(id = ref.id).toMap()
        ref.set(data).await()
        return ref.id
    }

    suspend fun getQuiz(quizId: String): FirestoreQuiz? {
        val snap = quizzesCollection().document(quizId).get().await()
        return snap.toObject(FirestoreQuiz::class.java)?.copy(id = snap.id)
    }

    private fun problemsCollection(quizId: String) =
        quizzesCollection().document(quizId).collection("problems")

    suspend fun addProblems(quizId: String, problems: List<FirestoreProblem>) {
        val batch = db.batch()
        problems.forEach { problem ->
            val ref = problemsCollection(quizId).document()
            batch.set(ref, problem.copy(id = ref.id))
        }
        batch.commit().await()
    }

    suspend fun getProblems(quizId: String): List<FirestoreProblem> {
        val snaps = problemsCollection(quizId)
            .orderBy("problemNumber", Query.Direction.ASCENDING)
            .get().await()
        return snaps.documents.mapNotNull { doc ->
            doc.toObject(FirestoreProblem::class.java)?.copy(id = doc.id)
        }
    }

    suspend fun updateUserAnswer(quizId: String, problemId: String, answer: Any) {
        problemsCollection(quizId).document(problemId)
            .update("userAnswer", answer).await()
    }

}