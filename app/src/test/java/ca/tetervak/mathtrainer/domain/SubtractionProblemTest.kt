package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SubtractionProblemTest {

    var problem = SubtractionProblem(5,3)

    @Before
    fun setUp() {
        println("--- testing case ---")
        println("problem = $problem")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun checkAnswer() {
        println("problem.answer = ${problem.answer}")

        assertEquals(
            AlgebraProblem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("2"))

        assertEquals(
            AlgebraProblem.Grade.WRONG_ANSWER,
            problem.checkAnswer("4"))

        assertEquals(
            AlgebraProblem.Grade.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        assertEquals(
            AlgebraProblem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("2.0"))

        assertEquals(
            AlgebraProblem.Grade.WRONG_ANSWER,
            problem.checkAnswer("2.1"))

        assertEquals(
            AlgebraProblem.Grade.WRONG_ANSWER,
            problem.checkAnswer("1.9"))
    }

    @Test
    fun getAnswer() {
        println("problem.answer = ${problem.answer}")
        assertEquals(2, problem.answer)
    }

    @Test
    fun getText() {
        println("problem.text = \"${problem.text}\"")
        assertEquals("5 - 3 = ?", problem.text)
    }

}