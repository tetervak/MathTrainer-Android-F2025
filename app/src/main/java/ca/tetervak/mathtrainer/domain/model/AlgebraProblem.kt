package ca.tetervak.mathtrainer.domain.model

import kotlin.math.abs

class AlgebraProblem(
    val firstNumber: Int, // the first number in the problem
    val secondNumber: Int, // the second number in the problem
    val algebraOperation: AlgebraOperation // the binary operator between these numbers
) {
    val text: String = "$firstNumber ${algebraOperation.symbol} $secondNumber = ?"
    val answer: Int = algebraOperation.calculate(firstNumber, secondNumber)

    fun checkAnswer(userAnswer: String?): AnswerStatus =
        if (userAnswer == null) {
            AnswerStatus.NOT_ANSWERED
        } else {
            try {
                val entered: Double = userAnswer.toDouble()
                if (abs(answer - entered) <= TOLERANCE) {
                    AnswerStatus.RIGHT_ANSWER
                } else {
                    AnswerStatus.WRONG_ANSWER
                }
            } catch (_: NumberFormatException) {
                AnswerStatus.INVALID_INPUT
            }
        }

    companion object {
        const val TOLERANCE: Double = 0.000001
    }
}

fun AlgebraProblem.Companion.fromText(text: String): AlgebraProblem {
    val parts: List<String> = text.split(" ", "=", limit = 4)
    val a: Int = parts[0].toInt()
    val op: AlgebraOperation = AlgebraOperation.fromSymbol(symbol = parts[1].first())
    val b: Int = parts[2].toInt()
    return AlgebraProblem(firstNumber = a, secondNumber = b, algebraOperation = op)
}