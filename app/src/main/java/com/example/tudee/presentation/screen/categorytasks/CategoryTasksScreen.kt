package com.example.tudee.presentation.screen.categorytasks

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.CategoryTaskComponent
import com.example.tudee.presentation.components.DefaultTabContent
import com.example.tudee.presentation.components.SnackBarComponent
import com.example.tudee.presentation.components.TabBarComponent
import com.example.tudee.presentation.components.TabBarItem
import com.example.tudee.presentation.components.TopAppBar
import com.example.tudee.presentation.components.TudeeScaffold
import kotlinx.coroutines.delay
import org.koin.compose.getKoin

@Composable
fun CategoryTasksScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: CategoryTasksViewModel = getKoin().get()
) {
    val uiState by viewModel.categoryTasksUiState.collectAsState()
    val showSnackBar = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.snackBarEvent.collect { event ->
            when (event) {
                is SnackBarEvent.ShowError -> {
                    showSnackBar.value = true
                    delay(3000L)
                    showSnackBar.value = false
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        CategoryTasksContent(
            modifier = modifier,
            categoryTaskUIState = uiState,
            onBackClick = { navController.navigateUp() },
            onEditIconClick = { viewModel.showError() }
        )

        if (showSnackBar.value) {
            SnackBarComponent(
                modifier = Modifier.statusBarsPadding(),
                message = stringResource(R.string.snack_bar_error_message),
                iconPainter = painterResource(id = R.drawable.ic_error),
                iconDescription = stringResource(R.string.snack_bar_error_message),
                iconBackgroundColor = TudeeTheme.color.statusColors.errorVariant,
                iconTint = TudeeTheme.color.statusColors.greenAccent,
            )
        }
    }
}

@Composable
fun CategoryTasksContent(
    modifier: Modifier = Modifier,
    categoryTaskUIState: CategoryTasksUiState,
    onBackClick: () -> Unit,
    onEditIconClick: () -> Unit
) {

    when (categoryTaskUIState) {
        is CategoryTasksUiState.Loading -> {
            LoadingState(Modifier)
        }

        is CategoryTasksUiState.Success -> SuccessState(
            modifier = modifier,
            categoryName = categoryTaskUIState.categoryTasksUiModel.title,
            categoryTasks = categoryTaskUIState.categoryTasksUiModel.tasks,
            onEditIconClick = { onEditIconClick() },
            onBackClick = { onBackClick() }
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessState(
    modifier: Modifier,
    categoryName: String,
    categoryTasks: List<TaskUIModel>,
    onEditIconClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val defaultTabBarHeaders = listOf(
        TabBarItem(
            title = stringResource(R.string.in_progress),
            taskCount = "0",
            isSelected = true
        ),
        TabBarItem(
            title = stringResource(R.string.to_do),
            taskCount = "0",
            isSelected = false
        ),
        TabBarItem(
            title = stringResource(R.string.done),
            taskCount = "0",
            isSelected = false
        ),
    )
    TudeeScaffold(
        modifier = modifier.fillMaxSize(),
        showTopAppBar = true,
        topAppBar = {
            Column {
                TopAppBar(
                    onBackButtonClicked = { onBackClick() },
                    showBackButton = true,
                    title = categoryName,
                    trailingComposable = {
                        IconButton(onClick = onEditIconClick) {
                            Icon(
                                modifier = Modifier
                                    .border(
                                        1.dp, TudeeTheme.color.stroke, RoundedCornerShape(100.dp)
                                    )
                                    .padding(10.dp)
                                    .size(20.dp),
                                painter = painterResource(R.drawable.pencil_edit),
                                contentDescription = null,
                                tint = TudeeTheme.color.textColors.body
                            )
                        }
                    }
                )
                TabBarComponent(
                    modifier = Modifier,
                    selectedTabIndex = 0,
                    tabBarItems = defaultTabBarHeaders,
                    onTabSelected = {},
                    tabContent = { DefaultTabContent(tabBarItem = it) }
                )
            }

        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(categoryTasks) { categoryTask ->
                    val taskPriorityBackgroundColor = when (categoryTask.priority.tasPriorityType) {
                        TaskPriorityType.HIGH -> TudeeTheme.color.statusColors.pinkAccent
                        TaskPriorityType.MEDIUM -> TudeeTheme.color.statusColors.yellowAccent
                        TaskPriorityType.LOW -> TudeeTheme.color.statusColors.greenAccent
                    }
                    CategoryTaskComponent(
                        title = categoryTask.title,
                        description = categoryTask.description,
                        priority = stringResource(categoryTask.priority.priorityTextId),
                        priorityBackgroundColor = taskPriorityBackgroundColor,
                        dateText = categoryTask.assignedDate,
                        taskIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_category_book_open), // this will be change
                                contentDescription = "Task Icon",
                                modifier = Modifier.size(32.dp),
                                tint = TudeeTheme.color.statusColors.purpleAccent
                            )
                        },
                        priorityIcon = painterResource(id = R.drawable.ic_priority_medium),
                        onClick = {
                            // navigate to task details screen
                        }
                    )
                }
            }
        }
    }
}