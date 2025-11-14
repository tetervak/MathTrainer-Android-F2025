package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.firestore.FirestoreDataSource
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreQuizRepository(
    private val dataSource: FirestoreDataSource,
    private val dispatcher: CoroutineDispatcher
) {

    @Inject
    constructor(
        dataSource: FirestoreDataSource,
    ) : this(
        dataSource = dataSource,
        dispatcher = Dispatchers.IO
    )

    fun getUserQuizzesFlow(): Flow<List<Quiz>> =
        dataSource
            .getUserQuizzesFlow()
            .map { docList ->
                docList.map { doc -> doc.toDomain() }
            }
            .flowOn(context = dispatcher)

    fun getQuizByIdFlow(quizId: String): Flow<Quiz?> =
        dataSource
            .getQuizByIdFlow(quizId = quizId)
            .map { doc ->
                doc?.toDomain()
            }
            .flowOn(context = dispatcher)

    fun getQuizCountFlow(): Flow<Int> =
        dataSource
            .getUserQuizCountFlow()
            .flowOn(context = dispatcher)

    suspend fun createQuizWithProblems(
        problems: List<AlgebraProblem>
    ) = withContext(context = dispatcher) {
        dataSource.createQuizWithProblems(
            problems = problems.map { algebraProblem ->
                algebraProblem.toDoc()
            }
        )
    }

    suspend fun getQuizProblemCount(quizId: String): Int =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemCount(quizId = quizId)
        }

    suspend fun deleteQuizWithProblems(quizId: String) =
        withContext(context = dispatcher) {
            dataSource.deleteQuizWithProblems(quizId = quizId)
        }

    suspend fun getQuizNumber(quizId: String): Int =
        withContext(context = dispatcher){
            dataSource.getQuizNumber(quizId = quizId)
        }

    fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>> =
        dataSource
            .getQuizProblemsFlow(quizId = quizId)
            .map { docList ->
                docList.map { doc -> doc.toDomain() }
            }
            .flowOn(context = dispatcher)

    fun getProblemByIdFlow(problemId: String): Flow<Problem?> =
        dataSource
            .getProblemByIdFlow(problemId = problemId)
            .map { doc ->
                doc?.toDomain()
            }
            .flowOn(context = dispatcher)

    suspend fun updateUserAnswerAndAnswerStatus(
        problemId: String,
        userAnswer: String,
        answerStatus: AnswerStatus
    ) = withContext(context = dispatcher) {
        dataSource.updateUserAnswerAndAnswerStatus(
            problemId = problemId,
            userAnswer = userAnswer,
            answerStatus = answerStatus
        )
    }

    suspend fun getNextProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemByNumber(
                quizId = problem.id,
                problemNumber = problem.problemNumber + 1
            )?.id
        }

    suspend fun getPreviousProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemByNumber(
                quizId = problem.id,
                problemNumber = problem.problemNumber - 1
            )?.id
        }

    suspend fun getFirstProblemId(quizId: String): String? =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemByNumber(
                quizId = quizId,
                problemNumber = 1
            )?.id
        }

    fun getStatusDataFlow(quizId: String): Flow<QuizStatus> {
        val quizStatusFlow: Flow<QuizStatus> = combine(
            dataSource.getQuizProblemCountFlow(quizId = quizId),
            dataSource.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                answerStatus = AnswerStatus.RIGHT_ANSWER
            ),
            dataSource.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                answerStatus = AnswerStatus.NOT_ANSWERED
            ),
            dataSource.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                answerStatus = AnswerStatus.WRONG_ANSWER
            )
        ){ problemCount, rightAnswerCount, notAnsweredCount, wrongAnswerCount ->
            QuizStatus(
                problemCount = problemCount,
                rightAnswers = rightAnswerCount,
                notAnswered = notAnsweredCount,
                wrongAnswers = wrongAnswerCount
            )
        }
        return quizStatusFlow.flowOn(context = dispatcher)
    }

    suspend fun getNumberOfRightAnswers(quizId: String): Int =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemCountByStatus(
                quizId = quizId,
                answerStatus = AnswerStatus.RIGHT_ANSWER
            )
        }
}