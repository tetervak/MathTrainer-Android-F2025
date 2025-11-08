package ca.tetervak.mathtrainer.domain.model

import java.util.UUID

class Problem(
    val problem: AlgebraProblem,
    val userAnswer: String? = null,
    val id: String = UUID.randomUUID().toString(),
    val order: Int,
    val quizId: String
) {

    val text: String
        get() = problem.text

    val status: UserAnswerStatus = problem.checkAnswer(userAnswer = userAnswer)

    fun copy(userAnswer: String? = this.userAnswer) =
        Problem(problem = problem,
            userAnswer = userAnswer,
            id = id,
            order = order,
            quizId = quizId
        )

}