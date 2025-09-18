package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MultiplicationProblemTest {
    @Before
    fun setUp() {
        println("--- testing case ---")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun checkAnswer() {
        val problem = MultiplicationProblem(2,3)
        println("problem = $problem")
        println("problem.answer = ${problem.answer}")

        assertEquals(
            Problem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("6"))

        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("4"))

        assertEquals(
            Problem.Grade.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        assertEquals(
            Problem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("6.0"))

        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("6.1"))

        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("5.9"))
    }

    @Test
    fun getAnswer() {
        val problem = MultiplicationProblem(2,3)
        println("problem = $problem")
        println("problem.answer=${problem.answer}")
        assertEquals(6, problem.answer)
    }

    @Test
    fun getText() {
        val problem = MultiplicationProblem(2,3)
        println("problem = $problem")
        println("problem.text = \"${problem.text}\"")
        assertEquals("2 x 3 = ?", problem.text)
    }

}