package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Preview(showBackground = true)
@Composable
fun CategoryComponentPreview() {
    TudeeTheme {
        CategoryComponent(
            categoryPainter = painterResource(R.drawable.ic_eduction),
            categoryName = "Education",
            categoryImageContentDescription = "Education Category",
            categoryTextStyle = TudeeTheme.textStyle.label.small,
            categoryTextColor = TudeeTheme.color.textColors.body
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
) {
    Column(
        modifier = modifier.width(104.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .size(78.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(TudeeTheme.color.surfaceHigh)
            ,contentAlignment = Alignment.Center
        ) {
            Image(
                categoryPainter,
                contentDescription = categoryImageContentDescription,
            )
        }
        Text(
            text = categoryName,
            style = categoryTextStyle,
            color = categoryTextColor,
        )
    }
}