package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.SplashScreen

fun NavGraphBuilder.splashRoute (
    navController: NavController
) {
    composable(route = Destination.SplashScreen.route) {
        SplashScreen(navController = navController)
    }
}