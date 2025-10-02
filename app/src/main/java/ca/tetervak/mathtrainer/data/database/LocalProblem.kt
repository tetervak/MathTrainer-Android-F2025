package ca.tetervak.mathtrainer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_problems")
data class LocalProblem(

    @PrimaryKey
    val id: Int = 0,

    val a: Int,
    val op: Char,
    val b: Int,

    val userAnswer: String?
)