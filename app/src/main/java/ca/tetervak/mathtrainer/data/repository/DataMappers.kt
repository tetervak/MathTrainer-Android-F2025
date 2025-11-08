package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.UserAnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import java.util.UUID

fun ProblemEntity.toDomain(): Problem =
    Problem(
        problem = AlgebraProblem(a, b, op),
        userAnswer = userAnswer,
        id = pId,
        order = order,
        quizId = quizId
    )

fun Problem.toEntity(): ProblemEntity =
    this.problem.toEntity(
        id = this.id,
        quizId = this.quizId,
        order = this.order,
        userAnswer = this.userAnswer,
        status = this.status
    )

fun AlgebraProblem.toEntity(
    id: String = UUID.randomUUID().toString(),
    quizId: String,
    order: Int,
    userAnswer: String?,
    status: UserAnswerStatus
): ProblemEntity = ProblemEntity(
            pId = id,
            quizId = quizId,
            order = order,
            a = this.a,
            op = this.op,
            b = this.b,
            answer = this.answer,
            userAnswer = userAnswer,
            status = status
        )

fun QuizEntity.toDomain(): Quiz = Quiz(
    id = this.qId,
    order = this.order,
    userId = this.userId
)

fun Quiz.toEntity(): QuizEntity = QuizEntity(
    qId = this.id,
    order = this.order,
    userId = this.userId
)