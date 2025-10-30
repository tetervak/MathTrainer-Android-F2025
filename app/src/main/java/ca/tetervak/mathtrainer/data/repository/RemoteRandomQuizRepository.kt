package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.remote.AlgebraQuiz
import ca.tetervak.mathtrainer.data.remote.AlgebraQuizProblem
import ca.tetervak.mathtrainer.data.remote.RandomQuizApi
import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.DivisionProblem
import ca.tetervak.mathtrainer.domain.MultiplicationProblem
import ca.tetervak.mathtrainer.domain.SubtractionProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRandomQuizRepository @Inject constructor(
    private val randomQuizApi: RandomQuizApi
): RandomQuizRepository {

    override suspend fun getRandomQuizProblems(numberOfProblems: Int): List<AlgebraProblem> =
        withContext(Dispatchers.IO) {
            val quiz: AlgebraQuiz = randomQuizApi.getRandomQuiz(numberOfProblems)
            val problems: List<AlgebraQuizProblem> = quiz.problems
            problems.map { problem -> problem.toAlgebraProblem() }
        }

    private fun AlgebraQuizProblem.toAlgebraProblem(): AlgebraProblem {
        val parts: List<String> = this.text.split(" ", "=", limit = 4)
        val op: Char = parts[1].first()
        val a: Int = parts[0].toInt()
        val b: Int = parts[2].toInt()
        return when(op){
            '+' -> AdditionProblem(a, b)
            '-' -> SubtractionProblem(a, b)
            'x' -> MultiplicationProblem(a, b)
            '/' -> DivisionProblem(a, b)
            else -> throw IllegalArgumentException("Unknown operator: $op")
        }
    }
}