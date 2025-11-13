package ca.tetervak.mathtrainer.domain.model

import kotlin.math.abs

enum class AnswerStatus {
    NOT_ANSWERED, RIGHT_ANSWER, WRONG_ANSWER, INVALID_INPUT;

    companion object {

        private const val TOLERANCE: Double = 0.000001

        fun getStatus(correctAnswer: Int, userAnswer: String?): AnswerStatus =
            if (userAnswer == null) {
                NOT_ANSWERED
            } else {
                try {
                    val entered: Double = userAnswer.toDouble()
                    if (abs(correctAnswer - entered) <= TOLERANCE) {
                        RIGHT_ANSWER
                    } else {
                        WRONG_ANSWER
                    }
                } catch (_: NumberFormatException) {
                    INVALID_INPUT
                }
            }
    }

}