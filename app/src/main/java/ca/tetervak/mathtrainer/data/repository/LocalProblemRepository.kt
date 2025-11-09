package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class LocalProblemRepository(
    private val problemDao: ProblemDao,
    private val dispatcher: CoroutineDispatcher
) {

    @Inject
    constructor(problemDao: ProblemDao) : this(
        problemDao = problemDao,
        dispatcher = Dispatchers.IO
    )

    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>> =
        problemDao.getQuizProblemsFlow(quizId = quizId)
            .map { list -> list.map { entity -> entity.toDomain() } }
            .flowOn(context = dispatcher)

    fun getProblemByIdFlow(problemId: String): Flow<Problem?> =
        problemDao.getProblemFlowById(problemId = problemId)
            .map { localProblem -> localProblem?.toDomain() }
            .flowOn(context = dispatcher)

    suspend fun updateProblem(problem: Problem) =
        withContext(context = dispatcher) {
            problemDao.updateProblem(entity = problem.toEntity())
        }

    suspend fun getNextProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            problemDao.getQuizProblemByOrder(problem.quizId, order = problem.order + 1)?.pId
        }

    suspend fun getPreviousProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            problemDao.getQuizProblemByOrder(problem.quizId, order = problem.order - 1)?.pId
        }

    suspend fun getFirstProblemId(quizId: String): String? =
        withContext(context = dispatcher) {
            problemDao.getQuizProblemByOrder(quizId, order = 1)?.pId
        }

    suspend fun insertAlgebraProblems(
        quizId: String,
        list: List<AlgebraProblem>,
        firstOrder: Int = 1
    ) = withContext(context = dispatcher) {
        problemDao.insertProblems(
            entities = list.mapIndexed { index, algebraProblem ->
                algebraProblem.toEntity(
                    quizId = quizId,
                    order = index + firstOrder,
                    userAnswer = null,
                    status = UserAnswerStatus.NOT_ANSWERED
                )
            }
        )
    }

    suspend fun getQuizScore(quizId: String): QuizScore =
        withContext(context = dispatcher) {
            val numberOfProblems = problemDao.getQuizProblemCount(quizId = quizId)
            val numberOfRightAnswers = problemDao.getQuizProblemCountByStatus(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            )
            QuizScore(
                numberOfProblems = numberOfProblems,
                rightAnswers = numberOfRightAnswers
            )
        }

    fun getQuizScoreFlow(quizId: String): Flow<QuizScore> {
        val quizScoreFlow: Flow<QuizScore> = combine(
            problemDao.getQuizProblemCountFlow(quizId = quizId),
            problemDao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            ),
        ) { numberOfProblems, rightAnswers ->
            QuizScore(
                numberOfProblems = numberOfProblems,
                rightAnswers = rightAnswers
            )
        }
        return quizScoreFlow.flowOn(context = dispatcher)
    }


    fun getQuizStatusDataFlow(quizId: String): Flow<QuizStatus> {
        val quizStatusFlow: Flow<QuizStatus> = combine(
            problemDao.getQuizProblemCountFlow(quizId = quizId),
            problemDao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            ),
            problemDao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.NOT_ANSWERED
            ),
            problemDao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.WRONG_ANSWER
            )
        ) { numberOfProblems, rightAnswers, notAnswered, wrongAnswers ->
            QuizStatus(
                problemCount = numberOfProblems,
                rightAnswers = rightAnswers,
                notAnswered = notAnswered,
                wrongAnswers = wrongAnswers
            )
        }
        return quizStatusFlow.flowOn(context = dispatcher)
    }

    suspend fun getNumberOfProblems(quizId: String): Int =
        withContext(context = dispatcher) {
            problemDao.getQuizProblemCount(quizId = quizId)
        }

    suspend fun getNumberOfRightAnswers(quizId: String): Int =
        withContext(context = dispatcher) {
            problemDao.getQuizProblemCountByStatus(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            )
        }

    suspend fun deleteProblemsByQuizId(quizId: String) =
        withContext(context = dispatcher) {
            problemDao.deleteProblemsByQuizId(quizId = quizId)
        }
}


