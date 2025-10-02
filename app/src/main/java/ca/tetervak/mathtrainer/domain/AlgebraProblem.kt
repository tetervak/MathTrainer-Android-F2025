package ca.tetervak.mathtrainer.domain

import kotlin.math.abs

sealed class AlgebraProblem : Problem {

    protected abstract val answer: Int

    override fun checkAnswer(userAnswer: String): Problem.Grade =
        try {
            val entered: Double = userAnswer.toDouble()
            if (abs(answer - entered) <= TOLERANCE) {
                Problem.Grade.RIGHT_ANSWER
            } else {
                Problem.Grade.WRONG_ANSWER
            }
        } catch (_: NumberFormatException) {
            Problem.Grade.INVALID_INPUT
        }

    companion object {
        const val TOLERANCE: Double = 0.000001
    }
}

data class AdditionProblem(val a: Int, val b: Int) : AlgebraProblem() {
    public override val answer: Int = a + b
    override val text: String = "$a + $b = ?"
}

data class SubtractionProblem(val a: Int, val b: Int) : AlgebraProblem() {
    public override val answer: Int = a - b
    override val text: String = "$a - $b = ?"
}

data class MultiplicationProblem(val a: Int, val b: Int) : AlgebraProblem() {
    public override val answer: Int = a * b
    override val text: String = "$a x $b = ?"
}

data class DivisionProblem(val a: Int, val b: Int) : AlgebraProblem() {
    public override val answer: Int = a / b
    override val text: String = "$a / $b = ?"
}