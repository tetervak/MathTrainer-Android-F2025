package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import kotlin.random.Random

class SubtractionProblemFactory(
    private val random: Random = Random.Default
){

    fun createRandomProblem(): AlgebraProblem {
        val larger = getRandomLargerValue()
        val smaller = getRandomSmallerValue(larger)
        return AlgebraProblem(a = larger, b = smaller, op = AlgebraOperation.SUBTRACTION)
    }

    private fun getRandomLargerValue() =
        random.nextInt(LARGER_VALUE_FROM, LARGER_VALUE_UNTIL)

    private fun getRandomSmallerValue(larger: Int) =
        random.nextInt(SMALLER_VALUE_FROM, larger)

    private companion object {
        private const val LARGER_VALUE_UNTIL: Int = 50
        private const val LARGER_VALUE_FROM: Int = 12
        private const val SMALLER_VALUE_FROM: Int = 7
    }
}