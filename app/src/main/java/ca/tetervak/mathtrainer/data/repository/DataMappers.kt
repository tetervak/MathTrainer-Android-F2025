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
        algebraProblem = AlgebraProblem(firstNumber, secondNumber, algebraOperation),
        userAnswer = userAnswer,
        id = id,
        problemNumber = problemNumber,
        quizId = quizId
    )

fun Problem.toEntity(): ProblemEntity =
    this.algebraProblem.toEntity(
        id = this.id,
        quizId = this.quizId,
        problemNumber = this.problemNumber,
        userAnswer = this.userAnswer,
        status = this.status
    )

fun AlgebraProblem.toEntity(
    id: String = UUID.randomUUID().toString(),
    quizId: String,
    problemNumber: Int,
    userAnswer: String?,
    status: AnswerStatus
): ProblemEntity = ProblemEntity(
            id = id,
            quizId = quizId,
            problemNumber = problemNumber,
            firstNumber = this.firstNumber,
            algebraOperation = this.algebraOperation,
            secondNumber = this.secondNumber,
            correctAnswer = this.answer,
            userAnswer = userAnswer,
            answerStatus = status
        )

fun QuizEntity.toDomain(): Quiz = Quiz(
    id = this.id,
    quizNumber = this.quizNumber,
    userId = this.userId
)

fun Quiz.toEntity(): QuizEntity = QuizEntity(
    id = this.id,
    quizNumber = this.quizNumber,
    userId = this.userId
)