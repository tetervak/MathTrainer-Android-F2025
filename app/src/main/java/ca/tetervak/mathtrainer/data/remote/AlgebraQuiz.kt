package ca.tetervak.mathtrainer.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class AlgebraQuiz(
    val numberOfProblems: Int,
    val problems: List<AlgebraQuizProblem>
)
