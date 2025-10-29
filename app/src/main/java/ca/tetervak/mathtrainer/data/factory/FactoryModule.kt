package ca.tetervak.mathtrainer.data.factory

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