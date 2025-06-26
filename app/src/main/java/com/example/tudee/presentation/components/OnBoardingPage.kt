package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    onClick: () -> Unit = {},
    orientation: Boolean = true,
    onBoardingPageUiModel: OnBoardingPageUiModel,
) {
    if (orientation) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnBoardingPageContent(Modifier, onBoardingPageUiModel, onClick)
        }
    } else {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnBoardingPageContent(Modifier.padding(horizontal =  32.dp), onBoardingPageUiModel, onClick)
        }
    }
}

@Composable
private fun OnBoardingPageContent(
    modifier: Modifier = Modifier,
    onBoardingPageUiModel: OnBoardingPageUiModel,
    onClick: () -> Unit
) {

    Image(
        contentDescription = stringResource(R.string.image_for_on_boarding_page),
        painter = onBoardingPageUiModel.image,
    )
    Box {
        Box(
            modifier = modifier.padding(vertical = 32.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        width = 1.dp,
                        color = TudeeTheme.color.textColors.onPrimaryStroke,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .background(TudeeTheme.color.textColors.onPrimaryCard),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = onBoardingPageUiModel.title,
                    style = TudeeTheme.textStyle.title.medium,
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    minLines = 2,
                    color = TudeeTheme.color.textColors.title,
                )
                Text(
                    text = onBoardingPageUiModel.description,
                    style = TudeeTheme.textStyle.body.medium,
                    modifier = Modifier.padding(
                        bottom = 48.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                    maxLines = 3,
                    minLines = 3,
                    textAlign = TextAlign.Center,
                    color = TudeeTheme.color.textColors.body
                )
            }
        }
        FabButton(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.BottomCenter)
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
@PreviewLightDark
private fun OnBoardingPagePreview() {
    TudeeTheme {
        OnBoardingPage(
            onBoardingPageUiModel = OnBoardingPageUiModel(
                title = stringResource(R.string.on_boarding_title1),
                description = stringResource(R.string.on_boarding_description1),
                image = painterResource(R.drawable.tudee_onboarding_1),
            )
        )
    }
}