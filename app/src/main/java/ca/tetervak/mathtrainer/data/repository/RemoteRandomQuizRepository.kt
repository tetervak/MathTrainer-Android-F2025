package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.remote.AlgebraQuiz
import ca.tetervak.mathtrainer.data.remote.AlgebraQuizProblem
import ca.tetervak.mathtrainer.data.remote.RandomQuizApi
import ca.tetervak.mathtrainer.domain.AlgebraOperation
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRandomQuizRepository @Inject constructor(
    private val randomQuizApi: RandomQuizApi
) {

    suspend fun getRandomQuizProblems(numberOfProblems: Int): List<AlgebraProblem> =
        withContext(Dispatchers.IO) {
            val quiz: AlgebraQuiz = randomQuizApi.getRandomQuiz(numberOfProblems)
            val problems: List<AlgebraQuizProblem> = quiz.problems
            problems.map { problem -> problem.toAlgebraProblem() }
        }

    private fun AlgebraQuizProblem.toAlgebraProblem(): AlgebraProblem {
        val parts: List<String> = this.text.split(" ", "=", limit = 4)
        val a: Int = parts[0].toInt()
        val op: AlgebraOperation = AlgebraOperation.fromSymbol(symbol = parts[1].first())
        val b: Int = parts[2].toInt()
        return AlgebraProblem(a = a, b = b, op = op)
    }
}