package com.example.tudee.naviagtion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.category.CategoriesScreen
import com.example.tudee.presentation.screen.category.tasks.CategoryTasksScreen

fun NavGraphBuilder.categoryTasksRoute(navController: NavHostController) {
    composable(route = Destination.CategoryTasksScreen.route,
        arguments = listOf(navArgument(Destination.CategoryTasksScreen.CATEGORY_ID_ARG) {
            type = NavType.LongType } )
        ) {
            backStackEntry ->
        val categoryId = backStackEntry.arguments?.getLong(Destination.CategoryTasksScreen.CATEGORY_ID_ARG)
            ?: return@composable

        CategoryTasksScreen(navController = navController, categoryId = categoryId)
    }
}