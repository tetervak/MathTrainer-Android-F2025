package ca.tetervak.mathtrainer.di

import android.content.Context
import ca.tetervak.mathtrainer.data.database.MathTrainerDatabase
import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMathTrainerDatabase(
        @ApplicationContext applicationContext: Context
    ): MathTrainerDatabase = MathTrainerDatabase.Companion.getDatabase(applicationContext)

    @Singleton
    @Provides
    fun provideLocalProblemDao(
        database: MathTrainerDatabase
    ): ProblemDao = database.localProblemDao()
}