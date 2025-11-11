package ca.tetervak.mathtrainer.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteQuiz(
    @SerialName(value = "number_of_problems")
    val numberOfProblems: Int,
    val problems: List<RemoteProblem>
)
