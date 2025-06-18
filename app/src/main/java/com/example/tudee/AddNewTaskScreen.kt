package com.example.tudee

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
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
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddNewTaskScreen() {
    val viewModel: AddNewTaskViewModel = koinViewModel()
    val taskScreenUiState by viewModel.uiState.collectAsState()

    val addButtonState by viewModel.isTaskValid.collectAsState()

    AddNewTaskContent(
        taskState = taskScreenUiState,
        onTaskTitleChanged = viewModel::onUpdateTaskTitle,
        onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
        onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
        onUpdateTaskPriority = viewModel::onUpdateTaskPriority,
        onSelectTaskCategory = viewModel::onSelectTaskCategory,
        addButtonState = addButtonState,
        onAddButtonClicked = viewModel::onAddClicked,
        onDismissBottomSheet = viewModel::onDismissBottomSheet,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskContent(
    taskState: AddNewTaskScreenState,
    onTaskTitleChanged: (String) -> Unit,
    onTaskDescriptionChanged: (String) -> Unit,
    onUpdateTaskDueDate: (LocalDate) -> Unit,
    onUpdateTaskPriority: (TaskPriority) -> Unit,
    onSelectTaskCategory: (Long) -> Unit,
    onAddButtonClicked: (TaskCreationRequest) -> Unit,
    addButtonState: Boolean,
    onDismissBottomSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val scope = rememberCoroutineScope()
    // Animate show/hide of the sheet
    LaunchedEffect(taskState.showBottomSheet) {
        if (taskState.showBottomSheet)
            sheetState.show()
        else
            sheetState.hide()
    }
    if (taskState.showBottomSheet || sheetState.currentValue != SheetValue.Hidden)
        ModalBottomSheet(
            onDismissRequest = onDismissBottomSheet,
            sheetState = sheetState, // control the visibility of the sheet
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
                AddOrCancelButtons(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    addButtonState = addButtonState,
                    taskState = taskState,
                    onAddButtonClicked = onAddButtonClicked,
                    onCancelButtonClicked = {
                        scope.launch {
                            sheetState.hide()
                            onDismissBottomSheet()
                        }
                    })
            }
        }

}

//@Preview(showBackground = true, widthDp = 360, heightDp = 852)
@Composable
fun BottomSheetContent(
    taskState: AddNewTaskScreenState,
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
                value = "2024, 1, 1",
                onValueChange = {},
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
                        backgroundColor = if (isSelected) TudeeTheme.color.statusColors.pinkAccent else TudeeTheme.color.surfaceLow,
                        icon = when (taskPriority) {
                            TaskPriority.HIGH -> painterResource(id = R.drawable.ic_priority_high)
                            TaskPriority.MEDIUM -> painterResource(id = R.drawable.ic_priority_medium)
                            TaskPriority.LOW -> painterResource(id = R.drawable.ic_priority_low)
                        }, iconSize = 12.dp
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
fun AddOrCancelButtons(
    modifier: Modifier = Modifier,
    taskState: AddNewTaskScreenState,
    addButtonState: Boolean,
    onAddButtonClicked: (TaskCreationRequest) -> Unit,
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
                onAddButtonClicked(
                    taskState.run {
                        TaskCreationRequest(
                            title = taskTitle,
                            description = taskDescription,
                            priority = selectedTaskPriority!!,
                            categoryId = selectedCategoryId!!,
                            status = TaskStatus.TODO,
                            assignedDate = LocalDate(2024, 1, 1)
                        )
                    }
                )
                Log.d("MEME", "Add Task Button state = Task added Successfully")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            state = if (addButtonState) ButtonState.IDLE else ButtonState.DISABLED,
        ) {
            Text(
                text = stringResource(R.string.add),
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
