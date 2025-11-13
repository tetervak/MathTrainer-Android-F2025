package ca.tetervak.mathtrainer.domain.model

import java.util.UUID

class Problem(
    val id: String = UUID.randomUUID().toString(),
    val quizId: String,
    val problemNumber: Int,
    val text: String,
    val correctAnswer: Int,
    val userAnswer: String? = null,
) {

    val answerStatus: AnswerStatus = AnswerStatus.getStatus(
        correctAnswer = correctAnswer,
        userAnswer = userAnswer
    )

    fun copy(userAnswer: String? = this.userAnswer) =
        Problem(
            id = id,
            quizId = quizId,
            problemNumber = problemNumber,
            text = text,
            correctAnswer = correctAnswer,
            userAnswer = userAnswer,
        )

    companion object{
        val Preview = Problem(
            quizId = "",
            problemNumber = 1,
            text = "2 + 2 = ?",
            correctAnswer = 4
        )
    }
}
