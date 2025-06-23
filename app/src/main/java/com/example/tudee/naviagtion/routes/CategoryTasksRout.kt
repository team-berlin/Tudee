package com.example.tudee.naviagtion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.category.tasks.CategoryTasksScreen

fun NavGraphBuilder.categoryTasksRout(navController: NavHostController) {
    composable(
        route = Destination.CategoryTasksScreen.route,
        arguments = listOf(
            navArgument("categoryId") { type = NavType.LongType }
        )
    ) { backStackEntry ->
        CategoryTasksScreen(navController = navController)
    }
}