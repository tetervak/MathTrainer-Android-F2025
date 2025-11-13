package ca.tetervak.mathtrainer.util

// In app/src/androidTest/java/ca/tetervak/mathtrainer/util/FirebaseEmulatorRule.kt
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class FirebaseEmulatorRule : TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)
        // Use 10.0.2.2 for the Android emulator to connect to localhost on the host machine
        FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080)
    }
}
