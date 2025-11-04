package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.LocalProblemDao
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.ScoreData
import ca.tetervak.mathtrainer.domain.StatusData
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.UserProblem
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
import java.util.Date
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class LocalUserProblemRepository(
    val dao: LocalProblemDao,
    private val externalScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) {

    @Inject
    constructor(dao: LocalProblemDao) : this(
        dao = dao,
        externalScope = GlobalScope,
        dispatcher = Dispatchers.IO
    )

    fun getAllUserProblemsFlow(): Flow<List<UserProblem>> =
        dao.getAllLocalProblemsFlow()
            .map { list -> list.map { localProblem -> localProblem.toUserProblem() } }
            .flowOn(context = dispatcher)

    fun getUserProblemFlowById(id: Int): Flow<UserProblem?> =
        dao.getLocalProblemFlowById(id)
            .map { localProblem -> localProblem?.toUserProblem() }
            .flowOn(context = dispatcher)

    suspend fun updateUserProblem(userProblem: UserProblem) =
        withContext(context = dispatcher) {
            dao.updateLocalProblem(localProblem = userProblem.toLocalProblem())
        }

    suspend fun insertAlgebraProblems(list: List<AlgebraProblem>) {
        externalScope.launch(context = dispatcher) {
            dao.insertLocalProblems(
                list = list.mapIndexed { index, algebraProblem ->
                    algebraProblem.toLocalProblem(
                        id = index + 1,
                        userAnswer = null,
                        status = UserAnswerStatus.NOT_ANSWERED
                    )
                }
            )
        }
    }

    suspend fun emptyAndInsertAlgebraProblems(list: List<AlgebraProblem>) {
        externalScope.launch(context = dispatcher) {
            dao.emptyAndInsertLocalProblems(
                list = list.mapIndexed { index, algebraProblem ->
                    algebraProblem.toLocalProblem(
                        id = index + 1,
                        userAnswer = null,
                        status = UserAnswerStatus.NOT_ANSWERED
                    )
                }
            )
        }
    }

    suspend fun getUserProblemCount(): Int =
        withContext(context = Dispatchers.IO) {
            dao.getProblemCount()
        }

    suspend fun isEmpty(): Boolean = (getUserProblemCount() == 0)


    fun getScoreDataFlow(): Flow<ScoreData> {
        val scoreDataFlow: Flow<ScoreData> = combine(
            dao.getProblemCountFlow(),
            dao.getProblemCountByStatusFlow(status = UserAnswerStatus.RIGHT_ANSWER),
        ) { numberOfProblems, rightAnswers ->
            ScoreData(
                numberOfProblems = numberOfProblems,
                rightAnswers = rightAnswers
            )
        }
        return scoreDataFlow.flowOn(context = dispatcher)
    }

    fun getStatusDataFlow(): Flow<StatusData> {
        val statusDataFlow: Flow<StatusData> = combine(
            dao.getProblemCountFlow(),
            dao.getProblemCountByStatusFlow(status = UserAnswerStatus.RIGHT_ANSWER),
            dao.getProblemCountByStatusFlow(status = UserAnswerStatus.NOT_ANSWERED),
            dao.getProblemCountByStatusFlow(status = UserAnswerStatus.WRONG_ANSWER)
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
}


