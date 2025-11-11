package ca.tetervak.mathtrainer.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
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

    @Query(
        """
        SELECT COUNT(*) FROM problems 
        WHERE quiz_id = :quizId AND answer_status = :answerStatus
    """
    )
    fun getQuizProblemCountByStatusFlow(quizId: String, answerStatus: AnswerStatus): Flow<Int>

    @Query(
        """
        SELECT COUNT(*) FROM problems 
        WHERE quiz_id = :quizId AND answer_status = :answerStatus
    """
    )
    suspend fun getQuizProblemCountByStatus(quizId: String, answerStatus: AnswerStatus): Int

    @Query("""
        SELECT * FROM problems 
        WHERE id = :problemId
    """)
    fun getProblemFlowById(problemId: String): Flow<ProblemEntity?>

    @Query("SELECT COUNT(*) FROM problems WHERE quiz_id = :quizId")
    fun getQuizProblemCountFlow(quizId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM problems WHERE quiz_id = :quizId")
    fun getQuizProblemCount(quizId: String): Int

    @Query(
        """
        SELECT * FROM problems 
        WHERE quiz_id = :quizId AND problem_number = :problemNumber
        LIMIT 1
        """
    )
    suspend fun getQuizProblemByNumber(quizId: String, problemNumber: Int): ProblemEntity?

}