package ca.tetervak.mathtrainer.data

import ca.tetervak.mathtrainer.domain.Problem

interface ProblemFactory {
    fun createRandomProblem(): Problem
}
