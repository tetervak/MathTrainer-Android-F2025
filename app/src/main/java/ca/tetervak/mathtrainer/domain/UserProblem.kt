package ca.tetervak.mathtrainer.domain

class UserProblem(val problem: Problem, val id: Int = 0) {

    val text: String
        get() = problem.text

    var userAnswer: String? = null
        set(value) {
            field = value
            status =
                if (value != null) {
                    when (problem.checkAnswer(userAnswer = value)) {
                        Problem.Grade.RIGHT_ANSWER -> Status.RIGHT_ANSWER
                        Problem.Grade.WRONG_ANSWER -> Status.WRONG_ANSWER
                        Problem.Grade.INVALID_INPUT -> Status.INVALID_INPUT
                    }
                } else {
                    Status.NOT_ANSWERED
                }
        }

    var status: Status = Status.NOT_ANSWERED

    fun reset(){
        userAnswer = null
    }

    override fun toString(): String {
        return "QuizProblem(problem=$problem, userAnswer=${userAnswer}, status=$status)"
    }

    enum class Status {
        NOT_ANSWERED, RIGHT_ANSWER, WRONG_ANSWER, INVALID_INPUT
    }
}