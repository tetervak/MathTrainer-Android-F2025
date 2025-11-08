package ca.tetervak.mathtrainer.ui.problem.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemListViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    val uiState: StateFlow<ProblemListUiState> =
        repository.getQuizProblemsFlow("")
            .map { list -> ProblemListUiState(problemList = list) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Companion.WhileSubscribed(5000),
                initialValue = ProblemListUiState(problemList = emptyList())
            )

    init {
        viewModelScope.launch {
            //if (repository.isEmpty()) {
            //    repository.insertGeneratedUserProblems()
            //}
        }
    }

}