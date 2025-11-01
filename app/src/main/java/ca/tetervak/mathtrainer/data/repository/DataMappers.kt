package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.LocalProblem
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.UserProblem

fun LocalProblem.toUserProblem(): UserProblem =
    UserProblem(
        problem = AlgebraProblem(a, b, op),
        userAnswer = userAnswer,
        id = id
    )

fun UserProblem.toLocalProblem(): LocalProblem =
    this.problem.toLocalProblem(
        id = this.id,
        userAnswer = this.userAnswer,
        status = this.status
    )

fun AlgebraProblem.toLocalProblem(
    id: Int,
    userAnswer: String?,
    status: UserAnswerStatus
): LocalProblem = LocalProblem(
            id = id,
            a = this.a,
            op = this.op,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )