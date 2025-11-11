package ca.tetervak.mathtrainer.ui.quiz.list

import ca.tetervak.mathtrainer.domain.model.Quiz
import ca.tetervak.mathtrainer.domain.model.QuizScore

data class QuizListUiState(
    val stateList: List<QuizListItemUiState>
)

data class QuizListItemUiState(
    val quiz: Quiz,
    val quizScore: QuizScore
)


