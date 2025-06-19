package com.example.tudee.naviagtion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.tudee.naviagtion.routes.categoriesRoute
import com.example.tudee.naviagtion.routes.categoryDetailsRoute
import com.example.tudee.naviagtion.routes.homeRoute
//import com.example.tudee.naviagtion.routes.onBoardingRoute
import com.example.tudee.naviagtion.routes.splashRoute
import com.example.tudee.naviagtion.routes.tasksRoute

/**
 * Sets up the navigation graph for the Tudee app using Jetpack Compose Navigation 2.
 *
 * This function initializes a [NavHost] with the given [navController] and defines the app's
 * navigation destinations and their respective navigation routes. The start destination is set
 * to the OnBoarding screen. Each destination is registered using its corresponding route extension
 * function to encapsulate the screen's navigation logic.
 *
 * @param navController The [NavHostController] used to manage app navigation and back stack.
 *
 */

@Composable
fun TudeeNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.OnBoardingScreen.route) {
        splashRoute(navController)
        //onBoardingRoute(navController)
        homeRoute(navController)
        tasksRoute(navController)
        categoriesRoute(navController)
        categoryDetailsRoute(navController)
    }
}