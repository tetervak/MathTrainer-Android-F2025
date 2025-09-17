package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class QuizProblemTest {

    val problem: Problem = AdditionProblem(1,2)
    val quizProblem: QuizProblem = QuizProblem(problem)

    @Before
    fun setUp() {
        println("--- testing case ---")
        println("quizProblem = $quizProblem")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun getUserAnswer() {
        println("quizProblem.userAnswer = ${quizProblem.userAnswer}")
    }

    @Test
    fun setUserAnswer() {

        println("quizProblem.problem = ${quizProblem.problem}")

        quizProblem.userAnswer = "3"
        assertEquals("3", quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.RIGHT_ANSWER, quizProblem.status)

        quizProblem.userAnswer = "4"
        assertEquals("4", quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.WRONG_ANSWER, quizProblem.status)

        quizProblem.userAnswer = "whatever"
        assertEquals("whatever", quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.INVALID_INPUT, quizProblem.status)

        quizProblem.userAnswer = "3.0"
        assertEquals("3.0", quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.RIGHT_ANSWER, quizProblem.status)

        quizProblem.userAnswer = "3.1"
        assertEquals("3.1", quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.WRONG_ANSWER, quizProblem.status)

        quizProblem.userAnswer = "2.9"
        assertEquals("2.9", quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.WRONG_ANSWER, quizProblem.status)

    }

    @Test
    fun getStatus() {
        println("quizProblem.status = ${quizProblem.status}")
    }

    @Test
    fun getText() {
        println("quizProblem.text = \"${quizProblem.text}\"")
    }

    @Test
    fun reset() {
        quizProblem.userAnswer = "3.0"
        println("quizProblem = $quizProblem")
        quizProblem.reset()
        println("after reset:")
        println("quizProblem = $quizProblem")
        assertEquals(null, quizProblem.userAnswer)
        assertEquals(QuizProblem.Status.NOT_ANSWERED, quizProblem.status)
    }

    @Test
    fun getProblem() {
        println("quizProblem.problem = ${quizProblem.problem}")
    }

}