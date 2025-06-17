package com.example.tudee.presentation.screen.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle
import com.example.tudee.presentation.components.BottomPageIndicator
import com.example.tudee.presentation.components.OnBoardingPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
) {
    val onboardingOnBoardingPageUiModels = listOf(
        OnBoardingPageUiModel(
            title = R.string.on_boarding_title1,
            description = R.string.on_boarding_description1,
            image = painterResource(R.drawable.tudee_onboarding_1)
        ),
        OnBoardingPageUiModel(
            title = R.string.on_boarding_title2,
            description = R.string.on_boarding_description2,
            image = painterResource(R.drawable.tudee_onboarding_2),
        ),
        OnBoardingPageUiModel(
            title = R.string.on_boarding_title3,
            description = R.string.on_boarding_description3,
            image = painterResource(R.drawable.tudee_onboarding_3),
        )
    )
    val onBoardingPageState = rememberPagerState(initialPage = 0) {
        onboardingOnBoardingPageUiModels.size
    }
    val coroutineScope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current

    val orientation = rememberSaveable { configuration.orientation }

    OnBoardingContent(
        modifier = modifier,
        pageState = onBoardingPageState,
        scope = coroutineScope,
        OnBoardingPageUiModels = onboardingOnBoardingPageUiModels,
        orientation = orientation
    )
}

@Composable
private fun OnBoardingContent(
    modifier: Modifier = Modifier,
    OnBoardingPageUiModels: List<OnBoardingPageUiModel>,
    pageState: PagerState,
    scope: CoroutineScope,
    orientation: Int,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TudeeTheme.color.statusColors.overlay),
        contentAlignment = Alignment.Center
    ) {
        if (pageState.currentPage != 2)
            TextButton(
                modifier = Modifier.align(alignment = Alignment.TopStart),
                onClick = { /* TODO NAVIGATE TO HOME SCREEN */ },
            ) {
                Text(
                    stringResource(R.string.skip_button),
                    modifier = Modifier.padding(16.dp),
                    style = TudeeTextStyle.label.large,
                    color = TudeeTheme.color.primary
                )
            }
        Image(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            painter = painterResource(R.drawable.background_ellipse),
            contentDescription = "Back ground ellipse"
        )
        HorizontalPager(
            modifier = modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 75.dp),
            state = pageState
        ) { index ->
            OnBoardingPage(
                orientation = (orientation == Configuration.ORIENTATION_PORTRAIT),
                modifier = Modifier.align(alignment = Alignment.Center),
                currentPage = pageState.currentPage,
                onBoardingPageUiModel = OnBoardingPageUiModels[index],
                onClick = { scope.launch { pageState.animateScrollToPage(pageState.currentPage + 1) } }
            )
        }
        BottomPageIndicator(
            OnBoardingPageUiModels = OnBoardingPageUiModels,
            pageNumber = pageState.currentPage,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
@PreviewLightDark()
@Preview(locale = "ar")
@Preview(heightDp = 360, widthDp = 800)
private fun OnBoardingScreenPreview() {
    TudeeTheme {
        OnBoardingScreen()
    }
}