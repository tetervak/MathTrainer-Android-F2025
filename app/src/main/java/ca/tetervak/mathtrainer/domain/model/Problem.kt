package ca.tetervak.mathtrainer.domain.model

import java.util.UUID

class Problem(
    val id: String = UUID.randomUUID().toString(),
    val quizId: String,
    val problemNumber: Int,
    val algebraProblem: AlgebraProblem,
    val userAnswer: String? = null,
) {

    val text: String
        get() = algebraProblem.text

    val status: AnswerStatus = AnswerStatus.getStatus(
        correctAnswer = algebraProblem.answer,
        userAnswer = userAnswer
    )

    fun copy(userAnswer: String? = this.userAnswer) =
        Problem(algebraProblem = algebraProblem,
            userAnswer = userAnswer,
            id = id,
            problemNumber = problemNumber,
            quizId = quizId
        )

}