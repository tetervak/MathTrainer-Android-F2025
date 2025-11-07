package ca.tetervak.mathtrainer.domain.repository

import ca.tetervak.mathtrainer.data.repository.LocalUserProblemRepository
import ca.tetervak.mathtrainer.data.repository.RandomQuizRepository
import ca.tetervak.mathtrainer.domain.model.ScoreData
import ca.tetervak.mathtrainer.domain.model.StatusData
import ca.tetervak.mathtrainer.domain.model.UserProblem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProblemRepository @Inject constructor(
    private val localUserProblemRepository: LocalUserProblemRepository,
    private val randomQuizRepository: RandomQuizRepository
) {

    fun getAllUserProblemsFlow(): Flow<List<UserProblem>> =
        localUserProblemRepository.getAllUserProblemsFlow()

    fun getUserProblemFlowById(id: Int): Flow<UserProblem?> =
        localUserProblemRepository.getUserProblemFlowById(id)

    suspend fun updateUserProblem(userProblem: UserProblem) =
        localUserProblemRepository.updateUserProblem(userProblem)

    suspend fun isEmpty(): Boolean =
        localUserProblemRepository.isEmpty()

    fun getScoreDataFlow(): Flow<ScoreData> =
        localUserProblemRepository.getScoreDataFlow()

    fun getStatusDataFlow(): Flow<StatusData> =
        localUserProblemRepository.getStatusDataFlow()

    suspend fun insertGeneratedUserProblems(){
        localUserProblemRepository.insertAlgebraProblems(
            list = randomQuizRepository.getRandomQuizProblems()
        )
    }

    suspend fun emptyAndInsertGeneratedUserProblems(){
        localUserProblemRepository.emptyAndInsertAlgebraProblems(
            list = randomQuizRepository.getRandomQuizProblems()
        )
    }


}