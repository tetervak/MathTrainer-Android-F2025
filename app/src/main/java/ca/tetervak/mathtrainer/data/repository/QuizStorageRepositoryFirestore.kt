package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.firestore.FirestoreDataSource
import ca.tetervak.mathtrainer.domain.model.AlgebraProblem
import ca.tetervak.mathtrainer.domain.model.AnswerStatus
import ca.tetervak.mathtrainer.domain.model.Problem
import ca.tetervak.mathtrainer.domain.model.Quiz
import ca.tetervak.mathtrainer.domain.model.QuizScore
import ca.tetervak.mathtrainer.domain.model.QuizStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuizStorageRepositoryFirestore(
    private val dataSource: FirestoreDataSource,
    private val dispatcher: CoroutineDispatcher
) : QuizStorageRepository {

    @Inject
    constructor(
        dataSource: FirestoreDataSource,
    ) : this(
        dataSource = dataSource,
        dispatcher = Dispatchers.IO
    )

    override fun getUserQuizzesFlow(): Flow<List<Quiz>> =
        dataSource
            .getUserQuizzesFlow()
            .map { docList ->
                docList.map { doc -> doc.toDomain() }
            }
            .flowOn(context = dispatcher)

    override fun getQuizByIdFlow(quizId: String): Flow<Quiz?> =
        dataSource
            .getQuizByIdFlow(quizId = quizId)
            .map { doc ->
                doc?.toDomain()
            }
            .flowOn(context = dispatcher)

    override fun getQuizCountFlow(): Flow<Int> =
        dataSource
            .getUserQuizCountFlow()
            .flowOn(context = dispatcher)

    override suspend fun insertQuizWithProblems(
        problems: List<AlgebraProblem>
    ) {
        withContext(context = dispatcher) {
            dataSource.createQuizWithProblems(
                problems = problems.map { algebraProblem ->
                    algebraProblem.toDoc()
                }
            )
        }
    }

    override suspend fun getQuizProblemCount(quizId: String): Int =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemCount(quizId = quizId)
        }

    override fun getQuizProblemCountFlow(quizId: String): Flow<Int> =
        dataSource
            .getQuizProblemCountFlow(quizId = quizId)
            .flowOn(context = dispatcher)

    override suspend fun deleteQuizWithProblems(quizId: String) =
        withContext(context = dispatcher) {
            dataSource.deleteQuizWithProblems(quizId = quizId)
        }

    override suspend fun getQuizNumber(quizId: String): Int =
        withContext(context = dispatcher) {
            dataSource.getQuizNumber(quizId = quizId)
        }

    override fun getQuizProblemsFlow(quizId: String): Flow<List<Problem>> =
        dataSource
            .getQuizProblemsFlow(quizId = quizId)
            .map { docList ->
                docList.map { doc -> doc.toDomain() }
            }
            .flowOn(context = dispatcher)

    override fun getProblemByIdFlow(problemId: String): Flow<Problem?> =
        dataSource
            .getProblemByIdFlow(problemId = problemId)
            .map { doc ->
                doc?.toDomain()
            }
            .flowOn(context = dispatcher)

    override suspend fun updateProblem(problem: Problem) = withContext(context = dispatcher) {
        dataSource.updateUserAnswerAndAnswerStatus(
            problemId = problem.id,
            userAnswer = problem.userAnswer,
            answerStatus = problem.answerStatus
        )
    }

    override suspend fun getNextProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemByNumber(
                quizId = problem.quizId,
                problemNumber = problem.problemNumber + 1
            )?.id
        }

    override suspend fun getPreviousProblemId(problem: Problem): String? =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemByNumber(
                quizId = problem.quizId,
                problemNumber = problem.problemNumber - 1
            )?.id
        }

    override suspend fun getFirstProblemId(quizId: String): String? =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemByNumber(
                quizId = quizId,
                problemNumber = 1
            )?.id
        }

    override suspend fun getQuizScore(quizId: String): QuizScore =
        withContext(dispatcher) {
            QuizScore(
                problemCount = getQuizProblemCount(quizId),
                rightAnswers = getNumberOfRightAnswers(quizId)
            )
        }

    override fun getQuizScoreFlow(quizId: String): Flow<QuizScore> {
        val quizScoreFlow: Flow<QuizScore> = combine(
            dataSource.getQuizProblemCountFlow(quizId = quizId),
            dataSource.getQuizProblemCountByStatusFlow(
                quizId = quizId,
                answerStatus = AnswerStatus.RIGHT_ANSWER
            )
        ) { problemCount, rightAnswerCount ->
            QuizScore(
                problemCount = problemCount,
                rightAnswers = rightAnswerCount
            )
        }
        return quizScoreFlow.flowOn(context = dispatcher)
    }

    override fun getQuizStatusDataFlow(quizId: String): Flow<QuizStatus> {
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
        ) { problemCount, rightAnswerCount, notAnsweredCount, wrongAnswerCount ->
            QuizStatus(
                problemCount = problemCount,
                rightAnswers = rightAnswerCount,
                notAnswered = notAnsweredCount,
                wrongAnswers = wrongAnswerCount
            )
        }
        return quizStatusFlow.flowOn(context = dispatcher)
    }

    override suspend fun getNumberOfRightAnswers(quizId: String): Int =
        withContext(context = dispatcher) {
            dataSource.getQuizProblemCountByStatus(
                quizId = quizId,
                answerStatus = AnswerStatus.RIGHT_ANSWER
            )
        }
}