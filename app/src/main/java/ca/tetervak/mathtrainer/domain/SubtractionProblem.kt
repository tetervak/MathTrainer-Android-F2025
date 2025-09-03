package ca.tetervak.mathtrainer.domain

data class SubtractionProblem(
    private val a: Int,
    private val b: Int
) : AlgebraProblem() {
    override val answer: Int = a - b
    override val text: String = "$a - $b = ?"
}
