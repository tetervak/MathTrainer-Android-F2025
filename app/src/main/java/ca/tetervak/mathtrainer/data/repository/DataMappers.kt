package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.model.UserProblem

fun ProblemEntity.toUserProblem(): UserProblem =
    UserProblem(
        problem = AlgebraProblem(a, b, op),
        userAnswer = userAnswer,
        id = id
    )

fun UserProblem.toLocalProblem(): ProblemEntity =
    this.problem.toLocalProblem(
        id = this.id,
        userAnswer = this.userAnswer,
        status = this.status
    )

fun AlgebraProblem.toLocalProblem(
    id: Int,
    userAnswer: String?,
    status: UserAnswerStatus
): ProblemEntity = ProblemEntity(
            id = id,
            a = this.a,
            op = this.op,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )