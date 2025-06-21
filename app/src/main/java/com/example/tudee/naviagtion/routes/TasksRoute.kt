package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.task_screen.ui.TasksScreen

fun NavGraphBuilder.tasksRoute(navController: NavController) {

    composable(
        route = Destination.TasksScreen.route,
        arguments = listOf(navArgument("status") { type = NavType.IntType })
    ) { backStackEntry ->
        val status = backStackEntry.arguments?.getInt("status") ?: 0
        TasksScreen(navController = navController, status = status)
    }


    composable(
        route = Destination.TasksScreen.route
    ) {
        TasksScreen(navController = navController, status = 0)
    }
}