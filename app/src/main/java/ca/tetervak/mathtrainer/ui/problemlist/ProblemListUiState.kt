package ca.tetervak.mathtrainer.ui.problemlist

import ca.tetervak.mathtrainer.domain.model.UserProblem

data class ProblemListUiState(
    val problemList: List<UserProblem>
)
