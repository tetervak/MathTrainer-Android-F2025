package ca.tetervak.mathtrainer.data.database

import androidx.room.TypeConverter
import ca.tetervak.mathtrainer.domain.AlgebraOperation

class AlgebraOperationConverter {

    @TypeConverter
    fun toString(op: AlgebraOperation): String =
        when (op) {
            AlgebraOperation.ADDITION -> "+"
            AlgebraOperation.SUBTRACTION -> "-"
            AlgebraOperation.MULTIPLICATION -> "x"
            AlgebraOperation.DIVISION -> "/"
        }

    @TypeConverter
    fun fromString(op: String): AlgebraOperation =
        when (op){
            "+" -> AlgebraOperation.ADDITION
            "-" -> AlgebraOperation.SUBTRACTION
            "x" -> AlgebraOperation.MULTIPLICATION
            "/" -> AlgebraOperation.DIVISION
            else -> throw IllegalArgumentException("Unknown operation: $op")
        }
}