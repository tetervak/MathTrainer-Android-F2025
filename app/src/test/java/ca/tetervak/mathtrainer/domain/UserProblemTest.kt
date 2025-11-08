package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserProblemTest {

    val algebraProblem: AlgebraProblem = AlgebraProblem(a = 1, b = 2, op = AlgebraOperation.ADDITION)
    val problem: Problem = Problem(
        problem = algebraProblem,
        order = 3,
        userAnswer = null,
        quizId = ""
    )

    @Before
    fun setUp() {
        println("--- testing case ---")
        println("quizProblem = $problem")
    }

    @After
    fun tearDown() {
        println("--- ------- ---- ---")
    }

    @Test
    fun getUserAnswer() {
        println("quizProblem.userAnswer = ${problem.userAnswer}")
    }

    @Test
    fun setUserAnswer() {

        println("quizProblem.problem = ${problem.problem}")

        val copy1 = problem.copy(userAnswer = "3")
        assertEquals("3", copy1.userAnswer)
        assertEquals(UserAnswerStatus.RIGHT_ANSWER, copy1.status)

        val copy2 = problem.copy(userAnswer = "4")
        assertEquals("4", copy2.userAnswer)
        assertEquals(UserAnswerStatus.WRONG_ANSWER, copy2.status)

        val copy3 = problem.copy(userAnswer = "whatever")
        assertEquals("whatever", copy3.userAnswer)
        assertEquals(UserAnswerStatus.INVALID_INPUT, copy3.status)

        val copy4 = problem.copy(userAnswer = "3.0")
        assertEquals("3.0", copy4.userAnswer)
        assertEquals(UserAnswerStatus.RIGHT_ANSWER, copy4.status)

        val copy5 = problem.copy(userAnswer = "3.1")
        assertEquals("3.1", copy5.userAnswer)
        assertEquals(UserAnswerStatus.WRONG_ANSWER, copy5.status)

        val copy6 = problem.copy(userAnswer = "2.9")
        assertEquals("2.9", copy6.userAnswer)
        assertEquals(UserAnswerStatus.WRONG_ANSWER, copy6.status)

    }

    @Test
    fun getStatus() {
        println("quizProblem.status = ${problem.status}")
    }

    @Test
    fun getText() {
        println("quizProblem.text = \"${problem.text}\"")
    }

    @Test
    fun getProblem() {
        println("quizProblem.problem = ${problem.problem}")
    }

}