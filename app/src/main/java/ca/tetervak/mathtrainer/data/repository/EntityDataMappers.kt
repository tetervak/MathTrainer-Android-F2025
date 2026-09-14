package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import java.util.UUID

fun ProblemEntity.toDomain(): Problem =
    Problem(
        id = id,
        quizId = quizId,
        problemNumber = problemNumber,
        text = text,
        correctAnswer = correctAnswer,
        userAnswer = userAnswer
    )

fun AlgebraProblem.toEntity(
    id: String = UUID.randomUUID().toString(),
    quizId: String,
    problemNumber: Int,
    userAnswer: String?,
    answerStatus: AnswerStatus
): ProblemEntity = ProblemEntity(
    id = id,
    quizId = quizId,
    problemNumber = problemNumber,
    text = text,
    correctAnswer = answer,
    userAnswer = userAnswer,
    answerStatus = answerStatus
)

fun QuizEntity.toDomain(): Quiz = Quiz(
    id = this.id,
    quizNumber = this.quizNumber
)