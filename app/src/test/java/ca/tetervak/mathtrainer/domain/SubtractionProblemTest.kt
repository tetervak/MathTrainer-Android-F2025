package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SubtractionProblemTest {

    var problem = AlgebraProblem(a = 5, b = 3, op = AlgebraOperator.MINUS)

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
        println("problem.answer = ${problem.answer}")
        assertEquals(2, problem.answer)
    }

    @Test
    fun getText() {
        println("problem.text = \"${problem.text}\"")
        assertEquals("5 - 3 = ?", problem.text)
    }

}