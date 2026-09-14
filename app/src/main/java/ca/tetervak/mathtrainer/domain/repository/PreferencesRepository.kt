package ca.tetervak.mathtrainer.domain.repository

import ca.tetervak.mathtrainer.domain.model.ProblemGeneration
import ca.tetervak.mathtrainer.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getUserPreferencesFlow(): Flow<UserPreferences>
    suspend fun getUserPreferences(): UserPreferences
    suspend fun saveNumberOfProblems(numberOfProblems: Int)
    suspend fun saveProblemGeneration(problemGeneration: ProblemGeneration)
}
