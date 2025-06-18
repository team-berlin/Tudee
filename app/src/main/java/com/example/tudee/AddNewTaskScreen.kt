package com.example.tudee

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.components.CategoryComponent
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.composables.buttons.ButtonColors
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.composables.buttons.DefaultButton
import com.example.tudee.presentation.composables.buttons.PrimaryButton
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddNewTaskScreen() {
    val viewModel: AddNewTaskViewModel = koinViewModel()
    val taskScreenUiState by viewModel.taskState.collectAsState()

    val addButtonState by viewModel.isTaskValid.collectAsState()

    AddNewTaskContent(
        taskState = taskScreenUiState,
        addButtonState = addButtonState,
        onTaskTitleChanged = viewModel::onUpdateTaskTitle,
        onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
        onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
        onUpdateTaskPriority = viewModel::onUpdateTaskPriority,
        onSelectTaskCategory = viewModel::onSelectTaskCategory,
        onAddButtonClicked = viewModel::onAddClicked,
        onCancelButtonClicked = viewModel::onCancelClicked,
    )
}


@Preview(showBackground = true, widthDp = 360, heightDp = 852)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewTaskContent(
    showSheetState: MutableState<Boolean> = mutableStateOf(true),
    taskState: AddNewTaskScreenState = AddNewTaskScreenState(
        taskTitle = "Task",
        taskDescription = "Desc",
        taskDueDate = LocalDateTime(2023, 10, 1, 12, 0, 0),
        selectedTaskPriority = TaskPriority.MEDIUM,
        categories = listOf(
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 2L,
                title = "Work",
                image = "ic_work",
                isPredefined = true
            ),
        ),
        selectedCategoryId = 12344L
    ),
    onTaskTitleChanged: (String) -> Unit = {},
    onTaskDescriptionChanged: (String) -> Unit = {},
    onUpdateTaskDueDate: (LocalDateTime) -> Unit = {},
    onUpdateTaskPriority: (TaskPriority) -> Unit = {},
    onSelectTaskCategory: (Long) -> Unit = {},
    onAddButtonClicked: (TaskCreationRequest) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    addButtonState: Boolean = false,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { it != SheetValue.Hidden },
//        initialValue = SheetValue.Expanded,
    )
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (showSheetState.value) Color(0x99000000) else Color.Transparent) // 60% opacity
    ) {
        if (showSheetState.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showSheetState.value = false
                },
                sheetState = sheetState, // control the visibility of the sheet
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                containerColor = TudeeTheme.color.surface,
                modifier = Modifier.fillMaxHeight(fraction = 0.8f)
            ) {
                Box(
//                    modifier = Modifier
                ) {
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
                        onAddButtonClicked,
                        onCancelButtonClicked,
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 360, heightDp = 852)
@Composable
fun BottomSheetContent(
    taskState: AddNewTaskScreenState = AddNewTaskScreenState(
        taskTitle = "Task",
        taskDescription = "Desc",
        taskDueDate = LocalDateTime(2023, 10, 1, 12, 0, 0),
        selectedTaskPriority = TaskPriority.MEDIUM,
        categories = listOf(
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
            TaskCategory(
                id = 1L,
                title = "Education",
                image = "ic_education",
                isPredefined = true
            ),
        ),
        selectedCategoryId = 12344L
    ),
    onTaskTitleChanged: (String) -> Unit = {},
    onTaskDescriptionChanged: (String) -> Unit = {},
    onUpdateTaskDueDate: (LocalDateTime) -> Unit = {},
    onUpdateTaskPriority: (TaskPriority) -> Unit = {},
    onSelectTaskCategory: (Long) -> Unit = {},
    priorities: List<TaskPriority> = listOf(
        TaskPriority.HIGH,
        TaskPriority.MEDIUM,
        TaskPriority.LOW
    ),
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

                placeholder = "Task title",
                textStyle = TudeeTheme.textStyle.label.medium
            )
        }
        item {
            TudeeTextField(
                modifier = Modifier
                    .height(168.dp),
                placeholder = "Description",
                onValueChange = onTaskDescriptionChanged,
                singleLine = false,
                textStyle = TudeeTheme.textStyle.body.medium,
                value = taskState.taskDescription,
            )
        }
        item {
            TudeeTextField(
                value = taskState.taskDueDate?.date.toString(),
                onValueChange = {},
                leadingContent = { isFocused ->
                    DefaultLeadingContent(
                        painter = painterResource(R.drawable.ic_add_calendar),
                        isFocused = isFocused
                    )
                },
                placeholder = "set due date",
                textStyle = TudeeTheme.textStyle.label.medium
            )
        }
        item {
            Text(
                text = "Priority",
                style = TudeeTheme.textStyle.title.medium,
                color = TudeeTheme.color.textColors.title
            )
        }
        item {
            Row(
                modifier = Modifier,
//                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                priorities.forEach { taskPriority ->
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
                text = "Category",
                style = TudeeTheme.textStyle.title.medium,
                color = TudeeTheme.color.textColors.title
            )
        }
        item {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 104.dp),
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth().padding(bottom = 148.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(9) { category ->
                    CategoryComponent(
                        modifier = Modifier.clickable {
//                            onSelectTaskCategory(category.id)
                        },
                        categoryPainter = painterResource(R.drawable.ic_education),
                        categoryImageContentDescription = "Education Category",
                        categoryName = "Education",
//                        showCheckedIcon = category.id == taskState.selectedCategoryId,
                        showCheckedIcon = true
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddOrCancelButtons(
    modifier: Modifier = Modifier,
    addButtonState: Boolean = true,
    onAddButtonClicked: (TaskCreationRequest) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            state = if (addButtonState) ButtonState.IDLE else ButtonState.DISABLED,
        ) {
            Text(
                text = "Add",
                style = TudeeTheme.textStyle.label.large,
                color = TudeeTheme.color.textColors.onPrimary
            )
        }
        DefaultButton(
            onClick = {},
            enabled = false,
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
                text = "Cancel",
                style = TudeeTheme.textStyle.label.large,
                color = TudeeTheme.color.primary
            )
        }
    }
}
