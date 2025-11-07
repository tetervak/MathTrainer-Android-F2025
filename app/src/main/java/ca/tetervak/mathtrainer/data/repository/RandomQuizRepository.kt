package ca.tetervak.mathtrainer.data.repository

import android.util.Log
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.ProblemGeneration
import ca.tetervak.mathtrainer.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomQuizRepository @Inject constructor(
    private val localRandomQuizRepository: LocalRandomQuizRepository,
    private val remoteRandomQuizRepository: RemoteRandomQuizRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    companion object {
        private const val TAG = "RandomQuizRepository"
    }

    suspend fun getRandomQuizProblems(): List<AlgebraProblem> =
        withContext(context = Dispatchers.IO){
            val userPreferences = userPreferencesRepository.getUserPreferences()
            val numberOfProblems: Int = userPreferences.numberOfProblems
            when(userPreferences.problemGeneration){
                ProblemGeneration.LOCAL -> localRandomQuizRepository.getRandomQuizProblems(numberOfProblems)
                ProblemGeneration.REMOTE -> try {
                    remoteRandomQuizRepository.getRandomQuizProblems(numberOfProblems)
                } catch (_: Exception){
                    Log.w(TAG,
                        "getRandomQuizProblems: Could not connect to the server, generating problems locally.")
                    localRandomQuizRepository.getRandomQuizProblems(numberOfProblems)
                }
            }
        }
}