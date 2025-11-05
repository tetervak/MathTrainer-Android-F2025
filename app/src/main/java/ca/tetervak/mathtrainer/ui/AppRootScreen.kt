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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import ca.tetervak.mathtrainer.ui.details.ProblemDetailsScreen
import ca.tetervak.mathtrainer.ui.home.HomeScreen
import ca.tetervak.mathtrainer.ui.list.ProblemListScreen
import ca.tetervak.mathtrainer.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
object SettingsDestination

@Serializable
data class ProblemDetailsDestination(val problemId: Int)

@Composable
fun AppRootScreen() {

    var showAboutDialog: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeDestination
    ) {
        composable<HomeDestination>{
            HomeScreen(
                onFirstClick = { navController.navigate(ProblemDetailsDestination(problemId = 1)) },
                onListClick = { navController.navigate("list-problems") },
                onSettingsClick = { navController.navigate(SettingsDestination) },
                onHelpClick = { showAboutDialog = true }
            )
        }
        composable(
            route = "list-problems?selected={selected}",
            arguments = listOf(navArgument("selected") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) {
            backStackEntry ->
            val selected = backStackEntry.arguments?.getInt("selected") ?: 0
            ProblemListScreen(
                selected = selected,
                onProblemClick = { problemId ->
                    navController.navigate(ProblemDetailsDestination(problemId = problemId))
                },
                onHomeClick = { navController.navigate(HomeDestination) },
                onHelpClick = { showAboutDialog = true }
            )
        }
        composable<ProblemDetailsDestination>{
            backStackEntry ->
            val problemDetailsDestination: ProblemDetailsDestination = backStackEntry.toRoute()
            val problemId = problemDetailsDestination.problemId
            ProblemDetailsScreen(
                onHelpClick = { showAboutDialog = true },
                onHomeClick = { navController.navigate(HomeDestination) },
                onListClick = { navController.navigate("list-problems?selected=$problemId") },
                onProblemNavClick = { problemId ->
                    navController.navigate(ProblemDetailsDestination(problemId = problemId))
                }
            )
        }
        composable<SettingsDestination> {
            SettingsScreen(
                onHelpClick = { showAboutDialog = true },
                onHomeClick = { navController.navigate(HomeDestination) }
            )
        }
    }

    if (showAboutDialog) {
        AboutDialog(onDismissRequest = { showAboutDialog = false })
    }
}