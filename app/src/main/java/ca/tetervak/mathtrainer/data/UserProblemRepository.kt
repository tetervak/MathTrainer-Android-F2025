package ca.tetervak.mathtrainer.data

import ca.tetervak.mathtrainer.domain.UserProblem
import kotlinx.coroutines.flow.Flow

interface UserProblemRepository {

    fun getAllUserProblemsFlow(): Flow<List<UserProblem>>;

    fun getUserProblemFlowById(id: Int): Flow<UserProblem?>;

    suspend fun updateUserProblemById(id: Int, userAnswer: String?, status: UserProblem.Status);

    suspend fun resetUserProblemById(id: Int);

    suspend fun insertUserProblems(list: List<UserProblem>);

    suspend fun emptyAndInsertUserProblems(list: List<UserProblem>);

    suspend fun getUserProblemCount(): Int;

    suspend fun isEmpty(): Boolean;
}