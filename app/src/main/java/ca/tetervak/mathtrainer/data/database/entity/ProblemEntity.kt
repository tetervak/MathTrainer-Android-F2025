package ca.tetervak.mathtrainer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "problems",
    foreignKeys = [ForeignKey(
        entity = QuizEntity::class,
        parentColumns = ["id"],
        childColumns = ["quiz_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("quiz_id")]
)
data class ProblemEntity(

    @PrimaryKey @ColumnInfo(name = "id")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "quiz_id")
    val quizId: String,
    @ColumnInfo(name = "problem_number")
    val problemNumber: Int, // problem number in the quiz
    @ColumnInfo(name = "first_number")
    val firstNumber: Int,
    @ColumnInfo(name = "second_number")
    val secondNumber: Int,
    @ColumnInfo(name = "algebra_operation")
    val algebraOperation: AlgebraOperation,
    @ColumnInfo(name = "correct_answer")
    val correctAnswer: Int = algebraOperation.calculate(firstNumber, secondNumber),
    @ColumnInfo(name = "user_answer")
    val userAnswer: String? = null,
    @ColumnInfo(name = "answer_status")
    val answerStatus: AnswerStatus = AnswerStatus.NOT_ANSWERED,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date()
)