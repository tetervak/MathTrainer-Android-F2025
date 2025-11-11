package ca.tetervak.mathtrainer.data

import ca.tetervak.mathtrainer.data.factory.SubtractionProblemFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class SubtractionProblemFactoryTest {

    private val random: Random = Random(2)
    private val factory: SubtractionProblemFactory = SubtractionProblemFactory(random)

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