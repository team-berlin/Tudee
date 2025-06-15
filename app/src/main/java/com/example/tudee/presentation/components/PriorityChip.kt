package com.example.tudee.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun TudeeChip(
    label: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    backgroundColor: Color = TudeeTheme.color.primary,
    textColor: Color = TudeeTheme.color.textColors.onPrimary,
    textSize: TextUnit = TudeeTheme.textStyle.label.small.fontSize,
    paddingValues: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
    iconSize: Dp = 12.dp
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                tint = textColor,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = label,
                color = textColor,
                style = TextStyle(fontSize = textSize)
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
                icon = rememberVectorPainter(Icons.Default.KeyboardArrowUp),
                backgroundColor = colors.pinkAccent,
                textColor = textColor
            )
            TudeeChip(
                label = "Medium",
                icon = rememberVectorPainter(Icons.Default.Warning),
                backgroundColor = colors.yellowAccent,
                textColor = textColor
            )
            TudeeChip(
                label = "Low",
                icon = rememberVectorPainter(Icons.Default.KeyboardArrowDown),
                backgroundColor = colors.greenAccent,
                textColor = textColor
            )
        }
    }
}