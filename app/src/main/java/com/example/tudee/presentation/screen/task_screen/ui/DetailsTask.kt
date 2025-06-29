package com.example.tudee.presentation.screen.task_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.components.buttons.SecondaryButton
import com.example.tudee.presentation.screen.category.model.toUiImage
import com.example.tudee.presentation.screen.task_screen.mappers.TaskPriorityUiState
import com.example.tudee.presentation.screen.task_screen.mappers.TaskStatusUiState
import com.example.tudee.presentation.screen.task_screen.mappers.toDomain
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskDetailsUiState
import com.example.tudee.presentation.utils.clickWithRipple
import com.example.tudee.presentation.viewmodel.TaskBottomSheetViewModel

@Composable
fun TaskDetailsScreen(
    taskDetailsState: TaskDetailsUiState,
    taskBottomSheetViewModel: TaskBottomSheetViewModel,
    hideAddTaskBottomSheet: () -> Unit,
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
        if (taskDetailsState.status.toDomain() != TaskStatus.DONE) {
            TaskActionButtons(taskBottomSheetViewModel, taskDetailsState, hideAddTaskBottomSheet)
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
private fun TaskCategoryIcon(categoryIconRes: String) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(TudeeTheme.color.surfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = categoryIconRes.toUiImage().asPainter(),
            contentDescription = "category icon",
            modifier = Modifier.size(32.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun TaskTitleAndDescription(title: String, description: String) {
    Text(
        text = title,
        style = TudeeTheme.textStyle.label.large,
        color = TudeeTheme.color.textColors.title
    )
    Text(
        text = description,
        style = TudeeTheme.textStyle.label.small,
        color = TudeeTheme.color.textColors.body
    )
}

@Composable
private fun TaskStatusAndPriorityChips(status: TaskStatusUiState, priority: TaskPriorityUiState) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        TudeeChip(
            label = stringResource(status.label),
            icon = painterResource(id = R.drawable.ic_task_status),
            backgroundColor = status.containerColor,
            labelColor = status.contentColor,
            iconSize = 5.dp
        )
        TudeeChip(
            label = priority.name.lowercase().replaceFirstChar { it.uppercase() },
            icon = painterResource(priority.icon),
            backgroundColor = priority.containerColor,
            labelColor = TudeeTheme.color.textColors.onPrimary
        )
    }
}

@Composable
private fun TaskActionButtons(
    taskBottomSheetViewModel: TaskBottomSheetViewModel,
    taskDetailsState: TaskDetailsUiState,
    hideAddTaskBottomSheet: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconFab(
            onClick = {
                taskBottomSheetViewModel.getTaskInfoById(taskDetailsState.id)
                taskBottomSheetViewModel.run {
                    hideAddTaskBottomSheet()
                    showButtonSheet()
                }
            },
            icon = painterResource(R.drawable.pencil_edit),
            contentDescription = stringResource(R.string.edit_icon)
        )
        SecondaryButton(
            onClick = {
                taskBottomSheetViewModel.updateTaskStatusToDone(taskDetailsState.id)
                hideAddTaskBottomSheet()
            },
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
            .clickWithRipple(onClick = onClick)
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


