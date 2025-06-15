package com.example.tudee.naviagtion.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tudee.naviagtion.Destination

fun NavGraphBuilder.tasksRoute (
    navController: NavController
) {
    composable(route = Destination.TasksScreen.route) {
        // add your screen Composable here
        // ex: TasksScreen(navController)
    }
}