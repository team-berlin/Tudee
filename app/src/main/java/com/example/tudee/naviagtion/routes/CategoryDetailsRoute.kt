package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tudee.naviagtion.Destination

fun NavGraphBuilder.categoryDetailsRoute (
    navController: NavController
) {
    composable(route = Destination.CategoryDetailsScreen.fullRoute,
        arguments = listOf(navArgument(Destination.CategoryDetailsScreen.categoryIdArg) {
            type = androidx.navigation.NavType.LongType
        }),
    ) {
            backStackEntry ->
        val categoryId = backStackEntry.arguments?.getLong(Destination.CategoryDetailsScreen.categoryIdArg)
        // TODO: Pass categoryId to your screen
        // CategoryDetailsScreen(navController = navController, categoryId = categoryId ?: return@composable)
    }
}