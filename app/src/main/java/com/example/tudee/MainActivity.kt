package com.example.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.naviagtion.Destination.CategoriesScreen
import com.example.tudee.naviagtion.Destination.TasksScreen

class MainActivity : ComponentActivity() {
    private val bottomBarRoutes =
        listOf(Destination.HomeScreen.route, TasksScreen.route, CategoriesScreen.route)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            TudeeTheme {

                    com.example.tudee.presentation.screen.task_screen.TasksScreen(navController)

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TudeeTheme {
        MainActivity()
    }
}