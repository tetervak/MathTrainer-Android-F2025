package ca.tetervak.mathtrainer.data.remote

data class AlgebraQuiz(
    val numberOfProblems: Int,
    val problems: List<AlgebraQuizProblem>
)
