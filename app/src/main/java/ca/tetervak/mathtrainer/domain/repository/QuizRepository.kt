package ca.tetervak.mathtrainer.domain.repository

import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getUserQuizzesFlow(): Flow<List<Quiz>>
    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>>
    fun getProblemByIdFlow(problemId: String): Flow<Problem?>
    suspend fun updateProblem(problemId: String, userAnswer: String?, answerStatus: AnswerStatus)
    suspend fun getQuizScore(quizId: String): QuizScore
    fun getQuizStatusFlow(quizId: String): Flow<QuizStatus>
    fun addNewGeneratedQuiz()
    fun deleteQuizWithProblems(quizId: String)
    suspend fun getNextProblemId(problem: Problem): String?
    suspend fun getPreviousProblemId(problem: Problem): String?
    suspend fun getFirstProblemId(quizId: String): String?
    suspend fun getQuizProblemCount(quizId: String): Int
    suspend fun getNumberOfRightAnswers(quizId: String): Int
    suspend fun getQuizNumber(quizId: String): Int
    fun getQuizByIdFlow(quizId: String): Flow<Quiz?>
    fun getQuizCountFlow(): Flow<Int>
}

