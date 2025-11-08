package ca.tetervak.mathtrainer.ui.problem.list

import ca.tetervak.mathtrainer.domain.model.Problem

sealed interface ProblemListUiState{
    object Loading: ProblemListUiState
    data class Success(
        val quizNumber: Int,
        val problemList: List<Problem>,
        val rightAnswers: Int
    ): ProblemListUiState
}