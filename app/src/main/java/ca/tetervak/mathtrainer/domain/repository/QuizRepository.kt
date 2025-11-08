package ca.tetervak.mathtrainer.domain.repository

import ca.tetervak.mathtrainer.data.repository.LocalProblemRepository
import ca.tetervak.mathtrainer.data.repository.LocalQuizRepository
import ca.tetervak.mathtrainer.data.repository.RandomQuizRepository
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val localProblemRepository: LocalProblemRepository,
    private val localQuizRepository: LocalQuizRepository,
    private val randomQuizRepository: RandomQuizRepository
) {

    fun getUserQuizzesFlow(): Flow<List<Quiz>> =
        localQuizRepository.getUserQuizzesFlow()

    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>> =
        localProblemRepository.getQuizProblemsFlow(quizId)

    fun getProblemByIdFlow(problemId: String): Flow<Problem?> =
        localProblemRepository.getProblemByIdFlow(problemId)

    suspend fun updateProblem(problem: Problem) =
        localProblemRepository.updateProblem(problem)

    fun getQuizScoreFlow(quizId: String): Flow<QuizScore> =
        localProblemRepository.getQuizScoreFlow(quizId)

    fun getQuizStatusFlow(quizId: String): Flow<QuizStatus> =
        localProblemRepository.getQuizStatusDataFlow(quizId)

    suspend fun insertGeneratedUserProblems(quizId: String){
        localProblemRepository.insertAlgebraProblems(
            quizId = quizId,
            list = randomQuizRepository.getRandomQuizProblems()
        )
    }

    suspend fun insertGeneratedQuiz(){
        val quiz = localQuizRepository.insertQuiz()
        insertGeneratedUserProblems(quizId = quiz.id)
    }

    suspend fun getNextProblemId(problem: Problem): String? =
        localProblemRepository.getNextProblemId(problem)

    suspend fun getPreviousProblemId(problem: Problem): String? =
        localProblemRepository.getPreviousProblemId(problem)

    suspend fun getFirstProblemId(quizId: String): String? =
        localProblemRepository.getFirstProblemId(quizId)

    suspend fun getNumberOfProblems(quizId: String): Int =
        localProblemRepository.getNumberOfProblems(quizId)

    suspend fun getNumberOfRightAnswers(quizId: String): Int =
        localProblemRepository.getNumberOfRightAnswers(quizId)

    suspend fun getQuizOrder(quizId: String): Int =
        localQuizRepository.getQuizOrder(quizId)

    suspend fun getQuizByIdFlow(quizId: String): Flow<Quiz?> =
        localQuizRepository.getQuizByIdFlow(quizId)


}