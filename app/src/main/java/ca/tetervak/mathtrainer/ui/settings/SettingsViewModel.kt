package ca.tetervak.mathtrainer.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.PreferencesRepository
import ca.tetervak.mathtrainer.domain.model.ProblemGeneration
import ca.tetervak.mathtrainer.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {

    val uiState: StateFlow<UserPreferences> = preferencesRepository.getUserPreferencesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = runBlocking {
                preferencesRepository.getUserPreferences()
            }
        )


    fun saveNumberOfProblems(numberOfProblems: Int) {
        viewModelScope.launch {
            preferencesRepository.saveNumberOfProblems(numberOfProblems)
        }
    }

    fun saveProblemGeneration(problemGeneration: ProblemGeneration) {
        viewModelScope.launch {
            preferencesRepository.saveProblemGeneration(problemGeneration)
        }
    }
}