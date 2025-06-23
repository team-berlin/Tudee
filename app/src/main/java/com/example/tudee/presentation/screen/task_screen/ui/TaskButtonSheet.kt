package com.example.tudee.presentation.screen.task_screen.ui

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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tudee.R
import com.example.tudee.data.mapper.getCategoryIcon
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskBottomSheetState
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.components.CategoryComponent
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.components.TudeeDateDialog
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.components.buttons.ButtonColors
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.DefaultButton
import com.example.tudee.presentation.components.buttons.PrimaryButton
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    taskState: TaskBottomSheetState,
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
    onCancelButtonClicked: () -> Unit,
    onDateFieldClicked: () -> Unit,
    onConfirmDatePicker: (Long?) -> Unit,
    onDismissDatePicker: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()


    if (taskState.isDatePickerVisible){
        TudeeDateDialog(
            onConfirm = {onConfirmDatePicker(it)},
            onDismiss = {onDismissDatePicker()},
            onClear = {}
        )
    }

    if (taskState.isButtonSheetVisible) {
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
                    onDateFieldClicked = onDateFieldClicked,
                    isEditMode = isEditMode
                )
                AddOrSaveButtons(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    addButtonState = addButtonState,
                    taskState = taskState,
                    isEditMode = isEditMode,
                    onCancelButtonClicked = {
                        scope.launch {
                            hideButtonSheet()
                            sheetState.hide()
                            onCancelButtonClicked()
                        }
                    },
                    onSaveClicked = { task ->
                        scope.launch {
                            onSaveClicked(task)
                            sheetState.hide()

                        }
                    },
                    onAddClicked = { request ->
                        scope.launch {
                            onAddClicked(request)
                            sheetState.hide()
                        }
                    }
                )
            }
        }
    }
 Box (Modifier
     .fillMaxWidth()
     .zIndex(1f)){
     taskState.snackBarMessage?.let { isSuccess ->
     SnackBarSection(
         isSnackBarVisible = isSuccess,
         hideSnackBar = true
     )
 }
 }

}

@Composable
fun BottomSheetContent(
    taskState: TaskBottomSheetState,
    onTaskTitleChanged: (String) -> Unit,
    onTaskDescriptionChanged: (String) -> Unit,
    onUpdateTaskDueDate: (LocalDate) -> Unit,
    onUpdateTaskPriority: (TaskPriority) -> Unit,
    onDateFieldClicked: () -> Unit,
    onSelectTaskCategory: (Long) -> Unit,
    isEditMode: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(isEditMode==true){
            item{
                Text(
                    text = stringResource(R.string.editNewTask) ,
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title
                )
            }
        }else{
            item {
                Text(
                    text = stringResource(R.string.addNewTask) ,
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title
                )
            }

        }

        item {
            TudeeTextField(
                value = taskState.taskTitle,
                onValueChange = onTaskTitleChanged,
                placeholder = stringResource(R.string.task_title),
                leadingContent = { isFocused ->
                    DefaultLeadingContent(
                        painter = painterResource(R.drawable.ic_notebook),
                        isFocused = isFocused
                    )
                }
            )
        }
        item {
            TudeeTextField(
                modifier = Modifier.height(168.dp),
                placeholder = stringResource(R.string.description),
                onValueChange = onTaskDescriptionChanged,
                singleLine = false,
                value = taskState.taskDescription
            )
        }
            item {
                TudeeTextField(
                    value = taskState.taskDueDate?.toString() ?: "2024, 1, 1",
                    onValueChange = { newValue ->
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
                            modifier = Modifier.clickable {
                                onDateFieldClicked()
                            },
                            painter = painterResource(R.drawable.ic_add_calendar),
                            isFocused = isFocused
                        )
                    },
                    placeholder = stringResource(R.string.set_due_date)
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
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                taskState.taskPriorities.forEach { taskPriority ->
                    val isSelected = taskState.selectedTaskPriority == taskPriority
                    TudeeChip(
                        label = taskPriority.name,
                        modifier = Modifier.clickable { onUpdateTaskPriority(taskPriority) },
                        labelColor = if (isSelected) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.hint,
                        backgroundColor = if (isSelected) {
                            when (taskPriority) {
                                TaskPriority.HIGH -> TudeeTheme.color.statusColors.pinkAccent
                                TaskPriority.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
                                TaskPriority.LOW -> TudeeTheme.color.statusColors.greenAccent
                            }
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
                    .padding(bottom = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(taskState.categories) { category ->
                    CategoryComponent(
                        modifier = Modifier.clickable { onSelectTaskCategory(category.id) },
                        categoryPainter = painterResource(getCategoryIcon(category.image)),
                        categoryImageContentDescription = category.title,
                        categoryName = category.title,
                        showCheckedIcon = category.id == taskState.selectedCategoryId
                    )
                }
            }
        }
    }
}

@Composable
private fun Title_operation(title:String) {
    Text(
        text = title,
        style = TudeeTheme.textStyle.title.large,
        color = TudeeTheme.color.textColors.title
    )
}
@Composable
fun AddOrSaveButtons(
    modifier: Modifier = Modifier,
    taskState: TaskBottomSheetState,
    addButtonState: Boolean,
    isEditMode: Boolean,
    onSaveClicked: (Task) -> Unit,
    onAddClicked: (TaskCreationRequest) -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(
            onClick = {
                if (taskState.isEditMode) {
                    val editedTask = taskState.taskDueDate?.let {
                        Task(
                            id = taskState.taskId!!,
                            title = taskState.taskTitle,
                            description = taskState.taskDescription,
                            priority = taskState.selectedTaskPriority!!,
                            status = taskState.taskStatus!!,
                            categoryId = taskState.selectedCategoryId!!,
                            assignedDate = it.toLocalDate()
                        )
                    }
                    onSaveClicked(editedTask!!)
                } else {
                    onAddClicked(
                        TaskCreationRequest(
                            title = taskState.taskTitle,
                            description = taskState.taskDescription,
                            priority = taskState.selectedTaskPriority!!,
                            categoryId = taskState.selectedCategoryId!!,
                            status = TaskStatus.TODO,
                            assignedDate = taskState.taskDueDate?.toLocalDate() ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                        )

                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            state = if (addButtonState) ButtonState.IDLE else ButtonState.DISABLED
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
            border = BorderStroke(1.dp, TudeeTheme.color.stroke),
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