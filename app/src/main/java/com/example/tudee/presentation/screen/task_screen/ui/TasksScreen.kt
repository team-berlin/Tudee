package com.example.tudee.presentation.screen.task_screen.ui


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.components.BottomNavItem
import com.example.tudee.presentation.components.CategoryTaskComponent
import com.example.tudee.presentation.components.NavBar
import com.example.tudee.presentation.components.SnackBarComponent
import com.example.tudee.presentation.components.TabBarComponent
import com.example.tudee.presentation.components.TopAppBar
import com.example.tudee.presentation.components.TudeeDateDialog
import com.example.tudee.presentation.components.TudeeDayCard
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.composables.buttons.FabButton
import com.example.tudee.presentation.composables.buttons.NegativeButton
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.presentation.screen.TaskDetailsScreen
import com.example.tudee.presentation.screen.task_screen.mappers.TaskPriorityUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.DateCardUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.DateUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState
import com.example.tudee.presentation.screen.task_screen.ui_states.TasksScreenUiState
import com.example.tudee.presentation.screen.task_screen.viewmodel.TasksScreenViewModel
import com.example.tudee.presentation.screen.taskscreen.addTask.AddBottomSheet
import com.example.tudee.presentation.screen.taskscreen.editTask.EditeBottomSheet
import com.example.tudee.presentation.viewmodel.AddTaskBottomSheetViewModel
import com.example.tudee.presentation.viewmodel.uistate.TaskBottomSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun TasksScreen(navController: NavController) {
    val tasksScreenViewModel: TasksScreenViewModel = koinViewModel()
    val taskScreenUiState by tasksScreenViewModel.taskScreenUiState.collectAsState()

    val addTaskBottomSheetViewModel: AddTaskBottomSheetViewModel = koinViewModel()
    val addTaskBottomSheetUiState by addTaskBottomSheetViewModel.uiState.collectAsState()

    TasksScreenContent(
        addTaskBottomSheetUiState = addTaskBottomSheetUiState,
        showAddTaskBottomSheet = addTaskBottomSheetViewModel::showButtonSheet,
        taskScreenUiState = taskScreenUiState,
        onTabSelected = tasksScreenViewModel::onTabSelected,
        onTaskCardClicked = tasksScreenViewModel::onTaskCardClicked,
        onDayCardClicked = tasksScreenViewModel::onDayCardClicked,
        onCalendarClicked = tasksScreenViewModel::onCalendarClicked,
        onPreviousArrowClicked = tasksScreenViewModel::onPreviousArrowClicked,
        onNextArrowClicked = tasksScreenViewModel::onNextArrowClicked,
        onDeleteIconClicked = tasksScreenViewModel::onDeleteIconClicked,
        onDeleteButtonClicked = tasksScreenViewModel::onConfirmDelete,
        onBottomSheetDismissed = tasksScreenViewModel::onBottomSheetDismissed,
        onCancelButtonClicked = tasksScreenViewModel::onCancelButtonClicked,
        onDismissDatePicker = tasksScreenViewModel::onDismissDatePicker,
        onConfirmDatePicker = tasksScreenViewModel::onConfirmDatePicker,
        hideSnackBar = tasksScreenViewModel::hideSnackBar,
        version =  tasksScreenViewModel.triggerEffectVersion.collectAsState().value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenContent(
    taskScreenUiState: TasksScreenUiState,
    onDayCardClicked: (Int) -> Unit,
    onCalendarClicked: () -> Unit,
    onPreviousArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onTaskCardClicked: (TaskUiState) -> Unit,
    onDeleteIconClicked: (Long) -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onBottomSheetDismissed: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onConfirmDatePicker: (Long?) -> Unit,
    onDismissDatePicker: () -> Unit,
    hideSnackBar: () -> Unit,
    addTaskBottomSheetUiState: TaskBottomSheetState,
    showAddTaskBottomSheet: () -> Unit,
    version: Int,

    ) {

    TudeeScaffold(
        showTopAppBar = true,
        topAppBar = { TaskScreenTopAppBar() },
        showBottomBar = true,
        bottomBarContent = { TaskScreenBottomAppBar() },
        showFab = true,
        floatingActionButton = {
            TaskScreenFloatingActionButton {
                showAddTaskBottomSheet()
            }
        })
    { paddingValues ->

        Box(
            Modifier
                .fillMaxSize()
                .background(TudeeTheme.color.surfaceHigh)
                .padding(paddingValues)
        ) {
            if (addTaskBottomSheetUiState.isButtonSheetVisible) {
                AddBottomSheet()

            }
            if (addTaskBottomSheetUiState.isButtonSheetVisible) {
                EditeBottomSheet()
            }
        }
        val showBottomSheet = false

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {

                },
                sheetState = rememberModalBottomSheetState(),
                containerColor = TudeeTheme.color.surface
            ) {
                TaskDetailsScreen(
                    taskDetailsState =
                        taskScreenUiState.taskDetailsUiState,
                    onEditButtonClicked = {}
                )
            }
        }


        if (taskScreenUiState.dateUiState.isDatePickerVisible) {
            TudeeDateDialog(
                onDismiss = onDismissDatePicker,
                onConfirm = onConfirmDatePicker,
                onClear = {})
        }
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DateSection(
                datePickerUiState = taskScreenUiState.dateUiState,
                listOfDateCardUiState = taskScreenUiState.dateUiState.daysCardsData,
                onCalendarClicked = onCalendarClicked,
                onPreviousArrowClicked = onPreviousArrowClicked,
                onNextArrowClicked = onNextArrowClicked,
                onDayCardClicked = onDayCardClicked,
                version = version
            )

            TabBarComponent(
                selectedTabIndex = taskScreenUiState.selectedTabIndex,
                tabBarItems = taskScreenUiState.listOfTabBarItem,
                onTabSelected = onTabSelected
            )

            TasksListContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surface)
                    .weight(1f),
                listOfTasks = taskScreenUiState.listOfTasksUiState,
                onTaskCardClicked =
                    { onTaskCardClicked },
                onDeleteIconClick = onDeleteIconClicked,
            )

            DeleteConfirmationBottomSheet(
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

        SnackBarSection(
            isSnackBarVisible = taskScreenUiState.isSnackBarVisible, hideSnackBar = hideSnackBar
        )

    }
}


