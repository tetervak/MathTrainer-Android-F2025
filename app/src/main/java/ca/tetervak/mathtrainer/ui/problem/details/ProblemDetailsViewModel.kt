package ca.tetervak.mathtrainer.ui.problem.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemDetailsViewModel @Inject constructor(
    private val repository: QuizRepository,
) : ViewModel() {

    private val problemIdFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProblemDetailsUiState> =
        problemIdFlow.flatMapLatest { problemId ->
            if (problemId == null) {
                flowOf(ProblemDetailsUiState.Loading)
            } else {
                repository.getProblemByIdFlow(problemId)
                    .filterNotNull()
                    .onEach { problem ->
                        answerInput = if (problem.status == UserAnswerStatus.RIGHT_ANSWER) {
                            checkNotNull(problem.userAnswer)
                        } else { "" }
                    }
                    .map { problem -> successState(problem) }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProblemDetailsUiState.Loading
        )

    fun loadProblem(problemId: String) {
        problemIdFlow.value = problemId
    }

    var answerInput: String by mutableStateOf("")
        private set

    fun updateAnswerInput(input: String) {
        answerInput = input
    }

    fun onSubmit() {
        val uiState = uiState.value
        if (uiState !is ProblemDetailsUiState.Success) return

        val userProblem = uiState.problem.copy(userAnswer = answerInput)
        viewModelScope.launch {
            repository.updateProblem(userProblem)
            answerInput = ""
        }
    }

    private suspend fun successState(problem: Problem): ProblemDetailsUiState.Success{
        val numberOfProblems = repository.getNumberOfProblems(problem.quizId)
        val quizNumber = repository.getQuizOrder(problem.quizId)
        val numberOfRightAnswers =
            repository.getNumberOfRightAnswers(problem.quizId)
        val firstProblemId = if (problem.order > 1) {
            repository.getFirstProblemId(problem.quizId)
        } else {
            null
        }
        val nextProblemId = if (problem.order < numberOfProblems) {
            repository.getNextProblemId(problem)
        } else {
            null
        }
        val previousProblemId = if (problem.order > 1) {
            repository.getPreviousProblemId(problem)
        } else {
            null
        }
        return ProblemDetailsUiState.Success(
            problem = problem,
            quizNumber = quizNumber,
            numberOfProblems = numberOfProblems,
            numberOfRightAnswers = numberOfRightAnswers,
            firstProblemId = firstProblemId,
            nextProblemId = nextProblemId,
            previousProblemId = previousProblemId
        )
    }

}