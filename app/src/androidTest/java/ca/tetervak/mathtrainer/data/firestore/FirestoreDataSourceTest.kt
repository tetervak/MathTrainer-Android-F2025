package ca.tetervak.mathtrainer.data.firestore

import androidx.test.ext.junit.runners.AndroidJUnit4
import ca.tetervak.mathtrainer.data.firestore.docs.FdsProblem
import ca.tetervak.mathtrainer.data.firestore.docs.QuizDoc
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import ca.tetervak.mathtrainer.domain.model.AlgebraOperation

@RunWith(AndroidJUnit4::class)
class FirestoreDataSourceTest {

    // --- Dependencies ---
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var dataSource: FirestoreDataSourceOld

    // --- Test Data ---
    private val testUserId = "testUser123"
    private val testUserEmail = "test@example.com"
    private val testUserPassword = "password"

    @Before
    fun setup() {
        // 1. Get dependency instances
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // 2. Point them to the emulators
        firestore.useEmulator("10.0.2.2", 8080)
        auth.useEmulator("10.0.2.2", 9099)

        // Configure Firestore for testing
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
        firestore.firestoreSettings = settings

        // 3. Create the class we want to test
        dataSource = FirestoreDataSourceOld(firestore, auth)

        // 4. Sign in a test user to get a valid UID for the tests
        runBlocking {
            // Create a user in the Auth emulator
            auth.createUserWithEmailAndPassword(testUserEmail, testUserPassword).await()
            // Sign in to make this the `currentUser`
            auth.signInWithEmailAndPassword(testUserEmail, testUserPassword).await()
            // Verify we are signed in
            assertNotNull("User should be logged in for tests", auth.currentUser)
            assertEquals("Signed-in user ID should match test ID", auth.currentUser?.email, testUserEmail)
        }
    }

    @After
    fun tearDown() = runBlocking {
        // Clear data from Firestore
        // This is a simplified clear; for production tests, you'd use the Admin SDK in a script.
        val quizDocs = dataSource.quizzesCollection().get().await()
        for (doc in quizDocs) {
            doc.reference.delete().await()
        }

        // Delete the user from the Auth emulator and sign out
        auth.currentUser?.delete()?.await()
        auth.signOut()
    }

    @Test
    fun addAndGetQuiz_returnsCorrectData() = runBlocking {
        // ARRANGE
        val quiz = QuizDoc(quizNumber = 1, problemCount = 10)

        // ACT
        // Add the quiz using our data source
        val newQuizId = dataSource.addQuiz(quiz)
        assertNotNull("The returned quiz ID should not be null", newQuizId)

        // Retrieve the quiz using our data source
        val retrievedQuiz = dataSource.getQuizById(newQuizId)

        // ASSERT
        assertNotNull("Retrieved quiz should not be null", retrievedQuiz)
        assertEquals("Problem count should match", 10, retrievedQuiz?.problemCount)
        assertEquals("Quiz number should match", 1, retrievedQuiz?.quizNumber)
    }

    @Test
    fun addAndGetProblems_returnsCorrectData() = runBlocking {
        // ARRANGE: First, create a quiz to own the problems
        val quizId = dataSource.addQuiz(
            QuizDoc(quizNumber = 2, problemCount = 2)
        )

        val problems = listOf(
            FdsProblem(
                problemNumber = 1,
                firstNumber = 10,
                secondNumber = 5,
                algebraOperation = AlgebraOperation.ADDITION
            ),
            FdsProblem(
                problemNumber = 2,
                firstNumber = 20,
                secondNumber = 7,
                algebraOperation = AlgebraOperation.SUBTRACTION
            )
        )

        // ACT
        // Add the list of problems using the data source
        dataSource.addProblems(quizId, problems)

        // Retrieve the problems using the data source
        val retrievedProblems = dataSource.getProblems(quizId)

        // ASSERT
        assertEquals("Should retrieve 2 problems", 2, retrievedProblems.size)
        assertEquals("First problem operation should be ADD", AlgebraOperation.ADDITION, retrievedProblems[0].algebraOperation)
        assertEquals("Second problem number should be 20", 20, retrievedProblems[1].firstNumber)
    }
}
