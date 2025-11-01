package ca.tetervak.mathtrainer.domain

import kotlin.math.abs

class AlgebraProblem(
    val a: Int, // the first number in the problem
    val b: Int, // the second number in the problem
    val op: AlgebraOperation // the binary operator between these numbers
) {
    val text: String = "$a ${op.symbol} $b = ?"
    val answer: Int = op.calculate(a, b)

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