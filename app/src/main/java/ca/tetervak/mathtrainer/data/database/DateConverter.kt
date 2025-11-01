package ca.tetervak.mathtrainer.data.database

// In a file like DateConverter.kt
import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
