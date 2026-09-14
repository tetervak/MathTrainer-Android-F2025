package ca.tetervak.mathtrainer.domain.model

data class QuizScore(
    val problemCount: Int,
    val rightAnswers: Int
) {
    companion object {
        val Preview: QuizScore = QuizScore(
            problemCount = 5,
            rightAnswers = 4
        )
    }
}