@Composable
private fun DateSection(
    listOfDateCardUiState: List<DateCardUiState>,
    datePickerUiState: DateUiState,
    onCalendarClicked: () -> Unit,
    onDayCardClicked: (Int) -> Unit,
    onPreviousArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
    version: Int,
) {
    DataHeader(
        selectedMonth = datePickerUiState.selectedMonth.month.getDisplayName(
            TextStyle.SHORT,
            Locale.getDefault()
        ),
        selectedYear = datePickerUiState.selectedYear,
        onCalendarClicked = onCalendarClicked,
        onPreviousArrowClicked = onPreviousArrowClicked,
        onNextArrowClicked = onNextArrowClicked
    )
    DaysRow(
        listOfDateCardUiState = listOfDateCardUiState,
        onDayCardClicked = onDayCardClicked,
        version = version
    )
}

@Composable
fun DataHeader(
    selectedMonth: String,
    selectedYear: String,
    onCalendarClicked: () -> Unit,
    onPreviousArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ArrowButton(
            icon = painterResource(R.drawable.arrow_left),
            contentDescription = stringResource(R.string.previous_week_arrow_content_description),
            onClick = onPreviousArrowClicked
        )
        Row(
            modifier = Modifier.clickable { onCalendarClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$selectedMonth, $selectedYear",
                style = TudeeTheme.textStyle.label.medium,
                color = TudeeTheme.color.textColors.body
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.arrow_down),
                tint = TudeeTheme.color.textColors.body,
                contentDescription = stringResource(R.string.open_calendar_content_description),
            )
        }

        ArrowButton(
            icon = painterResource(R.drawable.arrow_right),
            contentDescription = stringResource(R.string.next_week_arrow_content_description),
            onClick = onNextArrowClicked
        )
    }
}

