package ca.tetervak.mathtrainer.data

import ca.tetervak.mathtrainer.data.factory.DivisionProblemFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class DivisionProblemFactoryTest {

    private val random: Random = Random(2)
    private val factory: DivisionProblemFactory = DivisionProblemFactory(random)

    @Before
    fun setUp() {
        println("--- testing case ---")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun createRandomProblem() {
        println("making 50 problems")
        repeat(50){
            println("problem: ${factory.createRandomProblem().text}")
        }
    }
}