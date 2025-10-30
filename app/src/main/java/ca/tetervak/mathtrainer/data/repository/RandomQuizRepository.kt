package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.domain.AlgebraProblem

interface RandomQuizRepository {

    suspend fun getRandomQuizProblems(numberOfProblems: Int): List<AlgebraProblem>
}