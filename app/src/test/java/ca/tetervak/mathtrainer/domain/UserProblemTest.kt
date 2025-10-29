package ca.tetervak.mathtrainer.domain

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserProblemTest {

    val problem: AdditionProblem = AdditionProblem(1,2)
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

        val copy1 = userProblem.copy(userAnswer = "3")
        assertEquals("3", copy1.userAnswer)
        assertEquals(UserProblem.Status.RIGHT_ANSWER, copy1.status)

        val copy2 = userProblem.copy(userAnswer = "4")
        assertEquals("4", copy2.userAnswer)
        assertEquals(UserProblem.Status.WRONG_ANSWER, copy2.status)

        val copy3 = userProblem.copy(userAnswer = "whatever")
        assertEquals("whatever", copy3.userAnswer)
        assertEquals(UserProblem.Status.INVALID_INPUT, copy3.status)

        val copy4 = userProblem.copy(userAnswer = "3.0")
        assertEquals("3.0", copy4.userAnswer)
        assertEquals(UserProblem.Status.RIGHT_ANSWER, copy4.status)

        val copy5 = userProblem.copy(userAnswer = "3.1")
        assertEquals("3.1", copy5.userAnswer)
        assertEquals(UserProblem.Status.WRONG_ANSWER, copy5.status)

        val copy6 = userProblem.copy(userAnswer = "2.9")
        assertEquals("2.9", copy6.userAnswer)
        assertEquals(UserProblem.Status.WRONG_ANSWER, copy6.status)

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
    fun getProblem() {
        println("quizProblem.problem = ${userProblem.problem}")
    }

}