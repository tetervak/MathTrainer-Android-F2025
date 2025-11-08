package ca.tetervak.mathtrainer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(entity: QuizEntity)

    @Query("SELECT * FROM quizzes WHERE user_id = :userId ORDER BY quiz_number DESC")
    fun getUserQuizzesFlow(userId: String): Flow<List<QuizEntity>>

    @Query("SELECT * FROM quizzes WHERE quiz_id = :quizId")
    fun getQuizByIdFlow(quizId: String): Flow<QuizEntity?>

    @Query("SELECT MAX(quiz_number) FROM quizzes WHERE user_id = :userId")
    suspend fun getQuizMaxOrder(userId: String): Int?

    @Query("SELECT quiz_number FROM quizzes WHERE quiz_id = :quizId")
    suspend fun getQuizOrder(quizId: String): Int?
}