package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.data.AlgebraProblemFactory
import ca.tetervak.mathtrainer.data.ProblemFactory

class Quiz (
    val numberOfProblems: Int = 5,
    val problemFactory: ProblemFactory = AlgebraProblemFactory()
) {
    init {
        if (numberOfProblems < 1)
            throw IllegalArgumentException("The number of problems $numberOfProblems < 1")
    }

    var quizProblem: QuizProblem = makeNewQuizProblem()
    private set

    var problemNumber: Int = 1
    private set

    var quizEnded: Boolean = false
    private set

    var score: Int = 0
    private set

    fun submitAnswer(userAnswer: String){
        quizProblem.userAnswer = userAnswer
        if(quizProblem.status == QuizProblem.Status.RIGHT_ANSWER){
            nextProblemOrEnd()
            score++
        }
    }

    fun skipProblem(){
        nextProblemOrEnd()
    }

    fun reset(){
        quizProblem = makeNewQuizProblem()
        score = 0
        problemNumber = 1
        quizEnded = false
    }

    private fun nextProblemOrEnd() {
        if (problemNumber == numberOfProblems) {
            quizEnded = true
        } else {
            quizProblem = makeNewQuizProblem()
            problemNumber++
        }
    }

    private fun makeNewQuizProblem(): QuizProblem =
        QuizProblem(problem = problemFactory.createRandomProblem())
}