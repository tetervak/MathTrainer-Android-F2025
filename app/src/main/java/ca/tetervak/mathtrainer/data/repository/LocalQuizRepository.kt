package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import ca.tetervak.mathtrainer.data.database.entity.UserEntity
import ca.tetervak.mathtrainer.domain.model.Quiz
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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

    private val userId: String = UserEntity.demoUser.uId

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

    suspend fun getQuizMaxOrder(): Int =
        withContext(context = dispatcher) {
            quizDao.getQuizMaxOrder(userId) ?: 0
        }

    suspend fun insertQuiz(): Quiz =
        withContext(context = dispatcher) {
            val order = getQuizMaxOrder() + 1
            val entity = QuizEntity(order = order, userId = userId)
            quizDao.insertQuiz(entity)
            entity.toDomain()
        }

    suspend fun deleteQuizById(quizId: String) =
        withContext(context = dispatcher) {
            quizDao.deleteQuizById(quizId)
        }

    suspend fun getQuizOrder(quizId: String): Int =
        withContext(context = dispatcher) {
            quizDao.getQuizOrder(quizId) ?: 0
        }

    suspend fun updateProblemCount(quizId: String, problemCount: Int) =
        withContext(context = dispatcher){
            quizDao.updateProblemCount(quizId, problemCount)
        }

}