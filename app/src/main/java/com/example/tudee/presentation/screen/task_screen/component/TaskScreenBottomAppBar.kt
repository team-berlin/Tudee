package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.tudee.R
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.components.BottomNavItem
import com.example.tudee.presentation.components.NavBar

@Composable
fun TaskScreenBottomAppBar(navController: NavController) {
    NavBar(
        navDestinations = listOf(
            BottomNavItem(
                icon = painterResource(id = R.drawable.home),
                selectedIcon = painterResource(id = R.drawable.home_select),
                route = Destination.HomeScreen.route
            ), BottomNavItem(
                icon = painterResource(id = R.drawable.task),
                selectedIcon = painterResource(id = R.drawable.task_select),
                route = Destination.TasksScreen.route
            ), BottomNavItem(
                icon = painterResource(id = R.drawable.category),
                selectedIcon = painterResource(id = R.drawable.category_select),
                route = Destination.CategoriesScreen.route
            )
        ),
        currentRoute = navController.currentDestination?.route ?: Destination.HomeScreen.route,
        onNavDestinationClicked = { route ->
            when (route) {
                Destination.HomeScreen.route -> {
                    navController.navigate(Destination.HomeScreen.route)
                }

                Destination.TasksScreen.route -> {
                    navController.navigate(Destination.TasksScreen.route)
                }

                Destination.CategoriesScreen.route -> {
                    navController.navigate(Destination.CategoriesScreen.route)
                }
            }
        })
}