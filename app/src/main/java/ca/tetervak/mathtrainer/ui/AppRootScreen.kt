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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ca.tetervak.mathtrainer.ui.common.AboutDialog
import ca.tetervak.mathtrainer.ui.problem.details.ProblemDetailsScreen
import ca.tetervak.mathtrainer.ui.home.HomeScreen
import ca.tetervak.mathtrainer.ui.problem.list.ProblemListScreen
import ca.tetervak.mathtrainer.ui.quiz.details.QuizDetailsScreen
import ca.tetervak.mathtrainer.ui.quiz.list.QuizListScreen
import ca.tetervak.mathtrainer.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Settings

@Serializable
data class ProblemList(
    val quizId: String,
    val selectedId: String? = null,
)

@Serializable
data class ProblemDetails(val problemId: String)

@Serializable
data class QuizDetails(
    val quizId: String,
)

@Serializable
object QuizList

@Composable
fun AppRootScreen() {

    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    val onHelpClick: () -> Unit = { showAboutDialog = true }
    val onDismissHelpClick: () -> Unit = { showAboutDialog = false }

    val onBackClick: () -> Unit = { navController.popBackStack() }
    val onHomeClick: () -> Unit = { navController.popBackStack(route = Home, inclusive = false) }
    val onListQuizzesClick: () -> Unit = { navController.navigate(route = QuizList) }
    val onSettingsClick: () -> Unit = { navController.navigate(route = Settings) }
    val onQuizClick: (String) -> Unit = { quizId ->
        navController.navigate(route = QuizDetails(quizId = quizId))
    }
    val onProblemClick: (String) -> Unit = { problemId ->
        navController.navigate(route = ProblemDetails(problemId = problemId))
    }
    val onListProblemsClick: (String, String?) -> Unit = { quizId, selectedId ->
        navController.navigate(route = ProblemList(quizId = quizId, selectedId = selectedId))
    }

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onListQuizzesClick = onListQuizzesClick,
                onSettingsClick = onSettingsClick,
                onHelpClick = onHelpClick
            )
        }
        composable<QuizList> {
            QuizListScreen(
                onHomeClick = onHomeClick,
                onQuizClick = onQuizClick,
                onBackClick = onBackClick,
                onHelpClick = onHelpClick,
            )
        }
        composable<ProblemList> { backStackEntry ->
            val problemList: ProblemList = backStackEntry.toRoute()
            val quizId: String = problemList.quizId
            val selectedId: String? = problemList.selectedId
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
        composable<ProblemDetails> { backStackEntry ->
            val problemDetails: ProblemDetails = backStackEntry.toRoute()
            val problemId: String = problemDetails.problemId
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
        composable<QuizDetails> { backStackEntry ->
            val quizDetails: QuizDetails = backStackEntry.toRoute()
            val quizId: String = quizDetails.quizId
            QuizDetailsScreen(
                quizId = quizId,
                onHomeClick = onHomeClick,
                onProblemClick = onProblemClick,
                onListProblemsClick = onListProblemsClick,
                onBackClick = onBackClick,
                onHelpClick = onHelpClick
            )
        }
        composable<Settings> {
            SettingsScreen(
                onHelpClick = onHelpClick,
                onHomeClick = onHomeClick,
                onBackClick = onBackClick
            )
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismissRequest = onDismissHelpClick)
    }
}