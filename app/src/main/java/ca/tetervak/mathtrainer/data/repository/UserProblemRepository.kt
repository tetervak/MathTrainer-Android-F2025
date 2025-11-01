package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.domain.ScoreData
import ca.tetervak.mathtrainer.domain.StatusData
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.UserProblem
import kotlinx.coroutines.flow.Flow

interface UserProblemRepository {

    fun getAllUserProblemsFlow(): Flow<List<UserProblem>>

    fun getUserProblemFlowById(id: Int): Flow<UserProblem?>

    suspend fun updateUserProblem(userProblem: UserProblem)

    suspend fun resetUserProblemById(id: Int)

    suspend fun insertUserProblems(list: List<UserProblem>)

    suspend fun emptyAndInsertUserProblems(list: List<UserProblem>)

    suspend fun emptyAndInsertAlgebraProblems(list: List<AlgebraProblem>)

    suspend fun getUserProblemCount(): Int

    suspend fun isEmpty(): Boolean

    fun getScoreDataFlow(): Flow<ScoreData>

    fun getStatusDataFlow(): Flow<StatusData>
}