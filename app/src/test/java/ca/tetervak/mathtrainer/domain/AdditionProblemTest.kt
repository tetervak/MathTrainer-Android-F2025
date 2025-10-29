package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AdditionProblemTest {
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
        val problem = AdditionProblem(1,2)
        println("problem = $problem")
        println("problem.answer = ${problem.answer}")

        assertEquals(
            AlgebraProblem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("3"))

        assertEquals(
            AlgebraProblem.Grade.WRONG_ANSWER,
            problem.checkAnswer("4"))

        assertEquals(
            AlgebraProblem.Grade.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        assertEquals(
            AlgebraProblem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("3.0"))

        assertEquals(
            AlgebraProblem.Grade.WRONG_ANSWER,
            problem.checkAnswer("3.1"))

        assertEquals(
            AlgebraProblem.Grade.WRONG_ANSWER,
            problem.checkAnswer("2.9"))
    }

    @Test
    fun getAnswer() {
        val problem = AdditionProblem(1,2)
        println("problem = $problem")
        println("problem.answer=${problem.answer}")
        assertEquals(3, problem.answer)
    }

    @Test
    fun getText() {
        val problem = AdditionProblem(1,2)
        println("problem = $problem")
        println("problem.text = \"${problem.text}\"")
        assertEquals("1 + 2 = ?", problem.text)
    }

}