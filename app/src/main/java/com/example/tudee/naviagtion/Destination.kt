package com.example.tudee.naviagtion

sealed class Destination(val route: String) {
    object SplashScreen : Destination("splashScreen")
    object OnBoardingScreen : Destination("onBoardingScreen")
    object HomeScreen : Destination("homeScreen")
    object TasksScreen : Destination("tasksScreen")
    object CategoriesScreen : Destination("categoriesScreen")
    object CategoryDetailsScreen : Destination("categoryDetailsScreen")
}