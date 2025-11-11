package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.entity.UserEntity
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.Quiz
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class LocalQuizRepository(
    private val quizDao: QuizDao,
    private val dispatcher: CoroutineDispatcher
) {

    @OptIn(DelicateCoroutinesApi::class)
    @Inject constructor(quizDao: QuizDao) : this(
        quizDao = quizDao,
        dispatcher = Dispatchers.IO
    )

    private val userId: String = UserEntity.demoUser.id

    fun getUserQuizzesFlow(): Flow<List<Quiz>> =
        quizDao.getUserQuizzesFlow(userId).map { entities ->
            entities.map { entity -> entity.toDomain() }
        }.flowOn(context = dispatcher)

    fun getQuizByIdFlow(quizId: String): Flow<Quiz?> =
        quizDao.getQuizByIdFlow(quizId).map { entity ->
            entity?.toDomain()
        }.flowOn(context = dispatcher)

    fun getQuizCountFlow(): Flow<Int> =
        quizDao.getUserQuizCountFlow(userId)
            .flowOn(context = dispatcher)

    suspend fun insertQuizWithProblems(
        problems: List<AlgebraProblem>
    ) = withContext(context = dispatcher) {
            val quizId = UUID.randomUUID().toString()
            val problemEntities = problems.mapIndexed { index, problem ->
                problem.toEntity(
                    quizId = quizId,
                    problemNumber = index + 1,
                    userAnswer = null,
                    status = AnswerStatus.NOT_ANSWERED
                )
            }
            quizDao.insertQuizWithProblems(
                userId = userId,
                problems = problemEntities
            )
        }

    suspend fun getQuizProblemCount(quizId: String): Int? =
        withContext(context = dispatcher) {
            quizDao.getQuizProblemCount(quizId)
        }

    suspend fun deleteQuizWithProblems(quizId: String) =
        withContext(context = dispatcher){
            quizDao.deleteQuizWithProblems(quizId)
        }

    suspend fun getQuizNumber(quizId: String): Int =
        withContext(context = dispatcher) {
            quizDao.getQuizNumber(quizId) ?: 0
        }

}