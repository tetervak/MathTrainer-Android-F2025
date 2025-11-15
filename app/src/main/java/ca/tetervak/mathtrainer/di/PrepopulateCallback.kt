package ca.tetervak.mathtrainer.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ca.tetervak.mathtrainer.data.database.dao.ProblemDao
import ca.tetervak.mathtrainer.data.database.dao.QuizDao
import ca.tetervak.mathtrainer.data.database.dao.UserDao
import ca.tetervak.mathtrainer.data.database.entity.ProblemEntity
import ca.tetervak.mathtrainer.data.database.entity.UserEntity
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
                text = "1 + 2 = ?",
                correctAnswer = 3,
            ),
            ProblemEntity(
                quizId = quizId,
                problemNumber = 2,
                text = "6 - 4 = ?",
                correctAnswer = 2,
            ),
            ProblemEntity(
                quizId = quizId,
                problemNumber = 3,
                text = "2 x 4 = ?",
                correctAnswer = 8
            ),

        )
        quizDao.insertQuizWithProblems(
            userId = UserEntity.demoUser.id,
            problems = problems
        )
    }
}