package ca.tetervak.mathtrainer.ui.problemdetails

import ca.tetervak.mathtrainer.domain.model.UserProblem

sealed interface ProblemDetailsUiState {
    data class Success(val userProblem: UserProblem) : ProblemDetailsUiState
    object Loading : ProblemDetailsUiState
}
