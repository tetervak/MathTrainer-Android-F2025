package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import kotlin.random.Random

class AdditionProblemFactory(
    private val random: Random = Random.Default
) {
    fun createRandomProblem(): AlgebraProblem {
        val targetSum = getRandomTargetSum()
        val secondAddend = getRandomSecondAddend(targetSum)
        val firstAddend = targetSum - secondAddend

        return AlgebraProblem(
            firstNumber = firstAddend,
            secondNumber = secondAddend,
            algebraOperation = AlgebraOperation.ADDITION
        )
    }

    private fun getRandomTargetSum() =
        random.nextInt(TARGET_SUM_MIN, TARGET_SUM_MAX)

    private fun getRandomSecondAddend(targetSum: Int) =
        random.nextInt(SECOND_ADDEND_MIN, targetSum)

    private companion object {
        private const val TARGET_SUM_MIN: Int = 12
        private const val TARGET_SUM_MAX: Int = 50
        private const val SECOND_ADDEND_MIN: Int = 7
    }
}