package ca.tetervak.mathtrainer.domain.repository

import ca.tetervak.mathtrainer.data.repository.LocalProblemRepository
import ca.tetervak.mathtrainer.data.repository.LocalQuizRepository
import ca.tetervak.mathtrainer.data.repository.RandomQuizRepository
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizRepository(
    private val localProblemRepository: LocalProblemRepository,
    private val localQuizRepository: LocalQuizRepository,
    private val randomQuizRepository: RandomQuizRepository,
    private val externalScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) {

    @OptIn(DelicateCoroutinesApi::class)
    @Inject constructor(
        localProblemRepository: LocalProblemRepository,
        localQuizRepository: LocalQuizRepository,
        randomQuizRepository: RandomQuizRepository
    ): this(
        localProblemRepository = localProblemRepository,
        localQuizRepository = localQuizRepository,
        randomQuizRepository = randomQuizRepository,
        externalScope = GlobalScope,
        dispatcher = Dispatchers.IO
    )

    fun getUserQuizzesFlow(): Flow<List<Quiz>> =
        localQuizRepository.getUserQuizzesFlow()

    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>> =
        localProblemRepository.getQuizProblemsFlow(quizId)

    fun getProblemByIdFlow(problemId: String): Flow<Problem?> =
        localProblemRepository.getProblemByIdFlow(problemId)

    suspend fun updateProblem(problem: Problem) =
        localProblemRepository.updateProblem(problem)

    suspend fun getQuizScore(quizId: String): QuizScore =
        localProblemRepository.getQuizScore(quizId)

    fun getQuizStatusFlow(quizId: String): Flow<QuizStatus> =
        localProblemRepository.getQuizStatusDataFlow(quizId)

    suspend fun insertNewGeneratedProblems(quizId: String): Int {
        val problems: List<AlgebraProblem> = randomQuizRepository.getRandomQuizProblems()
        localProblemRepository.insertAlgebraProblems(
            quizId = quizId,
            list = problems
        )
        return problems.size
    }

    fun addNewGeneratedQuiz(){
        externalScope.launch(context = dispatcher) {
            val quiz = localQuizRepository.insertQuiz()
            val count = insertNewGeneratedProblems(quizId = quiz.id)
            localQuizRepository.updateProblemCount(quizId = quiz.id, problemCount = count)
        }
    }

    fun deleteQuizWithProblems(quizId: String){
        externalScope.launch(context = dispatcher) {
            localProblemRepository.deleteProblemsByQuizId(quizId)
            localQuizRepository.deleteQuizById(quizId)
        }
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

    fun getQuizByIdFlow(quizId: String): Flow<Quiz?> =
        localQuizRepository.getQuizByIdFlow(quizId)

    fun getQuizCountFlow(): Flow<Int> =
        localQuizRepository.getQuizCountFlow()

}