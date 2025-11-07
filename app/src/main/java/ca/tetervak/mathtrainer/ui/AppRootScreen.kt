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
import ca.tetervak.mathtrainer.ui.details.ProblemDetailsScreen
import ca.tetervak.mathtrainer.ui.home.HomeScreen
import ca.tetervak.mathtrainer.ui.list.ProblemListScreen
import ca.tetervak.mathtrainer.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Settings

@Serializable
data class ProblemDetails(val problemId: Int)

@Serializable
data class ProblemList(val selected: Int? = 0)


@Composable
fun AppRootScreen() {

    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home>{
            HomeScreen(
                onFirstClick = { navController.navigate(route = ProblemDetails(problemId = 1)) },
                onListClick = { navController.navigate(route = ProblemList()) },
                onSettingsClick = { navController.navigate(route = Settings) },
                onHelpClick = { showAboutDialog = true }
            )
        }
        composable<ProblemList>{
            backStackEntry ->
            val problemList: ProblemList = backStackEntry.toRoute()
            val selected: Int = problemList.selected ?: 0
            ProblemListScreen(
                selected = selected,
                onProblemClick = { problemId ->
                    navController.navigate(route = ProblemDetails(problemId = problemId))
                },
                onHomeClick = { navController.popBackStack(route = Home, inclusive = false) },
                onHelpClick = { showAboutDialog = true }
            )
        }
        composable<ProblemDetails>{
            backStackEntry ->
            val problemDetails: ProblemDetails = backStackEntry.toRoute()
            val problemId: Int = problemDetails.problemId
            ProblemDetailsScreen(
                onHelpClick = { showAboutDialog = true },
                onHomeClick = { navController.popBackStack(route = Home, inclusive = false) },
                onListClick = { navController.navigate(route = ProblemList(selected = problemId)) },
                onProblemNavClick = { problemId ->
                    navController.navigate(route = ProblemDetails(problemId = problemId))
                }
            )
        }
        composable<Settings> {
            SettingsScreen(
                onHelpClick = { showAboutDialog = true },
                onHomeClick = { navController.popBackStack(route = Home, inclusive = false) }
            )
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismissRequest = { showAboutDialog = false })
    }
}