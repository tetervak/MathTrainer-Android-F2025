package ca.tetervak.mathtrainer.data.firestore

import androidx.test.ext.junit.runners.AndroidJUnit4
import ca.tetervak.mathtrainer.data.firestore.docs.fromMap
import ca.tetervak.mathtrainer.data.firestore.docs.toMap
import ca.tetervak.mathtrainer.util.FirebaseEmulatorRule
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FirestoreDataSourceTestScratch {

    @get:Rule
    val emulatorRule = FirebaseEmulatorRule()

    private val db = FirebaseFirestore.getInstance()

    private val userId = "userId"

    private suspend fun insertUser(quizCount: Int = 0){
        val docRef = db.collection("users").document(userId)
        val user = FirestoreUser(quizCount = quizCount)
        docRef.set(user.toMap()).await()
    }

    private suspend fun updateQuizCount(count: Int) {
        db.collection("users")
            .document(userId)
            .update("quizCount", count).await()
    }

    private suspend fun getQuizCount(): Int {
        val docSnap = db.collection("users").document(userId).get().await()
        assertNotNull(docSnap.data)
        return (docSnap.data!!["quizCount"] as Long).toInt()
    }

    @Test
    fun testInsertUser() {
        runBlocking {
            insertUser(quizCount = 5)
            val docSnap = db.collection("users").document(userId).get().await()
            assertNotNull(docSnap.data)
            val user: FirestoreUser =
                FirestoreUser.fromMap(userId, docSnap.data!!)
            assertEquals(5, user.quizCount)
        }

    }

    @Test
    fun testGetQuizCount() {
        runBlocking {
            insertUser(quizCount = 6)
            //updateQuizCount(10)
            val quizCount = getQuizCount()
            println("Quiz count: $quizCount")
            assertEquals(6, quizCount)
        }
    }

    @Test
    fun testUpdateQuizCount() {
        runBlocking {
            insertUser(quizCount = 6)
            updateQuizCount(10)
            val quizCount = getQuizCount()
            assertEquals(10, quizCount)
        }
    }

}