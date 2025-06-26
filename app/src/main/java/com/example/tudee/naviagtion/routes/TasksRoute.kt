package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.task_screen.ui.TasksScreen
import com.example.tudee.presentation.screen.task_screen.viewmodel.TasksScreenViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel


fun NavGraphBuilder.tasksRoute(navController: NavController) {
    composable(
        route = Destination.TasksScreen.route,
        arguments = listOf(
            navArgument("status") {
                type = NavType.IntType
                defaultValue = 1
            }
        ),
    ) { backStackEntry ->
        val viewModel: TasksScreenViewModel = koinNavViewModel()
        TasksScreen(
            navController = navController,
            tasksScreenViewModel = viewModel
        )
    }
}