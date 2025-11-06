package ca.tetervak.mathtrainer.ui.details

import ca.tetervak.mathtrainer.domain.UserProblem

sealed interface ProblemDetailsUiState {
    data class Success(val userProblem: UserProblem) : ProblemDetailsUiState
    object Loading : ProblemDetailsUiState
}
