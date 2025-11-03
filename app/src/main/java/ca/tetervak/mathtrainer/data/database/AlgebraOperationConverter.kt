package ca.tetervak.mathtrainer.data.database

import androidx.room.TypeConverter
import ca.tetervak.mathtrainer.domain.AlgebraOperation

class AlgebraOperationConverter {

    @TypeConverter
    fun toString(op: AlgebraOperation): String =
        op.symbol.toString()

    @TypeConverter
    fun fromString(op: String): AlgebraOperation =
        AlgebraOperation.fromSymbol(symbol = op.first())
}