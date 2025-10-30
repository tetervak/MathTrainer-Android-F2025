package ca.tetervak.mathtrainer.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomQuizApi {

    @GET("random-quiz")
    suspend fun getRandomQuiz(
        @Query("number-of-problems") numberOfProblems: Int
    ): AlgebraQuiz
}