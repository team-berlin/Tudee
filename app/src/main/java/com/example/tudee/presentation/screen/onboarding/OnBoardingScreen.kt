package com.example.tudee.presentation.screen.onboarding

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.components.BottomPageIndicator
import com.example.tudee.presentation.components.OnBoardingPage
import com.example.tudee.presentation.components.buttons.TextButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.getKoin

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: OnBoardingViewModel = getKoin().get()
) {
    val onboardingOnBoardingPageUiModels = listOf(
        OnBoardingPageUiModel(
            title = stringResource(R.string.on_boarding_title1),
            description = stringResource(R.string.on_boarding_description1),
            image = painterResource(R.drawable.tudee_onboarding_1)
        ),
        OnBoardingPageUiModel(
            title = stringResource(R.string.on_boarding_title2),
            description = stringResource(R.string.on_boarding_description2),
            image = painterResource(R.drawable.tudee_onboarding_2),
        ),
        OnBoardingPageUiModel(
            title = stringResource(R.string.on_boarding_title3),
            description = stringResource(R.string.on_boarding_description3),
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
        onBoardingPageUiModels = onboardingOnBoardingPageUiModels,
        orientation = orientation,
        navigateToHome = {
            viewModel.loadInitialData()
            viewModel.saveFirstEntry()
            navController.navigate(Destination.HomeScreen.route) {
                popUpTo(Destination.OnBoardingScreen.route) { inclusive = true }
            }
        }
    )
}

@Composable
private fun OnBoardingContent(
    modifier: Modifier = Modifier,
    onBoardingPageUiModels: List<OnBoardingPageUiModel>,
    pageState: PagerState,
    scope: CoroutineScope,
    orientation: Int,
    navigateToHome: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(TudeeTheme.color.statusColors.overlay),
        contentAlignment = Alignment.Center
    ) {
        if (pageState.currentPage != Pages.ThirdPage.page) {
            TextButton(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .zIndex(10f),
                onClick = navigateToHome,
            ) {
                Text(
                    stringResource(R.string.skip_button),
                    modifier = Modifier.statusBarsPadding(),
                    style = TudeeTextStyle.label.large,
                    color = TudeeTheme.color.primary
                )
            }
        }

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_ellipse),
            contentDescription = stringResource(R.string.back_ground_ellipse),
            alignment = Alignment.TopEnd,
        )

        HorizontalPager(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 88.dp),
            state = pageState,
            verticalAlignment = Alignment.Bottom
        ) { index ->
            OnBoardingPage(
                orientation = (orientation == Configuration.ORIENTATION_PORTRAIT),
                modifier = Modifier.align(alignment = Alignment.Center),
                onBoardingPageUiModel = onBoardingPageUiModels[index],
                onClick = {
                    scope.launch {
                        if (pageState.currentPage != onBoardingPageUiModels.lastIndex) {
                            pageState.animateScrollToPage(
                                page = pageState.currentPage + 1,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        } else {
                            navigateToHome()
                        }
                    }
                }
            )
        }

        BottomPageIndicator(
            onBoardingPageUiModels = onBoardingPageUiModels,
            pageNumber = pageState.currentPage,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            onIndicatorClicked = {
                scope.launch {
                    pageState.animateScrollToPage(it)
                }
            }
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