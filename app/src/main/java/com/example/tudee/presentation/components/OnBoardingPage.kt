package com.example.tudee.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.buttons.FabButton
import com.example.tudee.presentation.screen.onboarding.OnBoardingPageUiModel

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    orientation: Boolean = true,
    onBoardingPageUiModel: OnBoardingPageUiModel,
) {
    if (orientation) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnBoardingPageContent(onBoardingPageUiModel, onClick,true)
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OnBoardingPageContent(onBoardingPageUiModel, onClick, false)
            }
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
private fun OnBoardingPageContent(
    onBoardingPageUiModel: OnBoardingPageUiModel,
    onClick: () -> Unit,
    isPortrait: Boolean = true
) {
    val config = LocalConfiguration.current
    val imageSize = if (isPortrait) {
        config.screenWidthDp.dp * 0.8f
    } else {
        config.screenHeightDp.dp * 0.4f
    }
    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(imageSize),
            contentDescription = "Image for On boarding page",
            painter = painterResource(R.drawable.blur_background_shape),
            contentScale = ContentScale.Fit,
        )
        Image(
            modifier = Modifier.size(imageSize),
            contentDescription = "Image for On boarding page",
            painter = onBoardingPageUiModel.image,
            contentScale = ContentScale.Fit,
        )
    }
    Box(
        modifier = Modifier.padding(bottom = 23.dp),
        contentAlignment = Alignment.BottomCenter) {
        OnBoardingTextCard(
            title = onBoardingPageUiModel.title,
            description = onBoardingPageUiModel.description
        )
            FabButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .offset(y = 23.dp)
                    .clip(shape = RoundedCornerShape(55.dp)),
                onClick = onClick,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_double_arrow),
                    contentDescription = stringResource(R.string.double_arrow_icon),
                )
            }
        }
    }

@Composable
private fun OnBoardingTextCard(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
            .border(
                width = 2.dp,
                color = TudeeTheme.color.textColors.onPrimaryStroke,
                shape = RoundedCornerShape(32.dp)
            )
            .background(TudeeTheme.color.textColors.onPrimaryCard),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = TudeeTheme.textStyle.title.medium,
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            color = TudeeTheme.color.textColors.title,
        )

        Text(
            text = description,
            style = TudeeTheme.textStyle.body.medium,
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 50.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            textAlign = TextAlign.Center,
            color = TudeeTheme.color.textColors.body
        )
    }
}


@Composable
@PreviewLightDark
private fun OnBoardingPagePreview() {
    TudeeTheme {
        OnBoardingPage(
            onBoardingPageUiModel = OnBoardingPageUiModel(
                title= stringResource(R.string.on_boarding_title1),
                description = stringResource(R.string.on_boarding_description1),
                image = painterResource(R.drawable.tudee_onboarding_1),
            )
        )
    }
}