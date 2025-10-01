package ca.tetervak.mathtrainer.data.factory

import ca.tetervak.mathtrainer.domain.Problem

interface ProblemFactory {
    fun createRandomProblem(): Problem
}