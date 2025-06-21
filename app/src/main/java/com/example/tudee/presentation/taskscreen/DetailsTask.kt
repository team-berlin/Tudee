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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.presentation.viewmodel.AddTaskBottomSheetViewModel
import com.example.tudee.presentation.viewmodel.taskuistate.TaskDetailsUiState

@Composable
fun TaskDetailsScreen(
    taskDetailsState: TaskDetailsUiState,
    addTaskBottomSheetViewModel: AddTaskBottomSheetViewModel

) {
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
        if (taskDetailsState.status != TaskStatus.DONE) {
            TaskActionButtons(addTaskBottomSheetViewModel,taskDetailsState)
        }
    }
}

@Composable
private fun TaskDetailsHeader() {
    Text(
        text = stringResource(R.string.task_details),
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
            painter = painterResource(R.drawable.tudee),
            contentDescription = stringResource(R.string.category_icon),
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
private fun TaskStatusAndPriorityChips(status: TaskStatus, priority: TaskPriority) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TudeeChip(
            label = status.name.lowercase().replaceFirstChar { it.uppercase() },
            icon = painterResource(id = R.drawable.ic_task_status),
            backgroundColor = getStatusLabelColor(status),
            labelColor = getStatusColor(status),
            iconSize = 5.dp
        )
        TudeeChip(
            label = priority.name.lowercase().replaceFirstChar { it.uppercase() },
            icon = painterResource(getPriorityIcon(priority)),
            backgroundColor = getPriorityColor(priority),
            labelColor = TudeeTheme.color.textColors.onPrimary
        )
    }
}

@Composable
private fun TaskActionButtons(
    addTaskBottomSheetViewModel: AddTaskBottomSheetViewModel,
    taskDetailsState: TaskDetailsUiState) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconFab(
            onClick = {
                addTaskBottomSheetViewModel.getTaskInfoById(taskDetailsState.id)
                addTaskBottomSheetViewModel.run {
                    showButtonSheet()
                } },
            icon = painterResource(R.drawable.pencil_edit),
            contentDescription = stringResource(R.string.edit_icon)
        )
        SecondaryButton(
            onClick = {},
            modifier = Modifier.weight(1f),
            content = {
                Text(
                    text = stringResource(R.string.move_to_done),
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
            .padding(horizontal = 24.dp, vertical = 8.dp),
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

private fun getPriorityIcon(priority: TaskPriority): Int {
    return when (priority) {
        TaskPriority.HIGH -> R.drawable.ic_priority_high
        TaskPriority.MEDIUM -> R.drawable.ic_priority_medium
        TaskPriority.LOW -> R.drawable.ic_priority_low
    }
}

@Composable
private fun getPriorityColor(priority: TaskPriority): Color {
    return when (priority) {
        TaskPriority.HIGH -> TudeeTheme.color.statusColors.pinkAccent
        TaskPriority.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
        TaskPriority.LOW -> TudeeTheme.color.statusColors.greenAccent
    }
}

@Composable
private fun getStatusColor(status: TaskStatus): Color {
    return when (status) {
        TaskStatus.TODO -> TudeeTheme.color.statusColors.yellowAccent
        TaskStatus.IN_PROGRESS -> TudeeTheme.color.statusColors.purpleAccent
        TaskStatus.DONE -> TudeeTheme.color.statusColors.greenAccent
    }
}

@Composable
private fun getStatusLabelColor(status: TaskStatus): Color {
    return when (status) {
        TaskStatus.TODO -> TudeeTheme.color.statusColors.yellowVariant
        TaskStatus.IN_PROGRESS -> TudeeTheme.color.statusColors.purpleVariant
        TaskStatus.DONE -> TudeeTheme.color.statusColors.greenVariant
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TaskDetailsScreenPreview() {
//    TudeeTheme {
//        val sampleTask = TaskDetailsUiState(
//            id = 1L,
//            title = "Study Jetpack Compose",
//            description = "Finish the layout chapter and preview tips",
//            categoryIconRes = R.drawable.ic_category_book_open,
//            priority = TaskPriority.HIGH,
//            status = TaskStatus.IN_PROGRESS,
//        )
//        TaskDetailsScreen(sampleTask, {})
//    }
//}