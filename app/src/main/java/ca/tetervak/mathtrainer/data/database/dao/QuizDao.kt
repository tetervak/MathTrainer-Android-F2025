package ca.tetervak.mathtrainer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM quizzes WHERE user_id = :userId ORDER BY quiz_number ASC")
    fun getUserQuizzesFlow(userId: String): Flow<List<QuizEntity>>

    @Query("SELECT COUNT(*) FROM quizzes WHERE user_id = :userId")
    fun getUserQuizCountFlow(userId: String): Flow<Int>

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizByIdFlow(quizId: String): Flow<QuizEntity?>

    @Query("SELECT MAX(quiz_number) FROM quizzes WHERE user_id = :userId")
    suspend fun getMaxQuizNumber(userId: String): Int?

    @Query("SELECT quiz_number FROM quizzes WHERE id = :quizId")
    suspend fun getQuizNumber(quizId: String): Int?

    @Query("SELECT problem_count FROM quizzes WHERE id = :quizId")
    suspend fun getQuizProblemCount(quizId: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(entity: QuizEntity)

    @Insert
    suspend fun insertProblems(entities: List<ProblemEntity>)

    @Transaction
    suspend fun insertQuizWithProblems(userId: String, problems: List<ProblemEntity>){
        val order: Int = (getMaxQuizNumber(userId = userId) ?: 0) + 1
        val quizId = problems.first().quizId
        val quiz = QuizEntity(
            id = quizId,
            userId = userId,
            quizNumber = order,
            problemCount = problems.size
        )
        insertQuiz(entity = quiz)
        insertProblems(entities = problems)
    }

    //  The problems are deleted automatically via "onDelete = ForeignKey.CASCADE"
    @Query("DELETE FROM quizzes WHERE id = :quizId")
    suspend fun deleteQuizWithProblems(quizId: String)

//    @Query("DELETE FROM quizzes WHERE id = :quizId")
//    suspend fun deleteQuizById(quizId: String)

//    @Query("DELETE FROM problems WHERE quiz_id = :quizId")
//    suspend fun deleteProblemsByQuizId(quizId: String)

//    @Transaction
//    suspend fun deleteQuizWithProblems(quizId: String) {
//        deleteProblemsByQuizId(quizId = quizId)
//        deleteQuizById(quizId = quizId)
//    }
}