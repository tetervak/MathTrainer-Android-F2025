package ca.tetervak.mathtrainer.domain.model

class UserProblem(
    val problem: AlgebraProblem,
    val userAnswer: String? = null,
    val id: Int = 0,
) {

    val text: String
        get() = problem.text

    val status: UserAnswerStatus = problem.checkAnswer(userAnswer = userAnswer)

    fun copy(userAnswer: String? = this.userAnswer) =
        UserProblem(problem = problem, userAnswer = userAnswer, id = id)

}