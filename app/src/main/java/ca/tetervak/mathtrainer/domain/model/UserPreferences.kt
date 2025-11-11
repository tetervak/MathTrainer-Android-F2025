package ca.tetervak.mathtrainer.domain.model

data class UserPreferences(
    val numberOfProblems: Int,
    val problemGeneration: ProblemGeneration
)