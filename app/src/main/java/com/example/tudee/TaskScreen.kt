package com.example.tudee

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.components.CategoryComponent
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.composables.buttons.ButtonColors
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.composables.buttons.DefaultButton
import com.example.tudee.presentation.composables.buttons.PrimaryButton
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


// Sealed class for screen selection
sealed class TaskScreenSelected {
    object AddScreen : TaskScreenSelected()
    object EditScreen : TaskScreenSelected()
}

@Composable
fun TaskScreen(selectedScreen: TaskScreenSelected) {
    when (selectedScreen) {
        is TaskScreenSelected.AddScreen -> AddScreen()
        is TaskScreenSelected.EditScreen -> EditScreen()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    taskState: TaskScreenState,
    onTaskTitleChanged: (String) -> Unit,
    onTaskDescriptionChanged: (String) -> Unit,
    onUpdateTaskDueDate: (LocalDate) -> Unit,
    onUpdateTaskPriority: (TaskPriority) -> Unit,
    onSelectTaskCategory: (Long) -> Unit,
    addButtonState: Boolean,
    hideButtonSheet: () -> Unit,
    isEditMode: Boolean,
    onSaveClicked: (Task) -> Unit,
    onAddClicked: (TaskCreationRequest) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()


    LaunchedEffect(taskState.showBottomSheet) {
        if (taskState.showBottomSheet)
            sheetState.show()
        else
            sheetState.hide()
    }
    if (taskState.showBottomSheet || sheetState.currentValue != SheetValue.Hidden)
        ModalBottomSheet(
            onDismissRequest = hideButtonSheet,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = TudeeTheme.color.surface,
            modifier = Modifier.fillMaxHeight(fraction = 0.8f)
        ) {
            Box {
                BottomSheetContent(
                    taskState = taskState,
                    onTaskTitleChanged = onTaskTitleChanged,
                    onTaskDescriptionChanged = onTaskDescriptionChanged,
                    onUpdateTaskDueDate = onUpdateTaskDueDate,
                    onUpdateTaskPriority = onUpdateTaskPriority,
                    onSelectTaskCategory = onSelectTaskCategory,
                )
                AddOrSaveButtons(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    addButtonState = addButtonState,
                    taskState = taskState,
                    isEditMode = isEditMode,
                    onCancelButtonClicked = {
                        scope.launch {
                            sheetState.hide()
                            hideButtonSheet()
                        }
                    },
                    onSaveClicked = onSaveClicked,
                    onAddClicked = onAddClicked,
                )
            }
        }
}

@Composable
fun BottomSheetContent(
    taskState: TaskScreenState,
    onTaskTitleChanged: (String) -> Unit,
    onTaskDescriptionChanged: (String) -> Unit,
    onUpdateTaskDueDate: (LocalDate) -> Unit,
    onUpdateTaskPriority: (TaskPriority) -> Unit,
    onSelectTaskCategory: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TudeeTextField(
                value = taskState.taskTitle,
                onValueChange = onTaskTitleChanged,
                leadingContent = { isFocused ->
                    DefaultLeadingContent(
                        painter = painterResource(R.drawable.ic_notebook),
                        isFocused = isFocused
                    )
                },
                placeholder = stringResource(R.string.task_title),
                textStyle = TudeeTheme.textStyle.label.medium
            )
        }
        item {
            TudeeTextField(
                modifier = Modifier
                    .height(168.dp),
                placeholder = stringResource(R.string.description),
                onValueChange = onTaskDescriptionChanged,
                singleLine = false,
                textStyle = TudeeTheme.textStyle.body.medium,
                value = taskState.taskDescription,
            )
        }
        item {
            TudeeTextField(
                value = taskState.taskDueDate?.toString()
                    ?: "2024, 1, 1", // Display due date if available
                onValueChange = { newValue ->
                    // Parse the new value into LocalDate (simplified; adjust parsing logic as needed)
                    val parts = newValue.split(", ")
                    if (parts.size == 3) {
                        try {
                            val year = parts[0].toInt()
                            val month = parts[1].toInt()
                            val day = parts[2].toInt()
                            onUpdateTaskDueDate(LocalDate(year, month, day))
                        } catch (e: Exception) {
                            Log.e(
                                "BottomSheetContent",
                                "Invalid date format: $newValue, ${e.message}"
                            )
                        }
                    }
                },
                leadingContent = { isFocused ->
                    DefaultLeadingContent(
                        painter = painterResource(R.drawable.ic_add_calendar),
                        isFocused = isFocused
                    )
                },
                placeholder = stringResource(R.string.set_due_date),
                textStyle = TudeeTheme.textStyle.label.medium
            )
        }
        item {
            Text(
                text = stringResource(R.string.priority),
                style = TudeeTheme.textStyle.title.medium,
                color = TudeeTheme.color.textColors.title
            )
        }
        item {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                taskState.taskPriorities.forEach { taskPriority ->
                    val isSelected: Boolean = taskState.selectedTaskPriority == taskPriority
                    TudeeChip(
                        label = taskPriority.name,
                        modifier = Modifier.clickable {
                            onUpdateTaskPriority(taskPriority)
                        },
                        labelColor = if (isSelected) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.hint,
                        backgroundColor = if (isSelected) {
                            when(taskPriority){
                                TaskPriority.HIGH -> TudeeTheme.color.statusColors.pinkAccent
                                TaskPriority.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
                                TaskPriority.LOW -> TudeeTheme.color.statusColors.greenAccent
                            }
                            TudeeTheme.color.statusColors.pinkAccent
                        } else TudeeTheme.color.surfaceLow,
                        icon = when (taskPriority) {
                            TaskPriority.HIGH -> painterResource(id = R.drawable.ic_priority_high)
                            TaskPriority.MEDIUM -> painterResource(id = R.drawable.ic_priority_medium)
                            TaskPriority.LOW -> painterResource(id = R.drawable.ic_priority_low)
                        },
                        iconSize = 12.dp
                    )
                }
            }
        }
        item {
            Text(
                text = stringResource(R.string.category),
                style = TudeeTheme.textStyle.title.medium,
                color = TudeeTheme.color.textColors.title
            )
        }
        item {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 104.dp),
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .padding(bottom = 148.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(taskState.categories) {
                    Log.d("MEME", "Category: ${it.toString()}")
                    CategoryComponent(
                        modifier = Modifier.clickable {
                            onSelectTaskCategory(it.id)
                        },
                        categoryPainter = painterResource(R.drawable.ic_education),
                        categoryImageContentDescription = "Education Category",
                        categoryName = "Education",
                        showCheckedIcon = it.id == taskState.selectedCategoryId,
                    )
                }
            }
        }
    }
}

