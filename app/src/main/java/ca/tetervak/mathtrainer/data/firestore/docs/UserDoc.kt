package ca.tetervak.mathtrainer.data.firestore.docs

import com.google.firebase.Timestamp

data class UserDoc(
    val id: String = "",
    val name: String? = null,
    val email: String? = null,
    val createdAt: Timestamp? = null,
    val quizCount: Int = 0,
    val quizNumberCount: Int = 0
) {
    companion object
}

fun UserDoc.toMap(): Map<String, Any?> =
    mapOf(
        "name" to name,
        "email" to email,
        "createdAt" to createdAt,
        "quizCount" to quizCount,
        "quizNumberCount" to quizNumberCount
    )

fun UserDoc.Companion.fromMap(
    userId: String,
    data: Map<String, Any?>
): UserDoc {
    // Safely convert Longs from Firestore to Int
    val quizCount = (data["quizCount"] as? Long)?.toInt() ?: 0
    val quizNumberCounter = (data["quizNumberCount"] as? Long)?.toInt() ?: 0

    return UserDoc(
        id = userId,
        name = data["name"] as? String,
        email = data["email"] as? String,
        createdAt = data["createdAt"] as? Timestamp,
        quizCount = quizCount,
        quizNumberCount = quizNumberCounter
    )
}

