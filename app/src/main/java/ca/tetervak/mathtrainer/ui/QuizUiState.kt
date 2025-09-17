package ca.tetervak.mathtrainer.ui

import ca.tetervak.mathtrainer.domain.Problem

/**
 * Data class that represents the game UI state
 */
data class QuizUiState(
    val numberOfProblems: Int,
    val problem: Problem,
    val problemCount: Int,
    val score: Int,
    val wrongAnswer: Boolean,
    val quizEnded: Boolean
)