package ca.tetervak.mathtrainer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ca.tetervak.mathtrainer.domain.UserProblem

@Entity(tableName = "user_problems")
data class LocalProblem (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val a: Int,
    val op: Char,
    val b: Int,

    val userAnswer: String?,
    val status: UserProblem.Status
)