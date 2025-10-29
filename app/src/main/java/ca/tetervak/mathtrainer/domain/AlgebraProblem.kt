package ca.tetervak.mathtrainer.domain

import kotlin.math.abs

sealed class AlgebraProblem {

    abstract val text: String
    abstract val answer: Int

    enum class Grade {
        RIGHT_ANSWER, WRONG_ANSWER, INVALID_INPUT
    }

    fun checkAnswer(userAnswer: String): Grade =
        try {
            val entered: Double = userAnswer.toDouble()
            if (abs(answer - entered) <= TOLERANCE) {
                Grade.RIGHT_ANSWER
            } else {
                Grade.WRONG_ANSWER
            }
        } catch (_: NumberFormatException) {
            Grade.INVALID_INPUT
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