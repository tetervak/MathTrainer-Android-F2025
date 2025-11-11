package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SubtractionProblemTest {

    var problem = AlgebraProblem(firstNumber = 5, secondNumber = 3, algebraOperation = AlgebraOperation.SUBTRACTION)

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
        println("problem.answer = ${problem.answer}")
        assertEquals(2, problem.answer)
    }

    @Test
    fun getText() {
        println("problem.text = \"${problem.text}\"")
        assertEquals("5 - 3 = ?", problem.text)
    }

}