package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Preview(showBackground = true)
@Composable
fun CategoryComponentPreview() {
    TudeeTheme {
        CategoryComponent(
            categoryPainter = painterResource(R.drawable.ic_education),
            categoryName = "Education",
            categoryImageContentDescription = "Education Category",
            categoryTextStyle = TudeeTheme.textStyle.label.small,
            categoryTextColor = TudeeTheme.color.textColors.body,
            showCheckedIcon = true
        )
    }
}

@Composable
fun CategoryComponent(
    modifier: Modifier = Modifier,
    categoryPainter: Painter,
    categoryImageContentDescription: String?,
    categoryName: String,
    categoryTextStyle: TextStyle = TudeeTheme.textStyle.label.small,
    categoryTextColor: Color = TudeeTheme.color.textColors.body,
    showCheckedIcon: Boolean = false,
) {
    Column(
        modifier = modifier.width(104.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(78.dp), contentAlignment = Alignment.Center
        ) {
            if (showCheckedIcon)
                Box(
                    Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(TudeeTheme.color.statusColors.greenAccent)
                        .align(Alignment.TopEnd)
                        .zIndex(1f)
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
            Box(
                Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(100.dp))
                    .background(TudeeTheme.color.surfaceHigh)
                    .zIndex(0f),
                contentAlignment = Alignment.Center,

                ) {
                Image(
                    categoryPainter,
                    contentDescription = categoryImageContentDescription,
                )
            }
        }
        Text(
            text = categoryName,
            style = categoryTextStyle,
            color = categoryTextColor,
        )
    }
}
