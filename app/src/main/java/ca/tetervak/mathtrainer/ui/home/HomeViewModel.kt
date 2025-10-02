package ca.tetervak.mathtrainer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.data.repository.UserProblemRepository
import ca.tetervak.mathtrainer.domain.NewQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserProblemRepository,
    private val newQuizUseCase: NewQuizUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.isEmpty()) {
                repository.insertUserProblems(list = newQuizUseCase(numberOfProblems = 5))
            }
        }
    }

    fun makeNewProblems() {
        viewModelScope.launch {
            repository.emptyAndInsertUserProblems(list = newQuizUseCase(numberOfProblems = 5))
        }
    }
}