package ca.tetervak.mathtrainer.domain.model

class AlgebraProblem(
    val firstNumber: Int, // the first number in the problem
    val secondNumber: Int, // the second number in the problem
    val algebraOperation: AlgebraOperation // the binary operator between these numbers
) {
    val text: String = "$firstNumber ${algebraOperation.symbol} $secondNumber = ?"
    val answer: Int = algebraOperation.calculate(firstNumber, secondNumber)

    companion object{
        fun fromText(text: String): AlgebraProblem {
            val parts: List<String> = text.split(" ", "=", limit = 4)
            val a: Int = parts[0].toInt()
            val op: AlgebraOperation = AlgebraOperation.fromSymbol(symbol = parts[1].first())
            val b: Int = parts[2].toInt()
            return AlgebraProblem(firstNumber = a, secondNumber = b, algebraOperation = op)
        }
    }
}