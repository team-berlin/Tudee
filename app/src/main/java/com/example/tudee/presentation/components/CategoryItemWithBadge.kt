package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun CategoryItemWithBadge(
    modifier: Modifier = Modifier,
    categoryPainter: Painter,
    categoryBackgroundColor: Color = TudeeTheme.color.surfaceHigh,
    badgeBackgroundColor: Color = TudeeTheme.color.surfaceLow,
    categoryImageContentDescription: String?,
    categoryName: String,
    categoryTextStyle: TextStyle = TudeeTheme.textStyle.label.small,
    categoryTextColor: Color = TudeeTheme.color.textColors.body,
    showCheckedIcon: Boolean = false,
    badgeCount: Int? = null
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .height(102.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(78.dp), contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(shape = CircleShape, color = categoryBackgroundColor),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    categoryPainter,
                    contentDescription = categoryImageContentDescription,
                )
            }

            when {
                showCheckedIcon -> Box(
                    Modifier
                        .size(20.dp)
                        .background(shape = CircleShape, color = badgeBackgroundColor)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.Center),
                        tint = TudeeTheme.color.textColors.onPrimary,
                        painter = painterResource(R.drawable.ic_double_check),
                        contentDescription = null,
                    )
                }

                badgeCount != null -> {
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height(20.dp)
                            .align(Alignment.TopEnd)
                            .clip(RoundedCornerShape(50))
                            .background(badgeBackgroundColor)
                            .padding(horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = badgeCount.toString(),
                            style = categoryTextStyle,
                            color = categoryTextColor,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        Text(
            text = categoryName,
            style = categoryTextStyle,
            color = categoryTextColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryComponentWithCheckPreview() {
    TudeeTheme {
        CategoryItemWithBadge(
            categoryPainter = painterResource(R.drawable.ic_eduction),
            categoryName = "Education",
            categoryImageContentDescription = "Education Category",
            categoryTextStyle = TudeeTheme.textStyle.label.small,
            categoryTextColor = TudeeTheme.color.textColors.body,
            badgeBackgroundColor = TudeeTheme.color.statusColors.greenAccent,
            showCheckedIcon = true,
            //badgeCount = 1
        )
    }
}
@Preview(showBackground = true)
@Composable
fun CategoryComponentWithBadgePreview() {
    TudeeTheme {
        CategoryItemWithBadge(
            categoryPainter = painterResource(R.drawable.ic_eduction),
            categoryName = "Education",
            categoryImageContentDescription = "Education Category",
            categoryTextStyle = TudeeTheme.textStyle.label.small,
            categoryTextColor = TudeeTheme.color.textColors.body,
            //showCheckedIcon = true
            badgeCount = 1
        )
    }
}