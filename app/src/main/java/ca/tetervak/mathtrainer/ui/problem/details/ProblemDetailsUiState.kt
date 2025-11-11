package ca.tetervak.mathtrainer.ui.problem.details

import ca.tetervak.mathtrainer.domain.model.Problem

sealed interface ProblemDetailsUiState {
    object Loading : ProblemDetailsUiState
    data class Success(
        val problem: Problem,
        val quizNumber: Int,
        val numberOfProblems: Int,
        val numberOfRightAnswers: Int,
        val firstProblemId: String?,
        val nextProblemId: String?,
        val previousProblemId: String?
    ) : ProblemDetailsUiState
}