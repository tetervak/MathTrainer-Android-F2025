package ca.tetervak.mathtrainer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "quizzes")
data class QuizEntity(
    @PrimaryKey @ColumnInfo(name = "quiz_id")
    val qId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "quiz_number")
    val order: Int,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)