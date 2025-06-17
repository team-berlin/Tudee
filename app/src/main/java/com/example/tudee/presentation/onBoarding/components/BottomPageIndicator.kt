package com.example.tudee.presentation.onBoarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.onBoarding.Page

@Composable
fun BottomPageIndicator(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    pages: List<Page>
) {
    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(pages.size) { page ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = if (page == pageNumber) TudeeTheme.color.primary
                        else TudeeTheme.color.primaryVariant
                    )
            )
        }
    }
}

@Composable
@Preview
private fun BottomPageIndicatorPreview() {
    BottomPageIndicator(
        pageNumber = 1,
        pages = listOf(
            Page(0, 0, painterResource(R.drawable.tudee_onboarding_1)),
            Page(0, 0, painterResource(R.drawable.tudee_onboarding_1)),
            Page(0, 0, painterResource(R.drawable.tudee_onboarding_1))
        )
    )
}