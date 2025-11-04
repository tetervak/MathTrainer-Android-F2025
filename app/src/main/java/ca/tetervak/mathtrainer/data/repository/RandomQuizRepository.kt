package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.ProblemGeneration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomQuizRepository @Inject constructor(
    private val localRandomQuizRepository: LocalRandomQuizRepository,
    private val remoteRandomQuizRepository: RemoteRandomQuizRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun getRandomQuizProblems(): List<AlgebraProblem> =
        withContext(context = Dispatchers.IO){
            val userPreferences = userPreferencesRepository.getUserPreferences()
            val numberOfProblems: Int = userPreferences.numberOfProblems
            when(userPreferences.problemGeneration){
                ProblemGeneration.LOCAL -> localRandomQuizRepository.getRandomQuizProblems(numberOfProblems)
                ProblemGeneration.REMOTE -> remoteRandomQuizRepository.getRandomQuizProblems(numberOfProblems)
            }
        }
}