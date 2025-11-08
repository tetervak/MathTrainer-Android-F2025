package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import ca.tetervak.mathtrainer.data.database.entity.UserEntity
import ca.tetervak.mathtrainer.domain.model.Quiz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalQuizRepository @Inject constructor(
    private val quizDao: QuizDao
) {

    private val userId: String = UserEntity.demoUser.uId

    fun getUserQuizzesFlow(): Flow<List<Quiz>> =
        quizDao.getUserQuizzesFlow(userId).map { entities ->
            entities.map { entity -> entity.toDomain() }
        }.flowOn(Dispatchers.IO)

    suspend fun getQuizMaxOrder(): Int =
        withContext(Dispatchers.IO) {
            quizDao.getQuizMaxOrder(userId) ?: 0
        }

    suspend fun insertQuiz(): Quiz =
        withContext(Dispatchers.IO) {
            val order = getQuizMaxOrder() + 1
            val entity = QuizEntity(order = order, userId = userId)
            quizDao.insertQuiz(entity)
            entity.toDomain()
        }

    suspend fun getQuizOrder(quizId: String): Int =
        withContext(Dispatchers.IO) {
            quizDao.getQuizOrder(quizId) ?: 0
        }
}