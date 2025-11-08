package ca.tetervak.mathtrainer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import java.util.Date
import java.util.UUID

@Entity(tableName = "problems")
data class ProblemEntity(

    @PrimaryKey @ColumnInfo(name = "problem_id")
    val pId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "quiz_id")
    val quizId: String,
    @ColumnInfo(name = "problem_number")
    val order: Int, // problem number in the quiz
    @ColumnInfo(name = "first_number")
    val a: Int,
    @ColumnInfo(name = "second_number")
    val b: Int,
    @ColumnInfo(name = "operation")
    val op: AlgebraOperation,
    @ColumnInfo(name = "correct_answer")
    val answer: Int = op.calculate(a, b),
    @ColumnInfo(name = "user_answer")
    val userAnswer: String? = null,
    val status: UserAnswerStatus = UserAnswerStatus.NOT_ANSWERED,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date()
)