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

        println("problem.userAnswer = \"3\", grade = ${problem.checkAnswer("3")}")
        assertEquals(
            Problem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("3"))

        println("problem.userAnswer = \"4\", grade = ${problem.checkAnswer("4")}")
        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("4"))

        println("problem.userAnswer = \"whatever\", grade = ${problem.checkAnswer("whatever")}")
        assertEquals(
            Problem.Grade.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        println("problem.userAnswer = \"3.0\", grade = ${problem.checkAnswer("3.0")}")
        assertEquals(
            Problem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("3.0"))

        println("problem.userAnswer = \"3.1\", grade = ${problem.checkAnswer("3.1")}")
        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("3.1"))

        println("problem.userAnswer = \"2.9\", grade = ${problem.checkAnswer("2.9")}")
        assertEquals(
            Problem.Grade.WRONG_ANSWER,
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