package ca.tetervak.mathtrainer.data.firebase

import com.google.firebase.Timestamp
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val firestore: FirestoreService
) {

    suspend fun createQuiz(title: String, problems: List<FirestoreProblem>): String {
        // 1. Create quiz doc
        val quiz = FirestoreQuiz(
            id = "",
            quizNumber = 0,
            problemCount = problems.size,
            createdAt = Timestamp.now()
        )
        val quizId = firestore.addQuiz(quiz)

        // 2. Add problems (sub-collection)
        firestore.addProblems(quizId, problems.mapIndexed { i, p ->
            p.copy(problemNumber = i + 1)
        })
        return quizId
    }


}