@Composable
fun AddOrSaveButtons(
    modifier: Modifier = Modifier,
    taskState: TaskScreenState,
    addButtonState: Boolean,
    isEditMode: Boolean, // New parameter to indicate edit mode
    onSaveClicked: (Task) -> Unit,
    onAddClicked: (TaskCreationRequest) -> Unit,
    onCancelButtonClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(
            onClick = {
                if (taskState.isEditMode) {
                    val editedTask = Task(
                        id = taskState.taskId!!,
                        title = taskState.taskTitle,
                        description = taskState.taskDescription,
                        priority = taskState.selectedTaskPriority!!,
                        status = taskState.taskStatus!!,
                        categoryId = taskState.selectedCategoryId!!,
                        assignedDate = taskState.taskDueDate!!
                    )
                    onSaveClicked(editedTask)
                } else {
                    onAddClicked(
                        TaskCreationRequest(
                            title = taskState.taskTitle,
                            description = taskState.taskDescription,
                            priority = taskState.selectedTaskPriority!!,
                            categoryId = taskState.selectedCategoryId!!,
                            status = TaskStatus.TODO,
                            assignedDate = LocalDate(2024, 1, 1),
                        )
                    )
                }
                Log.d(
                    "MEME",
                    if (isEditMode) "Edit Task Button state = Task edited Successfully" else "Add Task Button state = Task added Successfully"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            state = if (addButtonState) ButtonState.IDLE else ButtonState.DISABLED,
        ) {
            Text(
                text = stringResource(if (taskState.isEditMode) R.string.save else R.string.add),
                style = TudeeTheme.textStyle.label.large,
                color = TudeeTheme.color.textColors.onPrimary
            )
        }
        DefaultButton(
            onClick = onCancelButtonClicked,
            enabled = true,
            state = ButtonState.IDLE,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = BorderStroke(
                1.dp,
                TudeeTheme.color.stroke
            ),
            colors = ButtonColors(backgroundColor = TudeeTheme.color.surfaceHigh)
        ) {
            Text(
                text = stringResource(R.string.cancel),
                style = TudeeTheme.textStyle.label.large,
                color = TudeeTheme.color.primary
            )
        }
    }
}