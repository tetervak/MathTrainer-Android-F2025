package ca.tetervak.mathtrainer.di

import ca.tetervak.mathtrainer.data.repository.QuizStorageRepository
import ca.tetervak.mathtrainer.data.repository.QuizStorageRepositoryRoom
import ca.tetervak.mathtrainer.domain.repository.PreferencesRepository
import ca.tetervak.mathtrainer.data.repository.PreferencesRepositoryDataStore
import ca.tetervak.mathtrainer.domain.repository.QuizRepository
import ca.tetervak.mathtrainer.data.repository.QuizRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindQuizRepository(
        quizRepository: QuizRepositoryImpl
    ): QuizRepository

    @Binds
    abstract fun bindQuizDataRepository(
        quizDataRepository: QuizStorageRepositoryRoom
    ): QuizStorageRepository

    @Binds
    abstract fun bindPreferencesRepository(
        preferencesRepository: PreferencesRepositoryDataStore
    ): PreferencesRepository

}