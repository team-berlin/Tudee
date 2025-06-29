package com.example.tudee.presentation.screen.splash

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.data.preferences.PreferencesManager
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.themeViewModel.ThemeViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel = koinViewModel(),
    navController: NavController = rememberNavController(),
    backgroundColor: Color = TudeeTheme.color.surface,
    overlayColor: Color = TudeeTheme.color.statusColors.overlay,
    backgroundPainter: Painter = painterResource(R.drawable.background_ellipse),
    iconPainter: Painter = painterResource(R.drawable.tudee_logo),
    prefs: PreferencesManager = getKoin().get()
) {
    val isDark by themeViewModel.isDarkMode.collectAsState()
    LaunchedEffect(Unit) {
        delay(3000)
        if (prefs.isOnboardingCompleted().not()) {
            navController.navigate(Destination.OnBoardingScreen.route) {
                popUpTo(Destination.SplashScreen.route) { inclusive = true }
            }

        } else {
            navController.navigate(Destination.HomeScreen.route) {
                popUpTo(Destination.SplashScreen.route) { inclusive = true }
            }
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(overlayColor),
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = if (isDark) painterResource(R.drawable.background_ellipse_dark) else backgroundPainter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Image(painter = iconPainter, contentDescription = null)
    }
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SplashScreenPreview() {
    TudeeTheme {
        SplashScreen()
    }
}