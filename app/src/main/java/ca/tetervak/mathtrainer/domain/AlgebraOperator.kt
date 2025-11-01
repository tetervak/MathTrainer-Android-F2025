package ca.tetervak.mathtrainer.domain

enum class AlgebraOperator {
    PLUS, MINUS, MULTIPLY, DIVIDE;

    val symbol: Char
        get() = when(this) {
            PLUS -> '+'
            MINUS -> '-'
            MULTIPLY -> 'x'
            DIVIDE -> '/'
        }

    fun calculate(a: Int, b: Int): Int =
        when(this){
            PLUS -> a + b
            MINUS -> a - b
            MULTIPLY -> a * b
            DIVIDE -> a / b
        }
}