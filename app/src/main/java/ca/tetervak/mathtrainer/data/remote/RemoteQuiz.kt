package ca.tetervak.mathtrainer.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RemoteQuiz(
    val numberOfProblems: Int,
    val problems: List<RemoteProblem>
)
