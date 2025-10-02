package ca.tetervak.mathtrainer.domain


interface Problem {
    val text: String
    fun checkAnswer(userAnswer: String): Grade

    enum class Grade {
        RIGHT_ANSWER, WRONG_ANSWER, INVALID_INPUT
    }
}