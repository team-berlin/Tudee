package com.example.tudee.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun TudeeChip(
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    backgroundColor: Color = TudeeTheme.color.primary,
    textColor: Color = TudeeTheme.color.textColors.onPrimary,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = textColor,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = label,
                color = textColor,
                style = TudeeTheme.textStyle.label.small
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PriorityChipPreview() {
    val colors = TudeeTheme.color.statusColors
    val textColor = TudeeTheme.color.textColors.onPrimary
    TudeeTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            TudeeChip(
                label = "High",
                icon = Icons.Default.KeyboardArrowUp,
                backgroundColor = colors.pinkAccent,
                textColor = textColor
            )
            TudeeChip(
                label = "Medium",
                icon = Icons.Default.Warning,
                backgroundColor = colors.yellowAccent,
                textColor = textColor
            )
            TudeeChip(
                label = "Low",
                icon = Icons.Default.KeyboardArrowDown,
                backgroundColor = colors.greenAccent,
                textColor = textColor
            )
        }
    }
}