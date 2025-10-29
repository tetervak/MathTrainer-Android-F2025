package ca.tetervak.mathtrainer.domain

class UserProblem(
    val problem: AlgebraProblem,
    val userAnswer: String? = null,
    val id: Int = 0,
) {

    val text: String
        get() = problem.text

    val status: Status = if (userAnswer != null) {
        when (problem.checkAnswer(userAnswer = userAnswer)) {
            AlgebraProblem.Grade.RIGHT_ANSWER -> Status.RIGHT_ANSWER
            AlgebraProblem.Grade.WRONG_ANSWER -> Status.WRONG_ANSWER
            AlgebraProblem.Grade.INVALID_INPUT -> Status.INVALID_INPUT
        }
    } else {
        Status.NOT_ANSWERED
    }

    fun copy(userAnswer: String? = this.userAnswer) =
        UserProblem(problem = problem, userAnswer = userAnswer, id = id)

    enum class Status {
        NOT_ANSWERED, RIGHT_ANSWER, WRONG_ANSWER, INVALID_INPUT
    }
}