package ca.tetervak.mathtrainer.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.data.repository.UserProblemRepository
import ca.tetervak.mathtrainer.domain.AlgebraOperation
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.UserProblem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemDetailsViewModel @Inject constructor(
    private val repository: UserProblemRepository,
) : ViewModel() {

    private val problemIdFlow: MutableStateFlow<Int?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProblemDetailsUiState> =
        problemIdFlow.flatMapLatest { problemId ->
            if (problemId == null) {
                MutableStateFlow(ProblemDetailsUiState.Loading)
            } else {
                repository.getUserProblemFlowById(problemId).filterNotNull()
                    .onEach { userProblem ->
                        if (userProblem.status == UserAnswerStatus.RIGHT_ANSWER) {
                            answerInput = checkNotNull(userProblem.userAnswer)
                        }
                    }
                    .map { userProblem -> ProblemDetailsUiState.Success(userProblem = userProblem) }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProblemDetailsUiState.Loading
        )

    fun loadProblem(problemId: Int) {
        problemIdFlow.value = problemId
    }

    var answerInput by mutableStateOf("")
        private set

    fun updateAnswerInput(input: String) {
        answerInput = input
    }

    fun onSubmit() {
        val uiState = uiState.value
        if (uiState !is ProblemDetailsUiState.Success) return

        val userProblem = uiState.userProblem.copy(userAnswer = answerInput)
        viewModelScope.launch {
            repository.updateUserProblem(userProblem)
            answerInput = ""
        }
    }
}