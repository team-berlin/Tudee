package com.example.tudee.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.categories.model.TaskCategoryUiModel

@Composable
fun CategoryItem(
    category: TaskCategoryUiModel,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            // Icon
            Icon(Icons.Filled.Favorite, contentDescription = null)


            // Count Badge
            if (category.count > 0) {
                Text(
                    text = if (category.count > 99) "+100" else category.count.toString(),
                    style = TudeeTheme.textStyle.label.small,
                    color = TudeeTheme.color.textColors.onPrimaryCard,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = TudeeTheme.color.secondary,
                            shape = CircleShape
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            style = TudeeTheme.textStyle.label.medium,
            color = if (category.isPredefined) TudeeTheme.color.textColors.title
            else TudeeTheme.color.textColors.onPrimaryCard
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TudeeTheme {
        CategoryItem(TaskCategoryUiModel(1, "Category", "10"), onClick = {})
    }
}