package ca.tetervak.mathtrainer.ui

import ca.tetervak.mathtrainer.domain.Problem

/**
 * Data class that represents the game UI state
 */
data class GameUiState(
    val problem: Problem,
    val problemCount: Int,
    val score: Int = 0,
    val isWrongAnswer: Boolean = false,
    val isGameOver: Boolean = false
)