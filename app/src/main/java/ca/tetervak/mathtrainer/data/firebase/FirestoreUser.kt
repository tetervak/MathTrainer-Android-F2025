package ca.tetervak.mathtrainer.data.firebase

data class FirestoreUser(
    val id: String = "",
    val quizCount: Int = 0
){
    companion object
}

fun FirestoreUser.toMap(): Map<String, Any?> = mapOf(
    "quizCount" to quizCount
)

fun FirestoreUser.Companion.fromMap(userId: String, data: Map<String, Any?>) = FirestoreUser(
    id = userId,
    quizCount = (data["quizCount"] as Long).toInt()
)


