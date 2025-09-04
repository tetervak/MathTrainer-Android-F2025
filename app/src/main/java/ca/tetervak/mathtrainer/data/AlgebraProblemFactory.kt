package ca.tetervak.mathtrainer.data

import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.domain.Problem
import ca.tetervak.mathtrainer.domain.SubtractionProblem
import kotlin.random.Random

class AlgebraProblemFactory(
    private val random: Random = Random.Default
): ProblemFactory {

    override fun createRandomProblem(): Problem =
        if (random.nextBoolean())
            createRandomAdditionProblem()
        else
            createRandomSubtractionProblem()

    private fun createRandomAdditionProblem(): AdditionProblem{
        val larger = getRandomLargerValue()
        val smaller = getRandomSmallerValue(larger)
        return AdditionProblem(a = larger - smaller, b = smaller)
    }

    private fun createRandomSubtractionProblem(): Problem {
        val larger = getRandomLargerValue()
        val smaller = getRandomSmallerValue(larger)
        return SubtractionProblem(a = larger, b = smaller)
    }

    private fun getRandomLargerValue() =
        random.nextInt(LARGER_VALUE_FROM, LARGER_VALUE_UNTIL)

    private fun getRandomSmallerValue(larger: Int) =
        random.nextInt(SMALLER_VALUE_FROM, larger)

    private companion object{
        private const val LARGER_VALUE_UNTIL: Int = 50
        private const val LARGER_VALUE_FROM: Int = 12
        private const val SMALLER_VALUE_FROM: Int = 7
    }
}