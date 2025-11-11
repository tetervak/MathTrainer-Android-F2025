package ca.tetervak.mathtrainer.domain.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ca.tetervak.mathtrainer.domain.model.ProblemGeneration
import ca.tetervak.mathtrainer.domain.model.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        const val DEFAULT_NUMBER_OF_PROBLEMS = 10
        val DEFAULT_PROBLEM_GENERATION = ProblemGeneration.LOCAL
        const val TAG = "UserPreferencesRepository"
    }

    private val numberOfProblemsKey = intPreferencesKey("number_of_problems")
    private val problemGenerationKey = stringPreferencesKey("problem_generation")

    private val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val numberOfProblems: Int = preferences[numberOfProblemsKey] ?: DEFAULT_NUMBER_OF_PROBLEMS

            val problemGeneration: ProblemGeneration = preferences[problemGenerationKey]?.let {
                ProblemGeneration.valueOf(value = it)
            } ?: DEFAULT_PROBLEM_GENERATION

            UserPreferences(numberOfProblems, problemGeneration)
        }

    fun getUserPreferencesFlow(): Flow<UserPreferences> = userPreferencesFlow

    suspend fun getUserPreferences() = userPreferencesFlow.first()

    suspend fun saveNumberOfProblems(numberOfProblems: Int) {
        withContext(context = Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[numberOfProblemsKey] = numberOfProblems
            }
        }
    }

    suspend fun saveProblemGeneration(problemGeneration: ProblemGeneration) {
        withContext(context = Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[problemGenerationKey] = problemGeneration.name
            }
        }
    }
}