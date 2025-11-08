/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.tetervak.mathtrainer.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import ca.tetervak.mathtrainer.ui.problem.details.ProblemDetailsScreen
import ca.tetervak.mathtrainer.ui.home.HomeScreen
import ca.tetervak.mathtrainer.ui.problem.list.ProblemListScreen
import ca.tetervak.mathtrainer.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object Home: NavKey

@Serializable
object Settings: NavKey

@Serializable
data class ProblemDetails(val problemId: String): NavKey

@Serializable
data class ProblemList(
    val selectedId: String? = null,
): NavKey

@Serializable
data class QuizDetails(
    val quizId: String,
): NavKey

@Serializable
data class QuizList(
    val selectedId: String? = null,
): NavKey

@Composable
fun AppRootScreen() {

    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    // Create a back stack, specifying the key the app should start with
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(Home)


    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key){
                    HomeScreen(
                        onFirstProblemClick = { backStack.add(ProblemDetails(problemId = "?")) },
                        onListProblemsClick = { backStack.add(ProblemList()) },
                        onSettingsClick = { backStack.add(Settings) },
                        onHelpClick = { showAboutDialog = true }
                    )
                }
                is QuizList -> NavEntry(key) {

                }
                is QuizDetails -> NavEntry(key) {

                }
                is ProblemList -> NavEntry(key){
                    val selectedId: String? = key.selectedId
                    ProblemListScreen(
                        selectedId = selectedId,
                        onProblemClick = { problemId ->
                            backStack.add(ProblemDetails(problemId = problemId))
                        },
                        onHomeClick = { backStack.removeIf { it !is Home } },
                        onHelpClick = { showAboutDialog = true }
                    )
                }
                is ProblemDetails -> NavEntry(key) {
                    val problemId: String = key.problemId
                    ProblemDetailsScreen(
                        problemId = problemId,
                        onHelpClick = { showAboutDialog = true },
                        onHomeClick = { backStack.removeIf { it !is Home } },
                        onListProblemsClick = { backStack.add(ProblemList(selectedId = problemId)) },
                        onProblemClick = { problemId ->
                           backStack.add(ProblemDetails(problemId = problemId))
                        },
                        onQuizClick = { quizId ->
                            backStack.add(QuizDetails(quizId = quizId))
                        },
                        onBackClick = { backStack.removeLastOrNull() }
                    )
                }
                is Settings -> NavEntry(key) {
                    SettingsScreen(
                        onHelpClick = { showAboutDialog = true },
                        onHomeClick = { backStack.removeIf { it !is Home } }
                    )
                }
                else -> NavEntry(key) { Text("Unknown route") }
            }

        }
    )

    if (showAboutDialog) {
        AboutDialog(onDismissRequest = { showAboutDialog = false })
    }
}