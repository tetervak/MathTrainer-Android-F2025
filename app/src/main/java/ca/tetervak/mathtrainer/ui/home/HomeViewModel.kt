package ca.tetervak.mathtrainer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.UserProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserProblemRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.isEmpty()) {
                repository.insertGeneratedUserProblems()
            }
        }
    }

    fun makeNewProblems() {
        viewModelScope.launch {
            repository.emptyAndInsertGeneratedUserProblems()
        }
    }
}