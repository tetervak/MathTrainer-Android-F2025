package ca.tetervak.mathtrainer.data.repository

import ca.tetervak.mathtrainer.data.database.LocalProblem
import ca.tetervak.mathtrainer.data.database.LocalProblemDao
import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.domain.DivisionProblem
import ca.tetervak.mathtrainer.domain.MultiplicationProblem
import ca.tetervak.mathtrainer.domain.SubtractionProblem
import ca.tetervak.mathtrainer.domain.UserProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalUserProblemRepository @Inject constructor(
    val dao: LocalProblemDao
) : UserProblemRepository {

    override fun getAllUserProblemsFlow(): Flow<List<UserProblem>> =
        dao.getAllLocalProblemsFlow()
            .map { list -> list.map { localProblem -> localProblem.toUserProblem() } }
            .flowOn(context = Dispatchers.IO)

    override fun getUserProblemFlowById(id: Int): Flow<UserProblem?> =
        dao.getLocalProblemFlowById(id)
            .map { localProblem -> localProblem?.toUserProblem() }
            .flowOn(context = Dispatchers.IO)


    override suspend fun updateUserProblemById(
        id: Int,
        userAnswer: String?
    ) = withContext(context = Dispatchers.IO) {
        dao.updateLocalProblemById(id, userAnswer)
    }

    override suspend fun resetUserProblemById(id: Int) =
        withContext(context = Dispatchers.IO) {
            dao.updateLocalProblemById(
                id = id,
                userAnswer = null
            )
        }

    override suspend fun insertUserProblems(list: List<UserProblem>) =
        withContext(context = Dispatchers.IO) {
            dao.insertLocalProblems(list = list.map { userProblem -> userProblem.toLocalProblem() })
        }

    override suspend fun emptyAndInsertUserProblems(list: List<UserProblem>) =
        withContext(context = Dispatchers.IO) {
            dao.emptyAndInsertLocalProblems(
                list = list.map { userProblem -> userProblem.toLocalProblem() }
            )
        }

    override suspend fun getUserProblemCount(): Int =
        withContext(context = Dispatchers.IO) {
            dao.getLocalProblemCount()
        }

    override suspend fun isEmpty(): Boolean = (getUserProblemCount() == 0)
}

fun LocalProblem.toUserProblem(): UserProblem =
    UserProblem(
        problem = when (op) {
            '+' -> AdditionProblem(a, b)
            '-' -> SubtractionProblem(a, b)
            'x' -> MultiplicationProblem(a, b)
            '/' -> DivisionProblem(a, b)
            else -> throw IllegalArgumentException("Invalid operator: $op")
        },
        userAnswer = userAnswer,
        id = id
    )

fun UserProblem.toLocalProblem(): LocalProblem =
    when (problem) {
        is AdditionProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = '+',
            b = this.problem.b,
            userAnswer = this.userAnswer
        )

        is SubtractionProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = '-',
            b = this.problem.b,
            userAnswer = this.userAnswer
        )

        is MultiplicationProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = 'x',
            b = this.problem.b,
            userAnswer = this.userAnswer
        )

        is DivisionProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = '/',
            b = this.problem.b,
            userAnswer = this.userAnswer
        )

        else -> throw IllegalArgumentException("Invalid problem: $problem")
    }