package ca.tetervak.mathtrainer.domain.model

data class StatusData(
    val numberOfProblems: Int,
    val rightAnswers: Int,
    val notAnswered: Int,
    val wrongAnswers: Int
) {
    val invalidInputs: Int =
        numberOfProblems - (rightAnswers + wrongAnswers + notAnswered)
    val answered: Int = rightAnswers + wrongAnswers + invalidInputs
}