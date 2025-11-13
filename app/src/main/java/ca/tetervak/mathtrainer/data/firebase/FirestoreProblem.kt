package ca.tetervak.mathtrainer.data.firebase

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import com.google.firebase.Timestamp

data class FirestoreProblem(
    val id: String = "",
    val problemNumber: Int,
    val text: String,
    val userAnswer: String? = null,
    val answerStatus: AnswerStatus = AnswerStatus.NOT_ANSWERED,
    val updatedAt: Timestamp? = null
){
    companion object
}

// the id is not in the map to avoid data duplication
fun FirestoreProblem.toMap(): Map<String, Any?> = mapOf(
    "text" to text,
    "userAnswer" to userAnswer,
    "answerStatus" to answerStatus.name,
    "updatedAt" to updatedAt
)

fun FirestoreProblem.Companion.fromMap(problemId: String, data: Map<String, Any>) =
    FirestoreProblem(
        id = problemId,
        problemNumber = (data["problemNumber"] as Long).toInt(),
        text = data["text"] as String,
        userAnswer = data["userAnswer"] as String?,
        answerStatus = AnswerStatus.valueOf(data["answerStatus"] as String),
        updatedAt = data["updatedAt"] as Timestamp?
    )
