package ca.tetervak.mathtrainer.data.firebase

import com.google.firebase.Timestamp

data class FirestoreQuiz(
    val id: String,
    val quizNumber: Int,
    val problemCount: Int,
    val createdAt: Timestamp? = null
)

// the id is not in the map to avoid data duplication
fun FirestoreQuiz.toMap(): Map<String, Any?> = mapOf(
    "quizNumber" to quizNumber,
    "problemCount" to problemCount,
    "createdAt" to createdAt
)

