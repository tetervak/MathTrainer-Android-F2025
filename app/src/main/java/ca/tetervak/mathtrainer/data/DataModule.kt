package ca.tetervak.mathtrainer.data

import android.content.Context
import ca.tetervak.mathtrainer.data.local.LocalProblemDao
import ca.tetervak.mathtrainer.data.local.LocalUserProblemRepository
import ca.tetervak.mathtrainer.data.local.MathTrainerDatabase

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
    ): MathTrainerDatabase = MathTrainerDatabase.getDatabase(applicationContext)

    @Singleton
    @Provides
    fun provideLocalProblemDao(
        database: MathTrainerDatabase
    ): LocalProblemDao = database.localProblemDao()

    @Singleton
    @Provides
    fun provideUserProblemRepository(
        localProblemDao: LocalProblemDao
    ): UserProblemRepository = LocalUserProblemRepository(localProblemDao)
}