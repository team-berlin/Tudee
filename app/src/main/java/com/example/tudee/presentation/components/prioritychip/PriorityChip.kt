package com.example.tudee.presentation.components.prioritychip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun PriorityChip(
    priority: Priority,
    modifier: Modifier = Modifier
) {
    val colors = TudeeTheme.color.statusColors
    val textColor = TudeeTheme.color.textColors.onPrimary

    val (icon, backgroundColor) = when (priority) {
        Priority.HIGH -> Icons.Default.Done to colors.pinkAccent
        Priority.MEDIUM -> Icons.Default.Warning to colors.yellowAccent
        Priority.LOW -> Icons.Default.Add to colors.greenAccent
    }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = priority.name,
                tint = textColor,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = priority.name.lowercase().replaceFirstChar { it.uppercase() },
                color = textColor,
                style = TudeeTheme.textStyle.label.medium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PriorityChipPreview() {
    TudeeTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            PriorityChip(priority = Priority.HIGH)
            PriorityChip(priority = Priority.MEDIUM)
            PriorityChip(priority = Priority.LOW)
        }
    }
}