package ca.tetervak.mathtrainer.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class AlgebraQuizProblem(
    val id: Int,
    val text: String,
    val answer: Int
)