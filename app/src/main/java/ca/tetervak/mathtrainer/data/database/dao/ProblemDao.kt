package ca.tetervak.mathtrainer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblemDao {

    @Query("""
        SELECT * FROM problems 
        WHERE quiz_id = :quizId 
        ORDER BY problem_number ASC
    """)
    fun getQuizProblemsFlow(quizId: String): Flow<List<ProblemEntity>>

    @Update
    suspend fun updateProblem(entity: ProblemEntity)

    @Query("""
        SELECT COUNT(*) FROM problems 
        WHERE quiz_id = :quizId AND status = :status
    """)
    fun getQuizProblemCountByStatusFlow(quizId: String, status: UserAnswerStatus): Flow<Int>

    @Query("""
        SELECT COUNT(*) FROM problems 
        WHERE quiz_id = :quizId AND status = :status
    """)
    suspend fun getQuizProblemCountByStatus(quizId: String, status: UserAnswerStatus): Int

    @Query("""
        SELECT * FROM problems 
        WHERE problem_id = :problemId
    """)
    fun getProblemFlowById(problemId: String): Flow<ProblemEntity?>

    @Insert
    suspend fun insertProblems(entities: List<ProblemEntity>)

    @Query("DELETE FROM problems WHERE quiz_id = :quizId")
    suspend fun deleteProblemsByQuizId(quizId: String)

    @Query("SELECT COUNT(*) FROM problems WHERE quiz_id = :quizId")
    fun getQuizProblemCountFlow(quizId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM problems WHERE quiz_id = :quizId")
    fun getQuizProblemCount(quizId: String): Int

    @Query("""
        SELECT * FROM problems 
        WHERE quiz_id = :quizId AND problem_number = :order
        LIMIT 1
        """)
    suspend fun getQuizProblemByOrder(quizId: String, order: Int): ProblemEntity?

}