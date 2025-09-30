package ca.tetervak.mathtrainer.data.local

import ca.tetervak.mathtrainer.data.UserProblemRepository
import ca.tetervak.mathtrainer.domain.AdditionProblem
import ca.tetervak.mathtrainer.domain.AlgebraProblem
import ca.tetervak.mathtrainer.domain.DivisionProblem
import ca.tetervak.mathtrainer.domain.MultiplicationProblem
import ca.tetervak.mathtrainer.domain.SubtractionProblem
import ca.tetervak.mathtrainer.domain.UserProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalUserProblemRepository(val dao: LocalProblemDao): UserProblemRepository {

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
        userAnswer: String?,
        status: UserProblem.Status
    ) = withContext(context = Dispatchers.IO){
        dao.updateLocalProblemById(id, userAnswer, status)
    }

    override suspend fun resetUserProblemById(id: Int) =
        withContext(context = Dispatchers.IO){
            dao.updateLocalProblemById(
                id = id,
                userAnswer = null,
                status = UserProblem.Status.NOT_ANSWERED
            )
        }

    override suspend fun insertUserProblems(list: List<UserProblem>) =
        withContext(context = Dispatchers.IO){
            dao.insertLocalProblems(list = list.map { userProblem -> userProblem.toLocalProblem() })
        }

    override suspend fun emptyAndInsertUserProblems(list: List<UserProblem>) =
        withContext(context = Dispatchers.IO){
            dao.emptyAndInsertLocalProblems(
                list = list.map { userProblem -> userProblem.toLocalProblem() }
            )
        }

    override suspend fun getUserProblemCount(): Int =
        withContext(context = Dispatchers.IO){
            dao.getLocalProblemCount()
        }

    override suspend fun isEmpty(): Boolean = (getUserProblemCount() == 0)
}

fun LocalProblem.toUserProblem(): UserProblem  =
        UserProblem(
            problem = when(op){
                '+' -> AdditionProblem(a, b)
                '-' -> SubtractionProblem(a, b)
                'x' -> MultiplicationProblem(a, b)
                '/' -> DivisionProblem(a, b)
                else -> throw IllegalArgumentException("Invalid operator: $op")
            }
        ).also { userProblem ->
            userProblem.userAnswer = userAnswer
            userProblem.status = status
        }

fun UserProblem.toLocalProblem(): LocalProblem =
    when(problem){
        is AdditionProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = '+',
            b = this.problem.b,
            userAnswer = this.userAnswer,
            status = this.status
        )
        is SubtractionProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = '-',
            b = this.problem.b,
            userAnswer = this.userAnswer,
            status = this.status
        )
        is MultiplicationProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = 'x',
            b = this.problem.b,
            userAnswer = this.userAnswer,
            status = this.status
        )
        is DivisionProblem -> LocalProblem(
            id = this.id,
            a = this.problem.a,
            op = '/',
            b = this.problem.b,
            userAnswer = this.userAnswer,
            status = this.status
        )
        else -> throw IllegalArgumentException("Invalid problem: $problem")
    }