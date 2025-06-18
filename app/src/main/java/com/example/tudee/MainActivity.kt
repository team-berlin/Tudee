package com.example.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.naviagtion.Destination.CategoriesScreen
import com.example.tudee.naviagtion.Destination.TasksScreen
import com.example.tudee.naviagtion.TudeeNavGraph

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
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute in bottomBarRoutes) {
                            // add the bottom bar Composable here
                        }
                    }) { innerPadding ->
                    TudeeNavGraph(navController)
                    EditScreen(taskId = 1L)
                  //  AddScreen()

                }
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