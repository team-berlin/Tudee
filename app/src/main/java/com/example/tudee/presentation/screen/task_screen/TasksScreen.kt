package com.example.tudee.presentation.screen.task_screen


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.CategoryTaskComponent
import com.example.tudee.presentation.components.SnackBarComponent
import com.example.tudee.presentation.components.TabBarComponent
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.components.TopAppBar
import com.example.tudee.presentation.components.TudeeDayCard
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.composables.buttons.NegativeButton
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.presentation.composables.buttons.TextButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun TasksScreen() {
    val tasksScreenViewModel: TasksScreenViewModel = koinViewModel()
    val taskScreenUiState by tasksScreenViewModel.taskScreenUiState.collectAsState()

    TasksScreenContent(
        taskScreenUiState = taskScreenUiState,
        onTabSelected = tasksScreenViewModel::onTabSelected,
        onFloatingActionClicked = tasksScreenViewModel::onFloatingActionClicked,
        onTaskCardClicked = tasksScreenViewModel::onTaskCardClicked,
        onDayCardClicked = tasksScreenViewModel::onDayCardClicked,
        onDeleteIconClicked = tasksScreenViewModel::onDeleteIconClicked,
        onDeleteButtonClicked = tasksScreenViewModel::onDeleteButtonClicked,
        onBottomSheetDismissed = tasksScreenViewModel::onBottomSheetDismissed,
        onCancelButtonClicked = tasksScreenViewModel::onCancelButtonClicked,
        onDateCardClicked = tasksScreenViewModel::onDateCardClicked,
        onDismissDatePicker = tasksScreenViewModel::onDismissDatePicker,
        onConfirmDatePicker = tasksScreenViewModel::onConfirmDatePicker,


        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenContent(
    taskScreenUiState: TasksScreenUiState,
    onDayCardClicked: (Int) -> Unit,
    onTabSelected: (Int) -> Unit,
    onFloatingActionClicked: () -> Unit,
    onTaskCardClicked: (Long) -> Unit,
    onDeleteIconClicked: (Long?) -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onBottomSheetDismissed: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onDateCardClicked: () -> Unit,
    onConfirmDatePicker: (Long?) -> Unit,
    onDismissDatePicker: () -> Unit,


    ) {

    Box(
        Modifier
            .fillMaxSize()
            .background(TudeeTheme.color.surfaceHigh),
    ) {

        Box(
            Modifier.padding(horizontal = 16.dp)
        ) {
            if (taskScreenUiState.isSnackBarVisible) {
                SnackBarComponent(
                    message = stringResource(R.string.task_deleted_success),
                    iconPainter = painterResource(R.drawable.check_mark_ic),
                    iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant
                )
            }
        }

        DatePickerScreen(
            uiState = taskScreenUiState.datePickerUiState,
            onDismiss = onDismissDatePicker,
            onConfirm = onConfirmDatePicker
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TopAppBar(
                modifier = Modifier,
                title = "Tasks",
                showBackButton = false,
            )

            HeadingDate(onDateCardClicked)

            DaysRow(
                listOfDateCardUiState = taskScreenUiState.listOfDateCardUiState,
                onDayCardClicked = onDayCardClicked
            )

            TabBarComponent(
                selectedTabIndex = taskScreenUiState.selectedTabIndex,
                tabBarItems = taskScreenUiState.listOfTabBarItem,
                onTabSelected = onTabSelected
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surface)
                    .weight(1f)
            ) {
                TasksListContent(

                    listOfTasks = taskScreenUiState.listOfTasksUiState,
                    onTaskCardClicked = onTaskCardClicked,
                    onDeleteIconClick = onDeleteIconClicked,
                )
            }

            BottomSheet(
                isBottomSheetVisible = taskScreenUiState.isBottomSheetVisible,
                title = taskScreenUiState.deleteBottomSheetUiState.title,
                subtitle = taskScreenUiState.deleteBottomSheetUiState.subtitle,
                onBottomSheetDismissed = onBottomSheetDismissed,
                onDeleteButtonClicked = onDeleteButtonClicked,
                onCancelButtonClicked = onCancelButtonClicked,
                deleteButtonUiState = taskScreenUiState.deleteBottomSheetUiState.deleteButtonState,
                cancelButtonUiState = taskScreenUiState.deleteBottomSheetUiState.cancelButtonState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(
    uiState: DatePickerUiState,
    onDismiss: () -> Unit,
    onConfirm: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    if (uiState.isVisible) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(datePickerState.selectedDateMillis)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            },

            )
        {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun DaysRow(
    onDayCardClicked: (Int) -> Unit,
    listOfDateCardUiState: List<DateCardUiState>
) {
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listOfDateCardUiState) { index, dateCard ->
            val dayOfMonthTextColor = if (dateCard.isSelected) TudeeTheme.color.textColors.onPrimary
            else TudeeTheme.color.textColors.body

            val dayOfWeekTextColor =
                if (dateCard.isSelected) TudeeTheme.color.textColors.onPrimaryCaption
                else TudeeTheme.color.textColors.hint

            val modifier = if (dateCard.isSelected) {
                Modifier
                    .size(width = 56.dp, height = 65.dp)
                    .background(
                        brush = (Brush.linearGradient(
                            colors = TudeeTheme.color.primaryGradient,
                            start = Offset(0f, 0f),
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        )),

                        shape = RoundedCornerShape(16.dp)
                    )
            } else {
                Modifier
                    .size(width = 56.dp, height = 65.dp)
                    .background(
                        TudeeTheme.color.surface, shape = RoundedCornerShape(16.dp)
                    )
            }
            TudeeDayCard(
                modifier = modifier,
                dayOfMonth = dateCard.dayNumber,
                dayOfWeek = dateCard.dayName,
                dayOfMonthTextColor = dayOfMonthTextColor,
                dayOfWeekTextColor = dayOfWeekTextColor,
                onClick = { onDayCardClicked(index) })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TasksListContent(
    modifier: Modifier = Modifier,
    listOfTasks: List<TaskUiState>,
    onTaskCardClicked: (Long) -> Unit,
    onDeleteIconClick: (Long?) -> Unit
) {
    LazyColumn(
        modifier.padding(
            top = 12.dp, start = 16.dp, end = 16.dp
        ), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            listOfTasks,
            key = { it.id }
        )
        { task ->
            SwipeableCardWrapper(onDeleteIconClick = { onDeleteIconClick(task.id) }) {
                var priorityBackgroundColor = Color.Transparent
                var priorityIcon = painterResource(R.drawable.ic_priority_medium)
                when (task.priority) {
                    "HIGH" -> {
                        priorityBackgroundColor = TudeeTheme.color.statusColors.pinkAccent
                        priorityIcon = painterResource(R.drawable.ic_priority_high)
                    }

                    "MEDIUM" -> {
                        priorityBackgroundColor = TudeeTheme.color.statusColors.yellowAccent
                        priorityIcon = painterResource(R.drawable.ic_priority_medium)
                    }

                    "LOW" -> {
                        priorityBackgroundColor = TudeeTheme.color.statusColors.greenAccent
                        priorityIcon = painterResource(R.drawable.ic_priority_low)
                    }
                }
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                CategoryTaskComponent(
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(5000),
                        fadeOutSpec = tween(5000),
                        placementSpec = tween(5000)

                    ),
                    title = task.title,
                    description = task.description,
                    priority = task.priority,
                    priorityBackgroundColor = priorityBackgroundColor,
                    taskIcon = { },
                    onClick = { },
                    priorityIcon = priorityIcon,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TasksScreenContentPreview() {
    TudeeTheme
    TasksScreenContent(
        taskScreenUiState = TasksScreenUiState(),
        onDayCardClicked = {},
        onTabSelected = {},
        onFloatingActionClicked = {},
        onTaskCardClicked = {},
        onDeleteIconClicked = { },
        onDeleteButtonClicked = {},
        onBottomSheetDismissed = {},
        onCancelButtonClicked = {},
        onDateCardClicked = {},
        onConfirmDatePicker = {},
        onDismissDatePicker = { }
    )
}

val tesentreies = listOf(
    TabBarItem(
        title = "In progress", taskCount = "0", isSelected = true
    ),
    TabBarItem(
        title = "To do", taskCount = "0", isSelected = false
    ),
    TabBarItem(
        title = "Done", taskCount = "0", isSelected = false
    ),
)

@Composable
fun SwipeableCardWrapper(
    onDeleteIconClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    var hiddenIconWidth by remember { mutableFloatStateOf(0f) }
    var neededOffset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()


    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TudeeTheme.color.statusColors.errorVariant),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(
            modifier = Modifier
                .onSizeChanged {
                    hiddenIconWidth = it.width.toFloat()
                }
                .padding(horizontal = 12.dp), onClick = {
                onDeleteIconClick()
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(R.drawable.delete_ic),
                tint = TudeeTheme.color.statusColors.error,
                contentDescription = "delete icon"
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(x = -neededOffset.value.roundToInt(), y = 0) }
                .clip(RoundedCornerShape(12.dp))
                .pointerInput(hiddenIconWidth) {
                    detectHorizontalDragGestures(onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val newOffset = (neededOffset.value - dragAmount).coerceIn(
                                0f, hiddenIconWidth
                            )
                            neededOffset.snapTo(newOffset)
                        }
                    }, onDragEnd = {
                        scope.launch {
                            if (neededOffset.value >= hiddenIconWidth / 2) {
                                neededOffset.animateTo(hiddenIconWidth)
                            } else {
                                neededOffset.animateTo(0f)
                            }
                        }
                    })
                }) {
            content()
        }
    }
}

@Composable
fun HeadingDate(onDateCardClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(
                        1.dp, TudeeTheme.color.stroke, shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = stringResource(R.string.back_button),
                    tint = TudeeTheme.color.textColors.body
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(
                        1.dp, TudeeTheme.color.stroke, RoundedCornerShape(100.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = stringResource(R.string.back_button),
                    tint = TudeeTheme.color.textColors.body
                )
            }
        }

        Row(
            Modifier
                .clickable { onDateCardClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Jun, 2025",
                style = TudeeTheme.textStyle.label.medium,
                color = TudeeTheme.color.textColors.body
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.arrow_down),
                tint = TudeeTheme.color.textColors.body,
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    isBottomSheetVisible: Boolean,
    title: String,
    subtitle: String,
    deleteButtonUiState: ButtonState,
    cancelButtonUiState: ButtonState,
    onBottomSheetDismissed: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,

    ) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier, containerColor = TudeeTheme.color.surface,
            onDismissRequest = {
                onBottomSheetDismissed()

            }, sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surface)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = title,
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title,

                    )
                Text(
                    text = subtitle,
                    style = TudeeTheme.textStyle.body.large,
                    color = TudeeTheme.color.textColors.body,
                )
                Image(
                    painter = painterResource(R.drawable.delete_task_bot),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 107.dp, height = 100.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surfaceHigh)
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NegativeButton(
                    modifier = Modifier
                        .fillMaxWidth(), onClick = {

                        onDeleteButtonClicked()

                    }, state = deleteButtonUiState
                ) {
                    Text("Delete")
                }
                SecondaryButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    onCancelButtonClicked()
                }) {
                    Text("Cancel")
                }
            }
        }
    }
}


