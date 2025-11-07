package ca.tetervak.mathtrainer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ProblemDao {

    @Query("SELECT * FROM user_problems")
    fun getAllLocalProblemsFlow(): Flow<List<ProblemEntity>>

    @Query("SELECT * FROM user_problems WHERE id = :id")
    fun getLocalProblemFlowById(id: Int): Flow<ProblemEntity?>

    @Query("SELECT * FROM user_problems WHERE id = :id")
    suspend fun getLocalProblemById(id: Int): ProblemEntity?

    @Query("UPDATE user_problems SET user_answer = :userAnswer, status = :status, date = :date WHERE id = :id")
    suspend fun updateLocalProblemById(
        id: Int,
        userAnswer: String?,
        status: UserAnswerStatus,
        date: Date
    )

    @Update
    suspend fun updateLocalProblem(problemEntity: ProblemEntity)

    @Query("DELETE FROM user_problems")
    suspend fun deleteAllLocalProblems()

    @Insert
    suspend fun insertLocalProblems(list: List<ProblemEntity>)

    @Transaction
    suspend fun emptyAndInsertLocalProblems(list: List<ProblemEntity>) {
        deleteAllLocalProblems()
        insertLocalProblems(list)
    }

    @Query("SELECT COUNT(*) FROM user_problems")
    suspend fun getProblemCount(): Int

    @Query("SELECT COUNT(*) FROM user_problems")
    fun getProblemCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM user_problems WHERE status = :status")
    fun getProblemCountByStatusFlow(status: UserAnswerStatus): Flow<Int>

}