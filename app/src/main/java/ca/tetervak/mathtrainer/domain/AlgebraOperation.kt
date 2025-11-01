package ca.tetervak.mathtrainer.domain

enum class AlgebraOperation {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION;

    val symbol: Char
        get() = when(this) {
            ADDITION -> '+'
            SUBTRACTION -> '-'
            MULTIPLICATION -> 'x'
            DIVISION -> '/'
        }

    fun calculate(a: Int, b: Int): Int =
        when(this){
            ADDITION -> a + b
            SUBTRACTION -> a - b
            MULTIPLICATION -> a * b
            DIVISION -> a / b
        }
}