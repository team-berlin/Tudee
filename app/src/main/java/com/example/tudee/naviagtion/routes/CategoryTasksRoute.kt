package com.example.tudee.naviagtion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination.CategoryTasksScreen
import com.example.tudee.presentation.screen.category.tasks.CategoryTasksScreen

fun NavGraphBuilder.categoryTasksRoute(navController: NavHostController) {
    composable<CategoryTasksScreen>(
    ) { backStackEntry ->
        CategoryTasksScreen(navController = navController)
    }
}