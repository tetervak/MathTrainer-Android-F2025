package ca.tetervak.mathtrainer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import java.util.Date

@Entity(tableName = "user_problems")
data class ProblemEntity(

    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = "first_number")
    val a: Int,

    @ColumnInfo(name = "second_number")
    val b: Int,

    @ColumnInfo(name = "operation")
    val op: AlgebraOperation,

    @ColumnInfo(name = "correct_answer")
    val answer: Int,

    @ColumnInfo(name = "user_answer")
    val userAnswer: String?,

    val status: UserAnswerStatus,

    val date: Date = Date()
)