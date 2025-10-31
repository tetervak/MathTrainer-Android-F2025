package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.LocalProblem
import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.data.database.AlgebraOperator
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.DivisionProblem
import ca.tetervak.mathtrainer.domain.MultiplicationProblem
import ca.tetervak.mathtrainer.domain.SubtractionProblem
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.UserProblem

fun LocalProblem.toUserProblem(): UserProblem =
    UserProblem(
        problem = when (op) {
            AlgebraOperator.PLUS -> AdditionProblem(a, b)
            AlgebraOperator.MINUS -> SubtractionProblem(a, b)
            AlgebraOperator.MULTIPLY -> MultiplicationProblem(a, b)
            AlgebraOperator.DIVIDE -> DivisionProblem(a, b)
        },
        userAnswer = userAnswer,
        id = id
    )

fun UserProblem.toLocalProblem(): LocalProblem =
    this.problem.toLocalProblem(id = this.id, userAnswer = this.userAnswer, status = this.status)


fun AlgebraProblem.toLocalProblem(id: Int, userAnswer: String?, status: UserAnswerStatus): LocalProblem =
    when (this) {
        is AdditionProblem -> LocalProblem(
            id = id,
            a = this.a,
            op = AlgebraOperator.PLUS,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )

        is SubtractionProblem -> LocalProblem(
            id = id,
            a = this.a,
            op = AlgebraOperator.MINUS,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )

        is MultiplicationProblem -> LocalProblem(
            id = id,
            a = this.a,
            op = AlgebraOperator.MULTIPLY,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )

        is DivisionProblem -> LocalProblem(
            id = id,
            a = this.a,
            op = AlgebraOperator.DIVIDE,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )
    }