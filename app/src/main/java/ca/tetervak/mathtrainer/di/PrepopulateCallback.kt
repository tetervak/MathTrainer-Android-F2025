package ca.tetervak.mathtrainer.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.dao.UserDao
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.data.database.entity.QuizEntity
import ca.tetervak.mathtrainer.data.database.entity.UserEntity
import ca.tetervak.mathtrainer.domain.model.AlgebraOperation
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        // 1. Insert default user
        val demoUser = UserEntity.demoUser
        userDao.insert(demoUser)

        // 2. Insert default quiz
        val quiz = QuizEntity(
            userId = demoUser.uId,
            order = 1
        )
        quizDao.insertQuiz(quiz)

        // 3. Insert sample problems
        val problems = listOf(
            ProblemEntity(
                quizId = quiz.qId,
                order = 1,
                a = 1,
                b = 2,
                op = AlgebraOperation.ADDITION
            ),
            ProblemEntity(
                quizId = quiz.qId,
                order = 2,
                a = 6,
                b = 3,
                op = AlgebraOperation.SUBTRACTION
            ),
            ProblemEntity(
                quizId = quiz.qId,
                order = 2,
                a = 2,
                b = 4,
                op = AlgebraOperation.MULTIPLICATION
            ),

        )
        problemDao.insertProblems(entities = problems)
    }
}