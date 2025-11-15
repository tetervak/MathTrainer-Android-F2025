package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import kotlinx.coroutines.flow.Flow

interface QuizStorageRepository {
    fun getUserQuizzesFlow(): Flow<List<Quiz>>
    fun getQuizByIdFlow(quizId: String): Flow<Quiz?>
    fun getQuizCountFlow(): Flow<Int>
    suspend fun insertQuizWithProblems(problems: List<AlgebraProblem>)
    suspend fun getQuizProblemCount(quizId: String): Int
    fun getQuizProblemCountFlow(quizId: String): Flow<Int>
    suspend fun deleteQuizWithProblems(quizId: String)
    suspend fun getQuizNumber(quizId: String): Int
    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>>
    fun getProblemByIdFlow(problemId: String): Flow<Problem?>
    suspend fun updateProblem(problemId: String, userAnswer: String?, answerStatus: AnswerStatus)
    suspend fun getNextProblemId(problem: Problem): String?
    suspend fun getPreviousProblemId(problem: Problem): String?
    suspend fun getFirstProblemId(quizId: String): String?
    suspend fun getQuizScore(quizId: String): QuizScore
    fun getQuizScoreFlow(quizId: String): Flow<QuizScore>
    fun getQuizStatusDataFlow(quizId: String): Flow<QuizStatus>
    suspend fun getNumberOfRightAnswers(quizId: String): Int
}
