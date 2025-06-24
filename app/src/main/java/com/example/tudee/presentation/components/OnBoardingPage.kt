package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.tooling.preview.Preview
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
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnBoardingPageContent(onBoardingPageUiModel, onClick)
        }
    } else {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnBoardingPageContent(onBoardingPageUiModel, onClick)
        }
    }
}

@Composable
private fun OnBoardingPageContent(
    onBoardingPageUiModel: OnBoardingPageUiModel,
    onClick: () -> Unit
) {
    Box(modifier = Modifier, contentAlignment = Alignment.Center) {

        Image(
            contentDescription = "Image for On boarding page",
            painter = onBoardingPageUiModel.image,
        )
    }
    Box(
        modifier = Modifier.padding(vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier
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
                    bottom = 50.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
                maxLines = 3,
                minLines = 3,
                textAlign = TextAlign.Center,
                color = TudeeTheme.color.textColors.body
            )
        }
        FabButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .offset(y = 27.dp)
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
@Preview(showSystemUi = true)
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