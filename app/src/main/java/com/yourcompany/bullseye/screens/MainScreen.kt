package com.yourcompany.bullseye.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yourcompany.bullseye.viewmodels.GameScreenViewModel
import com.yourcompany.bullseye.viewmodels.GameScreenViewModelFactory

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "gamescreen"
    ) {
        composable(
            route = "gamescreen"
        ) {
            val viewModel: GameScreenViewModel = viewModel(factory = GameScreenViewModelFactory())

            GameScreen(
                viewModel = viewModel,
                onNavigateToAbout = {
                    navController.navigate("about")
                }
            )
        }

        composable(
            route = "about"
        ) {
            AboutScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}