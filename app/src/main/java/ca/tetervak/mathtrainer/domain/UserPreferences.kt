package ca.tetervak.mathtrainer.domain

data class UserPreferences(
    val numberOfProblems: Int,
    val problemGeneration: ProblemGeneration
)