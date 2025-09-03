package ca.tetervak.mathtrainer.domain

data class AdditionProblem(
    private val a: Int,
    private val b: Int
) : AlgebraProblem() {
    public override val answer: Int = a + b
    override val text: String = "$a + $b = ?"
}
