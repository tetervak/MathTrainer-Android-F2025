package ca.tetervak.mathtrainer.domain.model

data class Problem(
    val id: String,
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

    companion object{
        val Preview = Problem(
            id = "",
            quizId = "",
            problemNumber = 1,
            text = "2 + 2 = ?",
            correctAnswer = 4
        )
    }
}
