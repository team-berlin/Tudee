package com.example.tudee

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.TudeeNavGraph
import com.example.tudee.presentation.themeViewModel.ThemeViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = koinViewModel()
            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            TudeeTheme(isDarkTheme = isDarkMode) {
                TudeeNavGraph(navController)

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