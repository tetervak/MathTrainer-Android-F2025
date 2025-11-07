package ca.tetervak.mathtrainer.di

import ca.tetervak.mathtrainer.data.factory.AlgebraProblemFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {

    @Provides
    fun provideProblemFactory(): AlgebraProblemFactory = AlgebraProblemFactory()
}