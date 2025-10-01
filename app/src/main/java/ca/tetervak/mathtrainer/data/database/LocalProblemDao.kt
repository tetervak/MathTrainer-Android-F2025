package ca.tetervak.mathtrainer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalProblemDao {

    @Query("SELECT * FROM user_problems")
    fun getAllLocalProblemsFlow(): Flow<List<LocalProblem>>

    @Query("SELECT * FROM user_problems WHERE id = :id")
    fun getLocalProblemFlowById(id: Int): Flow<LocalProblem?>

    @Query("UPDATE user_problems SET userAnswer = :userAnswer WHERE id = :id")
    suspend fun updateLocalProblemById(id: Int, userAnswer: String?)

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
    suspend fun getLocalProblemCount(): Int

}