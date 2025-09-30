package ca.tetervak.mathtrainer.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.data.AlgebraProblemFactory
import ca.tetervak.mathtrainer.data.UserProblemRepository
import ca.tetervak.mathtrainer.domain.UserProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemListViewModel @Inject constructor(
    private val repository: UserProblemRepository
) : ViewModel() {

    val uiState: StateFlow<ProblemListUiState> =
        repository.getAllUserProblemsFlow()
            .map { list -> ProblemListUiState(problemList = list) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ProblemListUiState(problemList = emptyList())
            )

    init {
        viewModelScope.launch {
            if (repository.isEmpty()) {
                val problemFactory = AlgebraProblemFactory()
                repository.insertUserProblems(
                    List<UserProblem>(size = 20) {
                        UserProblem(problem = problemFactory.createRandomProblem())
                    }
                )
            }
        }
    }

}