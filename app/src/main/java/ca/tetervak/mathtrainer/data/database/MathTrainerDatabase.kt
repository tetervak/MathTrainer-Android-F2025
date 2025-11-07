package ca.tetervak.mathtrainer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ca.tetervak.mathtrainer.data.database.converter.AlgebraOperationConverter
import ca.tetervak.mathtrainer.data.database.converter.DateConverter
import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity

@Database(
    entities = [ProblemEntity::class], version = 6, exportSchema = false
)
@TypeConverters(DateConverter::class, AlgebraOperationConverter::class)
abstract class MathTrainerDatabase : RoomDatabase() {

    abstract fun localProblemDao(): ProblemDao

    companion object {
        @Volatile
        private var Instance: MathTrainerDatabase? = null

        fun getDatabase(context: Context): MathTrainerDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MathTrainerDatabase::class.java, "math_trainer_database"
                )
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}