package ca.tetervak.mathtrainer.domain

import ca.tetervak.mathtrainer.data.factory.ProblemFactory
import javax.inject.Inject

class NewQuizUseCase @Inject constructor(
    private val problemFactory: ProblemFactory
) {
    operator fun invoke(numberOfProblems: Int): List<UserProblem> {
        return List(size = numberOfProblems) { index ->
            UserProblem(
                problem = problemFactory.createRandomProblem(),
                userAnswer = null,
                id = index + 1
            )
        }
    }
}