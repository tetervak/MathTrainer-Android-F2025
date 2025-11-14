package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.firestore.docs.ProblemDoc
import ca.tetervak.mathtrainer.data.firestore.docs.QuizDoc
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz

fun ProblemDoc.toDomain(): Problem =
    Problem(
        id = id,
        quizId = quizId,
        problemNumber = problemNumber,
        text = text,
        correctAnswer = correctAnswer,
        userAnswer = userAnswer
    )

fun AlgebraProblem.toDoc() =
    ProblemDoc(
        text = text,
        correctAnswer = answer
    )

fun QuizDoc.toDomain(): Quiz =
        Quiz(
            id = id,
            quizNumber = quizNumber
        )
