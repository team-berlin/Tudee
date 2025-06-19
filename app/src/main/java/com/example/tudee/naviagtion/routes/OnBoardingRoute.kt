package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.screen.onboarding.OnBoardingScreen

fun NavGraphBuilder.onBoardingRoute (
    navController: NavController
) {
    composable(route = Destination.OnBoardingScreen.route) {
//            OnBoardingScreen(navController = navController)
    }
}