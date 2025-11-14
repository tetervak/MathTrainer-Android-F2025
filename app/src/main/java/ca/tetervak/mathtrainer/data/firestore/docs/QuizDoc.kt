package ca.tetervak.mathtrainer.data.firestore.docs

import com.google.firebase.Timestamp

data class QuizDoc(
    val id: String = "",
    val quizNumber: Int = 0,
    val title: String = "Quiz $quizNumber",
    val problemCount: Int = 0,
    val createdAt: Timestamp? = null,
) {
    companion object
}

// the id is not in the map to avoid data duplication
fun QuizDoc.toMap(): Map<String, Any?> =
    mapOf(
        "quizNumber" to quizNumber,
        "title" to title,
        "problemCount" to problemCount,
        "createdAt" to createdAt,
    )

fun QuizDoc.Companion.fromMap(
    quizId: String,
    data: Map<String, Any>
) = QuizDoc(
    id = quizId,
    quizNumber = (data["quizNumber"] as Long).toInt(),
    problemCount = (data["problemCount"] as Long).toInt(),
    createdAt = data["createdAt"] as Timestamp?
)

