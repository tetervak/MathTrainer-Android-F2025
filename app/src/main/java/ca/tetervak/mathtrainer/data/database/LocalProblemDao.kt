package ca.tetervak.mathtrainer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface LocalProblemDao {

    @Query("SELECT * FROM user_problems")
    fun getAllLocalProblemsFlow(): Flow<List<LocalProblem>>

    @Query("SELECT * FROM user_problems WHERE id = :id")
    fun getLocalProblemFlowById(id: Int): Flow<LocalProblem?>

    @Query("SELECT * FROM user_problems WHERE id = :id")
    suspend fun getLocalProblemById(id: Int): LocalProblem?

    @Query("UPDATE user_problems SET user_answer = :userAnswer, status = :status, date = :date WHERE id = :id")
    suspend fun updateLocalProblemById(
        id: Int,
        userAnswer: String?,
        status: UserAnswerStatus,
        date: Date
    )

    @Update
    suspend fun updateLocalProblem(localProblem: LocalProblem)

    @Query("DELETE FROM user_problems")
    suspend fun deleteAllLocalProblems()

    @Insert
    suspend fun insertLocalProblems(list: List<LocalProblem>)

    @Transaction
    suspend fun emptyAndInsertLocalProblems(list: List<LocalProblem>) {
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