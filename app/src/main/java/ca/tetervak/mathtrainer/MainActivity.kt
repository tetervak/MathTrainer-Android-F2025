package ca.tetervak.mathtrainer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import ca.tetervak.mathtrainer.ui.AppRootScreen
import ca.tetervak.mathtrainer.ui.getScreenVariant
import ca.tetervak.mathtrainer.ui.theme.MathTrainerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathTrainerTheme {
                val windowSize: WindowSizeClass = calculateWindowSizeClass(this)
                val windowWidth: WindowWidthSizeClass = windowSize.widthSizeClass
                val windowHeight: WindowHeightSizeClass = windowSize.heightSizeClass
                Log.d("MainActivity", "onCreate: windowWidth = $windowWidth")
                Log.d("MainActivity", "onCreate: windowHeight = $windowHeight")
                val screenVariant = getScreenVariant(windowWidth, windowHeight)
                Log.d("MainActivity", "onCreate: screenVariant = $screenVariant")
                AppRootScreen(screenVariant)
            }
        }
    }
}