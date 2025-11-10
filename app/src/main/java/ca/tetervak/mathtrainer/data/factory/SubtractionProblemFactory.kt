package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import kotlin.random.Random

class SubtractionProblemFactory(
    private val random: Random = Random.Default
){
    fun createRandomProblem(): AlgebraProblem {
        val minuend = getRandomMinuend()
        val subtrahend = getRandomSubtrahend(minuend)
        return AlgebraProblem(
            firstNumber = minuend,
            secondNumber = subtrahend,
            algebraOperation = AlgebraOperation.SUBTRACTION
        )
    }

    private fun getRandomMinuend() =
        random.nextInt(MINUEND_MIN, MINUEND_MAX)

    private fun getRandomSubtrahend(minuend: Int) =
        random.nextInt(SUBTRAHEND_MIN, minuend)

    private companion object {
        private const val MINUEND_MIN: Int = 12
        private const val MINUEND_MAX: Int = 50
        private const val SUBTRAHEND_MIN: Int = 7
    }
}