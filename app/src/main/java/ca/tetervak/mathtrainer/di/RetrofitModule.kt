package ca.tetervak.mathtrainer.di

import ca.tetervak.mathtrainer.data.remote.RandomQuizApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.Default.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideRandomQuizApi(retrofit: Retrofit): RandomQuizApi =
        retrofit.create(RandomQuizApi::class.java)

}