package ca.tetervak.mathtrainer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "quizzes",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["user_id"])]
)
data class QuizEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "quiz_number")
    val quizNumber: Int,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "problem_count")
    val problemCount: Int = 0,
    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date()
)