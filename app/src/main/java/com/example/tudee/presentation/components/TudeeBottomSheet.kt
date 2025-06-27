package com.example.tudee.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.SecondaryButton
import com.example.tudee.presentation.screen.category.model.toUiImage
import com.example.tudee.presentation.screen.home.viewmodel.CategoryUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskPriorityUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskStatusUiState
import com.example.tudee.presentation.screen.home.viewmodel.TaskUiState
import com.example.tudee.presentation.utils.clickWithRipple
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    content: @Composable ColumnScope.() -> Unit
) {
    if (isVisible.not()) return
    ModalBottomSheet(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        containerColor = TudeeTheme.color.surface,
        sheetMaxWidth = sheetMaxWidth,
        shape = shape,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor,
    ) {
        content()
    }
}

//region TaskDetailsBottomSheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeTaskDetailsBottomSheet(
    task: TaskUiState,
    onEditButtonClicked: () -> Unit,
    changeStatusButtonState: ButtonState,
    isChangeStatusButtonEnable: Boolean,
    onMoveActionClicked: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = false
) {
    TudeeBottomSheet(isVisible = isVisible, onDismissRequest = { onDismissRequest() }) {
        TudeeTaskDetailsBottomSheetContent(
            task = task,
            onEditButtonClicked = { onEditButtonClicked() },
            changeStatusButtonState = changeStatusButtonState,
            isChangeStatusButtonEnable = isChangeStatusButtonEnable,
            onMoveActionClicked = { onMoveActionClicked() }
        )
    }
}

@Composable
private fun TudeeTaskDetailsBottomSheetContent(
    task: TaskUiState,
    onEditButtonClicked: () -> Unit,
    changeStatusButtonState: ButtonState,
    onMoveActionClicked: () -> Unit,
    isChangeStatusButtonEnable: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = TudeeTheme.color.surface)
            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        TudeeTaskDetails(task = task)
        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            thickness = 1.dp,
            color = TudeeTheme.color.stroke
        )
        TudeeTaskDetailPriorityRow(task)
        if (task.taskStatusUiState != TaskStatusUiState.DONE) {
            TudeeTaskDetailsButtonsRow(
                task = task,
                onEditButtonClicked = onEditButtonClicked,
                onMoveActionClicked = onMoveActionClicked,
                changeStatusButtonState = changeStatusButtonState,
                isChangeStatusButtonEnable = isChangeStatusButtonEnable
            )
        }
    }
}

@Composable
private fun TudeeTaskDetails(task: TaskUiState, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.task_details),
        color = TudeeTheme.color.textColors.title,
        style = TudeeTheme.textStyle.title.large
    )
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .background(color = TudeeTheme.color.surfaceHigh, shape = CircleShape)
    ) {
        task.taskCategory.image?.let {
            it.toUiImage().asPainter()
        }?.let {
            Image(
                modifier = Modifier.padding(15.dp), painter = it,
                contentDescription = null
            )
        }
    }
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = task.taskTitle,
        color = TudeeTheme.color.textColors.title,
        style = TudeeTheme.textStyle.title.large
    )
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = task.taskDescription,
        color = TudeeTheme.color.textColors.body,
        style = TudeeTheme.textStyle.title.small
    )
}

@Composable
private fun TudeeTaskDetailPriorityRow(task: TaskUiState, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(top = 12.dp, bottom = 24.dp)) {
        Row(
            modifier = Modifier.background(
                TudeeTheme.color.statusColors.purpleVariant,
                shape = CircleShape
            ), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(5.dp)
                    .background(
                        color = TudeeTheme.color.statusColors.purpleAccent,
                        shape = CircleShape
                    )
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .padding(start = 4.dp, end = 12.dp),
                text = stringResource(task.taskStatusUiState.status),
                color = TudeeTheme.color.statusColors.purpleAccent,
                style = TudeeTheme.textStyle.label.small
            )
        }
        TudeeChip(
            modifier = Modifier.padding(start = 8.dp),
            label = stringResource(task.taskPriority.priority),
            icon = painterResource(
                when (task.taskPriority) {
                    TaskPriorityUiState.LOW -> R.drawable.ic_priority_low
                    TaskPriorityUiState.MEDIUM -> R.drawable.ic_priority_medium
                    TaskPriorityUiState.HIGH -> R.drawable.ic_priority_high
                }
            ),
            backgroundColor = when (task.taskPriority) {
                TaskPriorityUiState.LOW -> TudeeTheme.color.statusColors.greenAccent
                TaskPriorityUiState.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
                TaskPriorityUiState.HIGH -> TudeeTheme.color.statusColors.pinkAccent
            }
        )
    }
}

@Composable
private fun TudeeTaskDetailsButtonsRow(
    task: TaskUiState,
    onEditButtonClicked: () -> Unit,
    onMoveActionClicked: () -> Unit,
    changeStatusButtonState: ButtonState,
    isChangeStatusButtonEnable: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = TudeeTheme.color.stroke,
                    shape = CircleShape
                )
                .background(
                    color = TudeeTheme.color.surface,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickWithRipple(onClick = onEditButtonClicked),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .size(24.dp),
                painter = painterResource(R.drawable.ic_edit_task),
                contentDescription = null,
                tint = TudeeTheme.color.primary
            )
        }

        SecondaryButton(
            onClick = { onMoveActionClicked() },
            modifier = Modifier
                .weight(4f)
                .border(
                    width = 1.dp,
                    color = TudeeTheme.color.stroke,
                    shape = RoundedCornerShape(100.dp)
                ),
            state = changeStatusButtonState,
            enabled = isChangeStatusButtonEnable,
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),

            ) {
            Text(
                text = when (task.taskStatusUiState) {
                    TaskStatusUiState.TODO -> stringResource(R.string.move_to_in_progress)
                    else -> stringResource(R.string.move_to_done)
                },
                style = TudeeTheme.textStyle.label.large

            )
        }
    }
}

//endregion
@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomSheetPreview() {
    TudeeTheme {
        val previewTaskUiState = TaskUiState(
            taskId = "1",
            taskTitle = R.string.default_task_title.toString(),
            taskDescription = R.string.default_task_description.toString(),
            taskPriority = TaskPriorityUiState.HIGH,
            taskCategory = CategoryUiState(
                id = 0,
                title = "Work",
                isPredefined = true
            ),
            taskStatusUiState = TaskStatusUiState.DONE,
            taskAssignedDate = LocalDate(2025, 6, 19)
        )
        TudeeTaskDetailsBottomSheet(
            task = previewTaskUiState,
            onEditButtonClicked = {},
            changeStatusButtonState = ButtonState.IDLE,
            isChangeStatusButtonEnable = true,
            onMoveActionClicked = {},
            onDismissRequest = {},
            isVisible = true
        )
    }
}