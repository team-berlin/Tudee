package com.example.tudee

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.components.CategoryComponent
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.composables.buttons.ButtonColors
import com.example.tudee.presentation.composables.buttons.DefaultButton
import com.example.tudee.presentation.composables.buttons.PrimaryButton
import kotlinx.datetime.LocalDateTime


@Composable
fun AddNewTaskScreen(
) {
    val viewModel: AddNewTaskViewModel = viewModel()
    val taskScreenUiState by viewModel.taskState.collectAsState()

    val addButtonState by viewModel.isCreateTaskRequestValid.collectAsState()

    Content(
        taskState = taskScreenUiState,
        onTaskTitleChanged = viewModel::onUpdateTaskTitle,
        onTaskDescriptionChanged = viewModel::onUpdateTaskDescription,
        onUpdateTaskDueDate = viewModel::onUpdateTaskDueDate,
        onUpdateTaskPriority = viewModel::onUpdateTaskPriority,
        onSelectTaskCategory = viewModel::onSelectTaskCategory,
        onAddButtonClicked = viewModel::onAddClicked,
        onCancelButtonClicked = viewModel::onCancelClicked,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    showSheetState: MutableState<Boolean> = mutableStateOf(true),
    taskState: AddNewTaskScreenState,
    onTaskTitleChanged: (String) -> Unit,
    onTaskDescriptionChanged: (String) -> Unit,
    onUpdateTaskDueDate: (LocalDateTime) -> Unit,
    onUpdateTaskPriority: (TaskPriority) -> Unit = {},
    onAddButtonClicked: (TaskCreationRequest) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onSelectTaskCategory: (Long) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
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
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {
                BottomSheetContent(
                    taskState = taskState,
                    taskTitle = taskState.taskTitle,
                    onTaskTitleChanged = onTaskTitleChanged,
                    taskDescription = taskState.taskDescription,
                    onTaskDescriptionChanged = onTaskDescriptionChanged,
                    onUpdateTaskDueDate = onUpdateTaskDueDate,
                    onUpdateTaskPriority = onUpdateTaskPriority,
                    onSelectTaskCategory = onSelectTaskCategory,
                )
                AddOrCancelButtons()
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 360, heightDp = 852)
@Composable
fun BottomSheetContent(
    taskState: AddNewTaskScreenState,
    taskTitle: String = "Task 1",
    onTaskTitleChanged: (String) -> Unit,
    taskDescription: String = "This is a task description",
    onTaskDescriptionChanged: (String) -> Unit,
    onUpdateTaskDueDate: (LocalDateTime) -> Unit,
    onUpdateTaskPriority: (TaskPriority) -> Unit = {},
    onSelectTaskCategory: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TudeeTextField(
            value = taskTitle,
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
        TudeeTextField(
            modifier = Modifier
                .height(168.dp),
            placeholder = "Description",
            singleLine = false,
            textStyle = TudeeTheme.textStyle.body.medium,
            value = taskDescription,
            onValueChange = onTaskDescriptionChanged
        )
        TudeeTextField(
            value = taskTitle,
            onValueChange = onTaskTitleChanged,
            leadingContent = { isFocused ->
                DefaultLeadingContent(
                    painter = painterResource(R.drawable.ic_add_calendar),
                    isFocused = isFocused
                )
            },
            placeholder = "set due date",
            textStyle = TudeeTheme.textStyle.label.medium
        )
        Text(
            text = "Priority",
            style = TudeeTheme.textStyle.title.medium,
            color = TudeeTheme.color.textColors.title
        )
        Row(
            modifier = Modifier
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.spacedBy(8.dp),

            ) {
            val priorities = listOf("High", "Medium", "Low")
            var selected by remember { mutableStateOf("High") }
            priorities.forEach { label ->
                val isSelected = selected == label
                TudeeChip(
                    label = label,
                    modifier = Modifier.clickable {

                    },
                    labelColor = if (isSelected) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.hint,
                    backgroundColor = if (isSelected) TudeeTheme.color.statusColors.pinkAccent else TudeeTheme.color.surfaceLow,
                    icon = when (label) {
                        "High" -> painterResource(id = R.drawable.ic_priority_high)
                        "Medium" -> painterResource(id = R.drawable.ic_priority_medium)
                        "Low" -> painterResource(id = R.drawable.ic_priority_low)
                        else -> null
                    }, iconSize = 12.dp
                )
            }
        }
        Text(
            text = "Category",
            style = TudeeTheme.textStyle.title.medium,
            color = TudeeTheme.color.textColors.title
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 104.dp),
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(taskState.categories) {
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

@Composable
@Preview(showBackground = true)
fun AddOrCancelButtons(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text(
                text = "Add",
                style = TudeeTheme.textStyle.label.large,
                color = TudeeTheme.color.textColors.onPrimary
            )
        }
        DefaultButton(
            onClick = {},
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
