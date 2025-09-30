package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.data.AlgebraProblemFactory
import ca.tetervak.mathtrainer.data.ProblemFactory

class UserQuiz (
    val numberOfProblems: Int = 5,
    val problemFactory: ProblemFactory = AlgebraProblemFactory()
) {
    init {
        if (numberOfProblems < 1)
            throw IllegalArgumentException("The number of problems $numberOfProblems < 1")

    }

    var userProblem: UserProblem = makeNewQuizProblem()
    private set

    var problemNumber: Int = 1
    private set

    var quizEnded: Boolean = false
    private set

    var score: Int = 0
    private set

    fun submitAnswer(userAnswer: String){
        userProblem = userProblem.copy(userAnswer)
        if(userProblem.status == UserProblem.Status.RIGHT_ANSWER){
            nextProblemOrEnd()
            score++
        }
    }

    fun skipProblem(){
        nextProblemOrEnd()
    }

    fun reset(){
        userProblem = makeNewQuizProblem()
        score = 0
        problemNumber = 1
        quizEnded = false
    }

    private fun nextProblemOrEnd() {
        if (problemNumber == numberOfProblems) {
            quizEnded = true
        } else {
            userProblem = makeNewQuizProblem()
            problemNumber++
        }
    }

    private fun makeNewQuizProblem(): UserProblem =
        UserProblem(problem = problemFactory.createRandomProblem())
}