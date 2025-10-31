package ca.tetervak.mathtrainer.domain

import kotlin.math.abs

sealed class AlgebraProblem {

    abstract val text: String
    abstract val answer: Int

    fun checkAnswer(userAnswer: String?): UserAnswerStatus =
        if (userAnswer == null) {
            UserAnswerStatus.NOT_ANSWERED
        } else {
            try {
                val entered: Double = userAnswer.toDouble()
                if (abs(answer - entered) <= TOLERANCE) {
                    UserAnswerStatus.RIGHT_ANSWER
                } else {
                    UserAnswerStatus.WRONG_ANSWER
                }
            } catch (_: NumberFormatException) {
                UserAnswerStatus.INVALID_INPUT
            }
        }

    companion object {
        const val TOLERANCE: Double = 0.000001
    }
}

data class AdditionProblem(val a: Int, val b: Int) : AlgebraProblem() {
    override val answer: Int = a + b
    override val text: String = "$a + $b = ?"
}

data class SubtractionProblem(val a: Int, val b: Int) : AlgebraProblem() {
    override val answer: Int = a - b
    override val text: String = "$a - $b = ?"
}

data class MultiplicationProblem(val a: Int, val b: Int) : AlgebraProblem() {
    override val answer: Int = a * b
    override val text: String = "$a x $b = ?"
}

data class DivisionProblem(val a: Int, val b: Int) : AlgebraProblem() {
    override val answer: Int = a / b
    override val text: String = "$a / $b = ?"
}