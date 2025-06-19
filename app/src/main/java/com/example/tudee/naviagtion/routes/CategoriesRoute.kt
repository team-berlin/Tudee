package com.example.tudee.naviagtion.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.categories.screen.CategoriesScreen

fun NavGraphBuilder.categoriesRoute(navController: NavHostController) {
    composable(route = Destination.CategoriesScreen.route) {
        CategoriesScreen(navController = navController)
    }
}