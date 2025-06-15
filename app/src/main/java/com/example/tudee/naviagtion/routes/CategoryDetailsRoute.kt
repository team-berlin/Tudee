package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination

fun NavGraphBuilder.categoryDetailsRoute (
    navController: NavController
) {
    composable(route = Destination.CategoryDetailsScreen.route) {
        // add your screen Composable here
        // ex: CategoryDetailsScreen(navController)
    }
}