package ca.tetervak.mathtrainer.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.data.repository.UserProblemRepository
import ca.tetervak.mathtrainer.domain.UserProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    repository: UserProblemRepository
) : ViewModel() {

    val uiState: StateFlow<ScoreUiState> = repository.getAllUserProblemsFlow()
        .map { list ->
            ScoreUiState(
                score = list.sumOf { userProblem ->
                    if (userProblem.status == UserProblem.Status.RIGHT_ANSWER) 1 else 0
                },
                numberOfProblems = list.size
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScoreUiState(score = 0, numberOfProblems = 0)
        )

}