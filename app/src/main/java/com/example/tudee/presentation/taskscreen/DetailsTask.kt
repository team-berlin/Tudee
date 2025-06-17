package com.example.tudee.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.viewmodel.taskuistate.TaskDetailsUiState
import com.example.tudee.presentation.utils.Constant.CATEGORY_ICON_DESC
import com.example.tudee.presentation.utils.Constant.EDIT_ICON_DESC
import com.example.tudee.presentation.utils.Constant.MOVE_TO_DONE
import com.example.tudee.presentation.utils.Constant.PRIORITY_HIGH
import com.example.tudee.presentation.utils.Constant.PRIORITY_LOW
import com.example.tudee.presentation.utils.Constant.PRIORITY_MEDIUM
import com.example.tudee.presentation.utils.Constant.STATUS_DONE
import com.example.tudee.presentation.utils.Constant.STATUS_IN_PROGRESS
import com.example.tudee.presentation.utils.Constant.STATUS_TODO
import com.example.tudee.presentation.utils.Constant.TASK_DETAILS

@Composable
fun TaskDetailsScreen(taskDetailsState: TaskDetailsUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TaskDetailsHeader()
        TaskCategoryIcon(taskDetailsState.categoryIconRes)
        TaskTitleAndDescription(taskDetailsState.title, taskDetailsState.description)
        HorizontalDivider(thickness = 2.dp, color = TudeeTheme.color.stroke)
        TaskStatusAndPriorityChips(
            status = taskDetailsState.status,
            priority = taskDetailsState.priority
        )
        if (taskDetailsState.status.lowercase() != STATUS_DONE) {
            TaskActionButtons()
        }
    }
}

@Composable
private fun TaskDetailsHeader() {
    Text(
        text = TASK_DETAILS,
        style = TudeeTheme.textStyle.title.large.copy(color = TudeeTheme.color.textColors.title)
    )
}

@Composable
private fun TaskCategoryIcon(categoryIconRes: Int) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(TudeeTheme.color.surfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = categoryIconRes),
            contentDescription = CATEGORY_ICON_DESC,
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun TaskTitleAndDescription(title: String, description: String) {
    Text(text = title, style = TudeeTheme.textStyle.label.large)
    Text(text = description, style = TudeeTheme.textStyle.label.small)
}

@Composable
private fun TaskStatusAndPriorityChips(status: String, priority: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TudeeChip(
            label = status,
            icon = painterResource(id = R.drawable.ic_task_status),
            backgroundColor = getStatusLabelColor(status),
            labelColor = getStatusColor(status),
            iconSize = 5.dp
        )
        TudeeChip(
            label = priority,
            icon = painterResource(getPriorityIcon(priority)),
            backgroundColor = getPriorityColor(priority),
            labelColor = TudeeTheme.color.textColors.onPrimary
        )
    }
}

@Composable
private fun TaskActionButtons() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconFab(
            onClick = {},
            icon = painterResource(R.drawable.pencil_edit),
            contentDescription = EDIT_ICON_DESC
        )
        SecondaryButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            content = {
                Text(
                    text = MOVE_TO_DONE,
                    style = TudeeTheme.textStyle.label.large
                )
            }
        )
    }
}

@Composable
fun IconFab(
    onClick: () -> Unit,
    icon: Painter,
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .width(72.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = 1.dp,
                color = TudeeTheme.color.stroke,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable(onClick = onClick)
            .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp),
            tint = TudeeTheme.color.primary
        )
    }
}

private fun getPriorityIcon(priority: String): Int {
    return when (priority.lowercase()) {
        PRIORITY_HIGH -> R.drawable.ic_priority_high
        PRIORITY_MEDIUM -> R.drawable.ic_priority_medium
        PRIORITY_LOW -> R.drawable.ic_priority_low
        else -> R.drawable.ic_priority_low
    }
}

@Composable
private fun getPriorityColor(priority: String): Color {
    return when (priority.lowercase()) {
        PRIORITY_HIGH -> TudeeTheme.color.statusColors.pinkAccent
        PRIORITY_MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
        PRIORITY_LOW -> TudeeTheme.color.statusColors.greenAccent
        else -> TudeeTheme.color.statusColors.greenAccent
    }
}

@Composable
private fun getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        STATUS_TODO -> TudeeTheme.color.statusColors.yellowAccent
        STATUS_IN_PROGRESS -> TudeeTheme.color.statusColors.purpleAccent
        STATUS_DONE -> TudeeTheme.color.statusColors.greenAccent
        else -> TudeeTheme.color.statusColors.yellowAccent
    }
}

@Composable
private fun getStatusLabelColor(status: String): Color {
    return when (status.lowercase()) {
        STATUS_TODO -> TudeeTheme.color.statusColors.yellowVariant
        STATUS_IN_PROGRESS -> TudeeTheme.color.statusColors.purpleVariant
        STATUS_DONE -> TudeeTheme.color.statusColors.greenVariant
        else -> TudeeTheme.color.statusColors.yellowVariant
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsScreenPreview() {
    TudeeTheme {
        val sampleTask = TaskDetailsUiState(
            id = 1L,
            title = "Study Jetpack Compose",
            description = "Finish the layout chapter and preview tips",
            categoryIconRes = R.drawable.ic_category_book_open,
            priority = PRIORITY_HIGH,
            status = STATUS_IN_PROGRESS,
        )
        TaskDetailsScreen(sampleTask)
    }
}