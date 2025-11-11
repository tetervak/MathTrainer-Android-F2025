package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import kotlin.random.Random

class MultiplicationProblemFactory(
    private val random: Random = Random.Default
) {

    fun createRandomProblem(): AlgebraProblem {
        val firstValue = getRandomFirstValue()
        val secondValue = getRandomSecondValue()
        return if (random.nextBoolean()) {
            AlgebraProblem(
                firstNumber = firstValue,
                secondNumber = secondValue,
                algebraOperation = AlgebraOperation.MULTIPLICATION
            )
        } else {
            AlgebraProblem(
                firstNumber = secondValue,
                secondNumber = firstValue,
                algebraOperation = AlgebraOperation.MULTIPLICATION
            )
        }

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