@Composable
private fun ArrowButton(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .border(1.dp, TudeeTheme.color.stroke, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = TudeeTheme.color.textColors.body
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksListContent(
    modifier: Modifier = Modifier,
    listOfTasks: List<TaskUiState>,
    onTaskCardClicked: (TaskUiState) -> Unit,
    onDeleteIconClick: (Long) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (listOfTasks.isEmpty()) {
            Box(
                Modifier.align(Alignment.Center)
            ) {
                NotTaskForTodayDialogue()
            }
        }
        AnimatedContent(
            targetState = listOfTasks,
            transitionSpec = {
                fadeIn(tween(1000)) togetherWith fadeOut(tween(1000))
            },
        ) { listOfTasks ->
            LazyColumn(
                modifier.padding(
                    start = 16.dp, end = 16.dp
                ), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(listOfTasks, key = { it.id }) { task ->
                    SwipeableCardWrapper(
                        onDeleteIconClick =
                            { onDeleteIconClick(task.id) },
                        onTaskCardClicked = { onTaskCardClicked(task) }
                    ) {

                        var priorityBackgroundColor = Color.Transparent
                        var priorityIcon = painterResource(R.drawable.ic_priority_medium)
                        when (task.priority) {
                            TaskPriorityUiState.HIGH.priority -> {
                                priorityBackgroundColor = TudeeTheme.color.statusColors.pinkAccent
                                priorityIcon = painterResource(R.drawable.ic_priority_high)
                            }

                            TaskPriorityUiState.MEDIUM.priority -> {
                                priorityBackgroundColor = TudeeTheme.color.statusColors.yellowAccent
                                priorityIcon = painterResource(R.drawable.ic_priority_medium)
                            }

                            TaskPriorityUiState.LOW.priority -> {
                                priorityBackgroundColor = TudeeTheme.color.statusColors.greenAccent
                                priorityIcon = painterResource(R.drawable.ic_priority_low)
                            }
                        }
                        Modifier.clip(RoundedCornerShape(16.dp))
                        CategoryTaskComponent(
                            title = task.title,
                            description = task.description,
                            priority = stringResource(task.priority),
                            priorityBackgroundColor = priorityBackgroundColor,
                            taskIcon = { task.categoryIcon },
                            onClick = { onTaskCardClicked(task) },
                            priorityIcon = priorityIcon,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SnackBarSection(
    isSnackBarVisible: Boolean, hideSnackBar: () -> Unit
) {
    LaunchedEffect(isSnackBarVisible) {
        delay(2000)
        hideSnackBar()
    }

    AnimatedVisibility(
        visible = isSnackBarVisible, enter = slideInVertically(
            initialOffsetY = { fullHeight -> -fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy
            )
        ) + fadeIn(),

        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight }, animationSpec = spring(
                stiffness = Spring.StiffnessMedium, dampingRatio = Spring.DampingRatioNoBouncy
            )
        ) + fadeOut()
    ) {
        Box(
            Modifier.padding(horizontal = 16.dp)
        ) {
            SnackBarComponent(
                message = stringResource(R.string.task_deleted_success),
                iconPainter = painterResource(R.drawable.check_mark_ic),
                iconTint = TudeeTheme.color.statusColors.greenAccent,
                iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant
            )
        }
    }
}

@Composable
fun DaysRow(
    onDayCardClicked: (Int) -> Unit,
    listOfDateCardUiState: List<DateCardUiState>,
    version: Int
) {

    val listState = rememberLazyListState()

    val selectedIndex = listOfDateCardUiState.indexOfFirst { it.isSelected }.coerceAtLeast(0)

    LaunchedEffect(version) {
        if (selectedIndex > 0)
            listState.scrollToItem(selectedIndex-1)
        else
            listState.scrollToItem(selectedIndex)
    }

    LazyRow(
        state = listState,
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
                dayOfMonth = dateCard.dayNumber.toString(),
                dayOfWeek = dateCard.dayName,
                dayOfMonthTextColor = dayOfMonthTextColor,
                dayOfWeekTextColor = dayOfWeekTextColor,
                onClick = { onDayCardClicked(index) })
        }
    }

}


@Composable
fun SwipeableCardWrapper(
    onDeleteIconClick: () -> Unit,
    onTaskCardClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    var hiddenIconWidth by remember { mutableFloatStateOf(0f) }
    var neededOffset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()


    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TudeeTheme.color.statusColors.errorVariant)
            .clickable { onTaskCardClicked() },
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(
            modifier = Modifier
                .onSizeChanged {
                    hiddenIconWidth = it.width.toFloat()
                }
                .padding(horizontal = 12.dp), onClick = {
                onDeleteIconClick()
            }) {
            Icon(
                modifier = Modifier.size(32.dp),
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
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
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
            Modifier.clickable { onDateCardClicked() },
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
fun DeleteConfirmationBottomSheet(
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
            modifier = Modifier, containerColor = TudeeTheme.color.surface, onDismissRequest = {
                onBottomSheetDismissed()

            }, sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surface)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    modifier = Modifier.fillMaxWidth(), onClick = {

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

@Composable
private fun TaskScreenTopAppBar() {
    TopAppBar(
        modifier = Modifier.background(TudeeTheme.color.surfaceHigh),
        title = "Tasks",
        showBackButton = false
    )
}

@Composable

private fun TaskScreenBottomAppBar() {
    NavBar(
        navDestinations = listOf(
            BottomNavItem(
                icon = painterResource(id = R.drawable.home),
                selectedIcon = painterResource(id = R.drawable.home_select),
                route = Destination.HomeScreen.route
            ), BottomNavItem(
                icon = painterResource(id = R.drawable.task),
                selectedIcon = painterResource(id = R.drawable.task_select),
                route = Destination.TasksScreen.route
            ), BottomNavItem(
                icon = painterResource(id = R.drawable.category),
                selectedIcon = painterResource(id = R.drawable.category_select),
                route = Destination.CategoriesScreen.route
            )
        ), currentRoute = Destination.TasksScreen.route, onNavDestinationClicked = {})
}


@Composable
private fun TaskScreenFloatingActionButton(onFloatingActionClicked: () -> Unit) {
    FabButton(onClick = {
        onFloatingActionClicked()
    }, content = {
        Icon(
            painter = painterResource(R.drawable.note_add), contentDescription = null
        )
    })
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TasksScreenContentPreview() {
    TudeeTheme
    TasksScreenContent(
        taskScreenUiState = TasksScreenUiState(),
        onDayCardClicked = {},
        onCalendarClicked = {},
        onPreviousArrowClicked = {},
        onNextArrowClicked = {},
        onTabSelected = {},
        onTaskCardClicked = {},
        onDeleteIconClicked = { },
        onDeleteButtonClicked = {},
        onBottomSheetDismissed = {},
        onCancelButtonClicked = {},
        onConfirmDatePicker = {},
        onDismissDatePicker = { },
        hideSnackBar = {},
        addTaskBottomSheetUiState = TaskBottomSheetState(),
        showAddTaskBottomSheet = {},
        version = 0
    )
}


