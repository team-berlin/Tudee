package com.example.tudee.naviagtion

import com.example.tudee.naviagtion.Destination.CategoryDetailsScreen.categoryIdArg

sealed class Destination(val route: String) {
    object SplashScreen : Destination("splashScreen")
    object OnBoardingScreen : Destination("onBoardingScreen")
    object HomeScreen : Destination("homeScreen")
    object TasksScreen : Destination("tasksScreen/{status}") {
        fun createRoute(status: Int) = "tasksScreen/$status"
    }
    object CategoryTasksScreen : Destination("categoryTasksScreen/{categoryId}"){
        const val CATEGORY_ID_ARG = "categoryId"
        fun createRoute(categoryId: Long) = "categoryTasksScreen/$categoryId"
        val fullRoute = "$route/{$CATEGORY_ID_ARG}"
    }

    object CategoriesScreen : Destination("categoriesScreen")
    object CategoryDetailsScreen : Destination("categoryDetailsScreen") {
        const val categoryIdArg = "categoryId"
        fun withArgs(categoryId: Long): String = "$route/$categoryId"
        val fullRoute = "$route/{$categoryIdArg}"
    }
}