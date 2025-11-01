package ca.tetervak.mathtrainer.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.tetervak.mathtrainer.domain.AlgebraOperator
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import java.util.Date

@Entity(tableName = "user_problems")
data class LocalProblem(

    @PrimaryKey
    val id: Int = 0,

    @ColumnInfo(name = "first_number")
    val a: Int,

    @ColumnInfo(name = "second_number")
    val b: Int,

    @ColumnInfo(name = "operator")
    val op: AlgebraOperator,

    @ColumnInfo(name = "correct_answer")
    val answer: Int,

    @ColumnInfo(name = "user_answer")
    val userAnswer: String?,

    val status: UserAnswerStatus,

    val date: Date = Date()
)