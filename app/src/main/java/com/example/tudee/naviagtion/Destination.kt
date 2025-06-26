package com.example.tudee.naviagtion

import kotlinx.serialization.Serializable

sealed class Destination(val route: String) {
    object SplashScreen : Destination("splashScreen")
    object OnBoardingScreen : Destination("onBoardingScreen")
    object HomeScreen : Destination("homeScreen")
    object TasksScreen : Destination("tasksScreen?status={status}") {

        fun createRoute(status: Int) = "tasksScreen?status=$status"
    }
    object CategoriesScreen : Destination("categoriesScreen")
    object CategoryDetailsScreen : Destination("categoryDetailsScreen") {
        const val categoryIdArg = "categoryId"
        fun withArgs(categoryId: Long): String = "$route/$categoryId"
        val fullRoute = "$route/{$categoryIdArg}"
    }

    @Serializable
    data class CategoryTasksScreen(
        val categoryId: Long
    )
}

