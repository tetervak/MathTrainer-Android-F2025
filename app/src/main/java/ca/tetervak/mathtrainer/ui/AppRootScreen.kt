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
import ca.tetervak.mathtrainer.ui.quiz.details.QuizDetailsScreen
import ca.tetervak.mathtrainer.ui.quiz.list.QuizListScreen
import ca.tetervak.mathtrainer.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object Home : NavKey

@Serializable
object Settings : NavKey

@Serializable
data class ProblemDetails(val problemId: String) : NavKey

@Serializable
data class ProblemList(
    val quizId: String,
    val selectedId: String? = null,
) : NavKey

@Serializable
data class QuizDetails(
    val quizId: String,
) : NavKey

@Serializable
object QuizList: NavKey

@Composable
fun AppRootScreen() {

    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val onHelpClick: () -> Unit = { showAboutDialog = true }
    val onDismissHelpClick: () -> Unit = { showAboutDialog = false }

    val backStack: NavBackStack<NavKey> = rememberNavBackStack(Home)
    val onBackClick: () -> Unit = { backStack.removeLastOrNull() }
    val onHomeClick: () -> Unit = { backStack.removeIf { it !is Home } }
    val onListQuizzesClick: () -> Unit = { backStack.add(QuizList) }
    val onSettingsClick: () -> Unit = { backStack.add(Settings) }
    val onQuizClick: (String) -> Unit = { quizId ->
        backStack.add(QuizDetails(quizId = quizId))
    }
    val onProblemClick: (String) -> Unit = { problemId ->
        backStack.add(ProblemDetails(problemId = problemId))
    }
    val onListProblemsClick: (String, String?) -> Unit = { quizId, selectedId ->
        backStack.add(ProblemList(quizId = quizId, selectedId = selectedId))
    }

    NavDisplay(
        backStack = backStack,
        onBack = onBackClick,
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key) {
                    HomeScreen(
                        onListQuizzesClick = onListQuizzesClick,
                        onSettingsClick = onSettingsClick,
                        onHelpClick = onHelpClick
                    )
                }

                is QuizList -> NavEntry(key) {
                    QuizListScreen(
                        onHomeClick = onHomeClick,
                        onQuizClick = onQuizClick,
                        onBackClick = onBackClick,
                        onHelpClick = onHelpClick,
                    )
                }

                is ProblemList -> NavEntry(key) {
                    val quizId: String = key.quizId
                    val selectedId: String? = key.selectedId
                    ProblemListScreen(
                        quizId = quizId,
                        selectedId = selectedId,
                        onProblemClick = onProblemClick,
                        onHomeClick = onHomeClick,
                        onHelpClick = onHelpClick,
                        onBackClick = onBackClick,
                        onQuizClick = onQuizClick
                    )
                }

                is ProblemDetails -> NavEntry(key) {
                    val problemId: String = key.problemId
                    ProblemDetailsScreen(
                        problemId = problemId,
                        onHelpClick = onHelpClick,
                        onHomeClick = onHomeClick,
                        onListProblemsClick = onListProblemsClick,
                        onProblemClick = onProblemClick,
                        onQuizClick = onQuizClick,
                        onBackClick = onBackClick
                    )
                }

                is QuizDetails -> NavEntry(key) {
                    val quizId: String = key.quizId
                    QuizDetailsScreen(
                        quizId = quizId,
                        onHomeClick = onHomeClick,
                        onProblemClick = onProblemClick,
                        onListProblemsClick = onListProblemsClick,
                        onBackClick = onBackClick,
                        onHelpClick = onHelpClick
                    )
                }

                is Settings -> NavEntry(key) {
                    SettingsScreen(
                        onHelpClick = onHelpClick,
                        onHomeClick = onHomeClick,
                        onBackClick = onBackClick
                    )
                }

                else -> NavEntry(key) { Text("Unknown route") }
            }

        }
    )

    if (showAboutDialog) {
        AboutDialog(onDismissRequest = onDismissHelpClick)
    }
}