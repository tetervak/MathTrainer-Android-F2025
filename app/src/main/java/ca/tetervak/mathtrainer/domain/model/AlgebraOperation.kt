package ca.tetervak.mathtrainer.domain.model

enum class AlgebraOperation {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION;

    val symbol: Char
        get() = when(this) {
            ADDITION -> '+'
            SUBTRACTION -> '-'
            MULTIPLICATION -> 'x'
            DIVISION -> '/'
        }

    fun calculate(firstNumber: Int, secondNumber: Int): Int =
        when(this){
            ADDITION -> firstNumber + secondNumber
            SUBTRACTION -> firstNumber - secondNumber
            MULTIPLICATION -> firstNumber * secondNumber
            DIVISION -> firstNumber / secondNumber
        }

    companion object {
        fun fromSymbol(symbol: Char): AlgebraOperation =
            when(symbol) {
                '+' -> ADDITION
                '-' -> SUBTRACTION
                'x' -> MULTIPLICATION
                '/' -> DIVISION
                else -> throw IllegalArgumentException("Invalid operation symbol: $symbol")
            }
    }
}