package ca.tetervak.mathtrainer.data.firebase

import com.google.firebase.Timestamp

data class FirestoreQuiz(
    val id: String = "",
    val quizNumber: Int,
    val problemCount: Int,
    val createdAt: Timestamp? = null
){
    companion object
}

// the id is not in the map to avoid data duplication
fun FirestoreQuiz.toMap(): Map<String, Any?> = mapOf(
    "quizNumber" to quizNumber,
    "problemCount" to problemCount,
    "createdAt" to createdAt
)

fun FirestoreQuiz.Companion.fromMap(quizId: String, data: Map<String, Any>) =
    FirestoreQuiz(
        id = quizId,
        quizNumber = (data["quizNumber"] as Long).toInt(),
        problemCount = (data["problemCount"] as Long).toInt(),
        createdAt = data["createdAt"] as Timestamp?
    )

