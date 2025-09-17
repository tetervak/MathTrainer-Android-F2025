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

        println("problem.userAnswer = \"2\", grade = ${problem.checkAnswer("2")}")
        assertEquals(
            Problem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("2"))

        println("problem.userAnswer = \"4\", grade = ${problem.checkAnswer("4")}")
        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("4"))

        println("problem.userAnswer = \"whatever\", grade = ${problem.checkAnswer("whatever")}")
        assertEquals(
            Problem.Grade.INVALID_INPUT,
            problem.checkAnswer("whatever"))

        println("problem.userAnswer = \"2.0\", grade = ${problem.checkAnswer("2.0")}")
        assertEquals(
            Problem.Grade.RIGHT_ANSWER,
            problem.checkAnswer("2.0"))

        println("problem.userAnswer = \"2.1\", grade = ${problem.checkAnswer("2.1")}")
        assertEquals(
            Problem.Grade.WRONG_ANSWER,
            problem.checkAnswer("2.1"))

        println("problem.userAnswer = \"1.9\", grade = ${problem.checkAnswer("1.9")}")
        assertEquals(
            Problem.Grade.WRONG_ANSWER,
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