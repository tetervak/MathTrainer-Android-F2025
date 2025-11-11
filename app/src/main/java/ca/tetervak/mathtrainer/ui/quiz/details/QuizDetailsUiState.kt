package ca.tetervak.mathtrainer.ui.quiz.details

import ca.tetervak.mathtrainer.domain.model.Quiz
import ca.tetervak.mathtrainer.domain.model.QuizStatus

sealed interface QuizDetailsUiState {
    object Loading : QuizDetailsUiState
    data class Success(
        val quiz: Quiz,
        val quizStatus: QuizStatus,
        val firstProblemId: String?
    ) : QuizDetailsUiState
}