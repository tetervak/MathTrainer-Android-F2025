package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
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
        val problem = AlgebraProblem(a = 1, b = 2, op = AlgebraOperation.ADDITION)
        println("problem = $problem")
        println("problem.answer = ${problem.answer}")

        assertEquals(
            UserAnswerStatus.RIGHT_ANSWER,
            problem.checkAnswer("3"))

        assertEquals(
            UserAnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("4"))

        assertEquals(
            UserAnswerStatus.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        assertEquals(
            UserAnswerStatus.RIGHT_ANSWER,
            problem.checkAnswer("3.0"))

        assertEquals(
            UserAnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("3.1"))

        assertEquals(
            UserAnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("2.9"))
    }

    @Test
    fun getAnswer() {
        val problem = AlgebraProblem(a = 1, b = 2, op = AlgebraOperation.ADDITION)
        println("problem = $problem")
        println("problem.answer=${problem.answer}")
        assertEquals(3, problem.answer)
    }

    @Test
    fun getText() {
        val problem = AlgebraProblem(a = 1, b = 2, op = AlgebraOperation.ADDITION)
        println("problem = $problem")
        println("problem.text = \"${problem.text}\"")
        assertEquals("1 + 2 = ?", problem.text)
    }

}