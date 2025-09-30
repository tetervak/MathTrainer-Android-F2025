package ca.tetervak.mathtrainer.domain

class UserProblem(
    val problem: Problem,
    val userAnswer: String? = null,
    val id: Int = 0,
) {

    val text: String
        get() = problem.text

    val status: Status = if (userAnswer != null) {
        when (problem.checkAnswer(userAnswer = userAnswer)) {
            Problem.Grade.RIGHT_ANSWER -> Status.RIGHT_ANSWER
            Problem.Grade.WRONG_ANSWER -> Status.WRONG_ANSWER
            Problem.Grade.INVALID_INPUT -> Status.INVALID_INPUT
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