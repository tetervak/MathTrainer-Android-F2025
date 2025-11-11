package ca.tetervak.mathtrainer.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.dao.UserDao
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.data.database.entity.UserEntity
import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@InstallIn(SingletonComponent::class)
@EntryPoint
interface DatabaseCallbackEntryPoint {
    fun userDao(): UserDao
    fun quizDao(): QuizDao
    fun problemDao(): ProblemDao
}

class PrepopulateCallback(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val entryPoint = EntryPointAccessors.fromApplication(
                context,
                DatabaseCallbackEntryPoint::class.java
            )

            val userDao = entryPoint.userDao()
            val quizDao = entryPoint.quizDao()
            val problemDao = entryPoint.problemDao()

            prepopulate(userDao, quizDao, problemDao)
        }
    }

    private suspend fun prepopulate(userDao: UserDao, quizDao: QuizDao, problemDao: ProblemDao) {
        val demoUser = UserEntity.demoUser
        userDao.insert(demoUser)

        val quizId = UUID.randomUUID().toString()
        val problems = listOf(
            ProblemEntity(
                quizId = quizId,
                problemNumber = 1,
                firstNumber = 1,
                secondNumber = 2,
                algebraOperation = AlgebraOperation.ADDITION
            ),
            ProblemEntity(
                quizId = quizId,
                problemNumber = 2,
                firstNumber = 6,
                secondNumber = 4,
                algebraOperation = AlgebraOperation.SUBTRACTION
            ),
            ProblemEntity(
                quizId = quizId,
                problemNumber = 3,
                firstNumber = 2,
                secondNumber = 4,
                algebraOperation = AlgebraOperation.MULTIPLICATION
            ),

        )
        quizDao.insertQuizWithProblems(
            userId = UserEntity.demoUser.id,
            problems = problems
        )
    }
}