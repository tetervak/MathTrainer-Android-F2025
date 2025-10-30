package ca.tetervak.mathtrainer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.data.repository.RandomQuizRepository
import ca.tetervak.mathtrainer.data.repository.RemoteRandomQuizRepository
import ca.tetervak.mathtrainer.data.repository.UserProblemRepository
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.NewQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserProblemRepository,
    private val newQuizUseCase: NewQuizUseCase,
    private val randomQuizRepository: RemoteRandomQuizRepository
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
            val problems: List<AlgebraProblem> =
                randomQuizRepository.getRandomQuizProblems(numberOfProblems = 10)
            repository.emptyAndInsertAlgebraProblems(list = problems)
        }
    }
}