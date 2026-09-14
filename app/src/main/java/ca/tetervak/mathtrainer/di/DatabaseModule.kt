package ca.tetervak.mathtrainer.di

import android.content.Context
import androidx.room.Room
import ca.tetervak.mathtrainer.data.database.MathTrainerDatabase
import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        callback: PrepopulateCallback
    ): MathTrainerDatabase =
        Room.databaseBuilder(
            context, MathTrainerDatabase::class.java, "multiple_quizzes_db")
            .addCallback(callback)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()

    @Provides @Singleton
    fun provideQuizDao(db: MathTrainerDatabase): QuizDao = db.quizDao()

    @Provides @Singleton
    fun provideProblemDao(db: MathTrainerDatabase): ProblemDao = db.problemDao()

    @Provides @Singleton
    fun provideUserDao(db: MathTrainerDatabase): UserDao = db.userDao()

    @Provides @Singleton
    fun providePrepopulateCallback(@ApplicationContext context: Context): PrepopulateCallback =
        PrepopulateCallback(context)
}