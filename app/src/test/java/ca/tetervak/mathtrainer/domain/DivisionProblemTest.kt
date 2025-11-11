package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
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
        val problem = AlgebraProblem(firstNumber = 6, secondNumber = 3, algebraOperation = AlgebraOperation.DIVISION)
        println("problem = $problem")
        println("problem.answer = ${problem.answer}")

        assertEquals(
            AnswerStatus.RIGHT_ANSWER,
            problem.checkAnswer("2"))

        assertEquals(
            AnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("4"))

        assertEquals(
            AnswerStatus.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        assertEquals(
            AnswerStatus.RIGHT_ANSWER,
            problem.checkAnswer("2.0"))

        assertEquals(
            AnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("2.1"))

        assertEquals(
            AnswerStatus.WRONG_ANSWER,
            problem.checkAnswer("1.9"))
    }

    @Test
    fun getAnswer() {
        val problem = AlgebraProblem(firstNumber = 6, secondNumber = 3, algebraOperation = AlgebraOperation.DIVISION)
        println("problem = $problem")
        println("problem.answer=${problem.answer}")
        assertEquals(2, problem.answer)
    }

    @Test
    fun getText() {
        val problem = AlgebraProblem(firstNumber = 6, secondNumber = 3, algebraOperation = AlgebraOperation.DIVISION)
        println("problem = $problem")
        println("problem.text = \"${problem.text}\"")
        assertEquals("6 / 3 = ?", problem.text)
    }

}