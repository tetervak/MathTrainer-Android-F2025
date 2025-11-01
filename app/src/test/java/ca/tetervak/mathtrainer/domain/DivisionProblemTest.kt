package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DivisionProblemTest {
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
        val problem = AlgebraProblem(a = 6, b = 3, op = AlgebraOperation.DIVISION)
        println("problem = $problem")
        println("problem.answer = ${problem.answer}")

        assertEquals(
            UserAnswerStatus.RIGHT_ANSWER,
            problem.checkAnswer("2"))

        assertEquals(
            UserAnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("4"))

        assertEquals(
            UserAnswerStatus.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        assertEquals(
            UserAnswerStatus.RIGHT_ANSWER,
            problem.checkAnswer("2.0"))

        assertEquals(
            UserAnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("2.1"))

        assertEquals(
            UserAnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("1.9"))
    }

    @Test
    fun getAnswer() {
        val problem = AlgebraProblem(a = 6, b = 3, op = AlgebraOperation.DIVISION)
        println("problem = $problem")
        println("problem.answer=${problem.answer}")
        assertEquals(2, problem.answer)
    }

    @Test
    fun getText() {
        val problem = AlgebraProblem(a = 6, b = 3, op = AlgebraOperation.DIVISION)
        println("problem = $problem")
        println("problem.text = \"${problem.text}\"")
        assertEquals("6 / 3 = ?", problem.text)
    }

}