package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination
import com.example.tudee.ui.home.screen.HomeScreen

fun NavGraphBuilder.homeRoute (
    navController: NavController
) {
    composable(route = Destination.HomeScreen.route) {
        HomeScreen(
            navController = navController,
            navigateDoneTasks = {
                navController.navigate(Destination.TasksScreen.createRoute(2))
            },
            navigateInProgressTasks = {
                navController.navigate(Destination.TasksScreen.createRoute(0))
            },
            navigateTodoTasks = {
                navController.navigate(Destination.TasksScreen.createRoute(1))
            }
        )
    }
}