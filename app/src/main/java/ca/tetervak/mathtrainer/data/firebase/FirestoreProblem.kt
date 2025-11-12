package ca.tetervak.mathtrainer.data.firebase

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import com.google.firebase.Timestamp

data class FirestoreProblem(
    val id: String = "",
    val problemNumber: Int,
    val firstNumber: Int,
    val secondNumber: Int,
    val algebraOperation: AlgebraOperation,
    val userAnswer: String? = null,
    val answerStatus: AnswerStatus = AnswerStatus.NOT_ANSWERED,
    val updatedAt: Timestamp? = null
)

// the id is not in the map to avoid data duplication
fun FirestoreProblem.toMap(): Map<String, Any?> = mapOf(
    "problemNumber" to problemNumber,
    "firstNumber" to firstNumber,
    "secondNumber" to secondNumber,
    "algebraOperation" to algebraOperation,
    "userAnswer" to userAnswer,
    "answerStatus" to answerStatus,
    "updatedAt" to updatedAt
)
