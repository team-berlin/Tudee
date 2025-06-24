package com.example.tudee.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.screen.onboarding.OnBoardingPageUiModel

@Composable
fun BottomPageIndicator(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    onBoardingPageUiModels: List<OnBoardingPageUiModel>,
    onIndicatorClicked: (Int) -> Unit

) {

    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(onBoardingPageUiModels.size) { page ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = if (page == pageNumber) TudeeTheme.color.primary
                        else TudeeTheme.color.primaryVariant
                    )
                    .clickable(
                        indication = rememberRipple(color = TudeeTheme.color.primary),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onIndicatorClicked(page)
                    }

            )
        }
    }
}

@Composable
@Preview
private fun BottomPageIndicatorPreview() {
    BottomPageIndicator(
        pageNumber = 1,
        onBoardingPageUiModels = listOf(
            OnBoardingPageUiModel("0", "0", painterResource(R.drawable.tudee_onboarding_1)),
            OnBoardingPageUiModel("0", "0", painterResource(R.drawable.tudee_onboarding_1)),
            OnBoardingPageUiModel("0", "0", painterResource(R.drawable.tudee_onboarding_1))
        ),
        onIndicatorClicked = {}
    )
}