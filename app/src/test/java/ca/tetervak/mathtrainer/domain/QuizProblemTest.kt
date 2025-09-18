package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class QuizProblemTest {

    val problem: Problem = AdditionProblem(1,2)
    val userProblem: UserProblem = UserProblem(problem)

    @Before
    fun setUp() {
        println("--- testing case ---")
        println("quizProblem = $userProblem")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun getUserAnswer() {
        println("quizProblem.userAnswer = ${userProblem.userAnswer}")
    }

    @Test
    fun setUserAnswer() {

        println("quizProblem.problem = ${userProblem.problem}")

        userProblem.userAnswer = "3"
        assertEquals("3", userProblem.userAnswer)
        assertEquals(UserProblem.Status.RIGHT_ANSWER, userProblem.status)

        userProblem.userAnswer = "4"
        assertEquals("4", userProblem.userAnswer)
        assertEquals(UserProblem.Status.WRONG_ANSWER, userProblem.status)

        userProblem.userAnswer = "whatever"
        assertEquals("whatever", userProblem.userAnswer)
        assertEquals(UserProblem.Status.INVALID_INPUT, userProblem.status)

        userProblem.userAnswer = "3.0"
        assertEquals("3.0", userProblem.userAnswer)
        assertEquals(UserProblem.Status.RIGHT_ANSWER, userProblem.status)

        userProblem.userAnswer = "3.1"
        assertEquals("3.1", userProblem.userAnswer)
        assertEquals(UserProblem.Status.WRONG_ANSWER, userProblem.status)

        userProblem.userAnswer = "2.9"
        assertEquals("2.9", userProblem.userAnswer)
        assertEquals(UserProblem.Status.WRONG_ANSWER, userProblem.status)

    }

    @Test
    fun getStatus() {
        println("quizProblem.status = ${userProblem.status}")
    }

    @Test
    fun getText() {
        println("quizProblem.text = \"${userProblem.text}\"")
    }

    @Test
    fun reset() {
        userProblem.userAnswer = "3.0"
        println("quizProblem = $userProblem")
        userProblem.reset()
        println("after reset:")
        println("quizProblem = $userProblem")
        assertEquals(null, userProblem.userAnswer)
        assertEquals(UserProblem.Status.NOT_ANSWERED, userProblem.status)
    }

    @Test
    fun getProblem() {
        println("quizProblem.problem = ${userProblem.problem}")
    }

}