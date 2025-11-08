package ca.tetervak.mathtrainer.domain.model

data class QuizStatus(
    val problemCount: Int,
    val rightAnswers: Int,
    val notAnswered: Int,
    val wrongAnswers: Int
) {
    val invalidInputs: Int =
        problemCount - (rightAnswers + wrongAnswers + notAnswered)
    val answeredCount: Int = rightAnswers + wrongAnswers + invalidInputs
}