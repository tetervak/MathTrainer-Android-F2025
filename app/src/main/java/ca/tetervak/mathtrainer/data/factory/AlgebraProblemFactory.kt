package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import kotlin.random.Random

class AlgebraProblemFactory(
    private val random: Random = Random.Default
){
    private val additionProblemFactory = AdditionProblemFactory(random)
    private val subtractionProblemFactory = SubtractionProblemFactory(random)
    private val multiplicationProblemFactory = MultiplicationProblemFactory(random)
    private val divisionProblemFactory = DivisionProblemFactory(random)

    fun createRandomProblem(): AlgebraProblem =
        when (random.nextInt(1, 5)) {
            1 -> additionProblemFactory.createRandomProblem()
            2 -> subtractionProblemFactory.createRandomProblem()
            3 -> multiplicationProblemFactory.createRandomProblem()
            else -> divisionProblemFactory.createRandomProblem()
        }
}