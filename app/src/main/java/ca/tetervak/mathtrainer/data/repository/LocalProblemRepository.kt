package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.ScoreData
import ca.tetervak.mathtrainer.domain.model.StatusData
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class LocalProblemRepository(
    val dao: ProblemDao,
    private val externalScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) {

    @Inject
    constructor(dao: ProblemDao) : this(
        dao = dao,
        externalScope = GlobalScope,
        dispatcher = Dispatchers.IO
    )

    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>> =
        dao.getQuizProblemsFlow(quizId = quizId)
            .map { list -> list.map { entity -> entity.toDomain() } }
            .flowOn(context = dispatcher)

    fun getProblemByIdFlow(problemId: String): Flow<Problem?> =
        dao.getProblemFlowById(problemId = problemId)
            .map { localProblem -> localProblem?.toDomain() }
            .flowOn(context = dispatcher)

    suspend fun updateProblem(problem: Problem) =
        withContext(context = dispatcher) {
            dao.updateProblem(entity = problem.toEntity())
        }

    suspend fun getNextProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            dao.getQuizProblemByOrder(problem.quizId, order = problem.order + 1)?.pId
        }

    suspend fun getPreviousProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            dao.getQuizProblemByOrder(problem.quizId, order = problem.order - 1)?.pId
        }

    suspend fun getFirstProblemId(quizId: String): String? =
        withContext(context = dispatcher) {
            dao.getQuizProblemByOrder(quizId, order = 1)?.pId
        }

    fun insertAlgebraProblems(
        quizId: String,
        list: List<AlgebraProblem>,
        firstOrder: Int = 1
    ) {
        externalScope.launch(context = dispatcher) {
            dao.insertProblems(
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
    }

    fun getQuizScoreDataFlow(quizId: String): Flow<ScoreData> {
        val scoreDataFlow: Flow<ScoreData> = combine(
            dao.getQuizProblemCountFlow(quizId = quizId),
            dao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            ),
        ) { numberOfProblems, rightAnswers ->
            ScoreData(
                numberOfProblems = numberOfProblems,
                rightAnswers = rightAnswers
            )
        }
        return scoreDataFlow.flowOn(context = dispatcher)
    }

    fun getQuizStatusDataFlow(quizId: String): Flow<StatusData> {
        val statusDataFlow: Flow<StatusData> = combine(
            dao.getQuizProblemCountFlow(quizId = quizId),
            dao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            ),
            dao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.NOT_ANSWERED
            ),
            dao.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                status = UserAnswerStatus.WRONG_ANSWER
            )
        ) { numberOfProblems, rightAnswers, notAnswered, wrongAnswers ->
            StatusData(
                numberOfProblems = numberOfProblems,
                rightAnswers = rightAnswers,
                notAnswered = notAnswered,
                wrongAnswers = wrongAnswers
            )

        }
        return statusDataFlow.flowOn(context = dispatcher)
    }

    suspend fun getNumberOfProblems(quizId: String): Int =
        withContext(context = dispatcher) {
            dao.getQuizProblemCount(quizId = quizId)
        }

    suspend fun getNumberOfRightAnswers(quizId: String): Int =
        withContext(context = dispatcher) {
            dao.getQuizProblemCountByStatus(
                quizId = quizId,
                status = UserAnswerStatus.RIGHT_ANSWER
            )
        }
}


