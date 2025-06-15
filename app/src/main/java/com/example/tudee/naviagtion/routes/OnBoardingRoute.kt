package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination

fun NavGraphBuilder.onBoardingRoute (
    navController: NavController
) {
    composable(route = Destination.OnBoardingScreen.route) {
        // add your screen Composable here
        // ex: OnBoardingScreen(navController)
    }
}