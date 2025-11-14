package ca.tetervak.mathtrainer.data.firestore.docs

import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import com.google.firebase.Timestamp

data class ProblemDoc(
    val id: String = "",
    val problemNumber: Int = 0,
    val text: String = "",
    val correctAnswer: Int = 0,
    val userAnswer: Any? = null,
    val answerStatus: AnswerStatus = AnswerStatus.NOT_ANSWERED,
    val quizId: String = "",
    val userId: String = "",
    val updatedAt: Timestamp? = null
){
    companion object
}

// the id is not in the map to avoid data duplication
fun ProblemDoc.toMap(): Map<String, Any?> = mapOf(
    "problemNumber" to problemNumber,
    "text" to text,
    "correctAnswer" to correctAnswer,
    "userAnswer" to userAnswer,
    "answerStatus" to answerStatus,
    "quizId" to quizId,
    "userId" to userId,
    "updatedAt" to updatedAt
)

fun ProblemDoc.Companion.fromMap(problemId: String, data: Map<String, Any>) =
    ProblemDoc(
        id = problemId,
        problemNumber = (data["problemNumber"] as Long).toInt(),
        text =  data["text"] as String,
        correctAnswer = (data["correctAnswer"] as Long).toInt(),
        userAnswer = data["userAnswer"],
        answerStatus = AnswerStatus.valueOf(data["answerStatus"] as String),
        quizId = data["quizId"] as String,
        userId = data["userId"] as String,
        updatedAt = data["updatedAt"] as Timestamp?
    )
