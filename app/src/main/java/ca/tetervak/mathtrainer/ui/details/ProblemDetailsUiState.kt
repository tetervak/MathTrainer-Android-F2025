package ca.tetervak.mathtrainer.ui.details

import ca.tetervak.mathtrainer.domain.UserProblem

data class ProblemDetailsUiState(
    val userProblem: UserProblem,
    val score: Int = 0,
)
