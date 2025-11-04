package ca.tetervak.mathtrainer.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.data.repository.UserPreferencesRepository
import ca.tetervak.mathtrainer.domain.ProblemGeneration
import ca.tetervak.mathtrainer.domain.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    val uiState: StateFlow<UserPreferences> = userPreferencesRepository.getUserPreferencesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = runBlocking {
                userPreferencesRepository.getUserPreferences()
            }
        )


    fun saveNumberOfProblems(numberOfProblems: Int) {
        viewModelScope.launch {
            userPreferencesRepository.saveNumberOfProblems(numberOfProblems)
        }
    }

    fun saveProblemGeneration(problemGeneration: ProblemGeneration) {
        viewModelScope.launch {
            userPreferencesRepository.saveProblemGeneration(problemGeneration)
        }
    }
}