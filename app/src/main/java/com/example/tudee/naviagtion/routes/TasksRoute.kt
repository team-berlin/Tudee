package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.categorytasks.CategoryTasksScreen

fun NavGraphBuilder.tasksRoute(
    navController: NavController
) {
    composable(route = Destination.TasksScreen.route) {

        CategoryTasksScreen(navController = navController)
    }
}