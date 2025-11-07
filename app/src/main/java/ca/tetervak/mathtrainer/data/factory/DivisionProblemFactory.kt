package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import kotlin.random.Random

class DivisionProblemFactory(
    private val random: Random = Random.Default
){

    fun createRandomProblem(): AlgebraProblem {
        val first = getRandomFirstValue()
        val second = getRandomSecondValue()
        return if (random.nextBoolean())
            AlgebraProblem(a = first * second, b = first, op = AlgebraOperation.DIVISION)
        else
            AlgebraProblem(a = first * second, b = second, op = AlgebraOperation.DIVISION)
    }

    private fun getRandomFirstValue() =
        random.nextInt(FIRST_VALUE_FROM, FIRST_VALUE_UNTIL)

    private fun getRandomSecondValue() =
        random.nextInt(SECOND_VALUE_FROM, SECOND_VALUE_UNTIL)

    private companion object {

        private const val FIRST_VALUE_FROM: Int = 2
        private const val FIRST_VALUE_UNTIL: Int = 10

        private const val SECOND_VALUE_FROM: Int = 2
        private const val SECOND_VALUE_UNTIL: Int = 11
    }
}