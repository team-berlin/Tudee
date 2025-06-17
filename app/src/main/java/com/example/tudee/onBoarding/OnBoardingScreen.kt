package com.example.tudee.onBoarding

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle
import com.example.tudee.onBoarding.comonents.BottomPageIndicator
import com.example.tudee.onBoarding.comonents.OnBoardingPageContent

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
) {
    val onBoardingPageState = rememberPagerState(initialPage = 0) {
        onboardingPages.size
    }
    OnBoardingContent(modifier = modifier, pageState = onBoardingPageState)
}

@Composable
private fun OnBoardingContent(
    modifier: Modifier = Modifier,
    pageState: PagerState
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TudeeTheme.color.statusColors.overlay), contentAlignment = Alignment.Center
    ) {
        if (pageState.currentPage != 2)
            TextButton(
                modifier = Modifier.align(alignment = Alignment.TopStart),
                onClick = { /* TODO NAVIGATE TO HOME SCREEN */ },
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Skip",
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
            OnBoardingPageContent(
                currentPage = pageState.currentPage,
                page = onboardingPages[index],
                onClick = { /* TODO MOVE TO NEXT PAGE */ }
            )
        }
        BottomPageIndicator(
            pageNumber = pageState.currentPage, modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
@PreviewLightDark
@Preview(locale = "ar" , uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
private fun OnBoardingScreenPreview() {
    TudeeTheme {
        OnBoardingContent(pageState = rememberPagerState(initialPage = 0) {
            onboardingPages.size
        }
        )
        OnBoardingContent(pageState = rememberPagerState(initialPage = 0) {
            onboardingPages.size
        }
        )
    }
}