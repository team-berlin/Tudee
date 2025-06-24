package com.example.tudee.presentation.screen.category.tasks

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.tudee.presentation.components.SnackBarComponent
import com.example.tudee.presentation.components.TabBarComponent
import com.example.tudee.presentation.components.TopAppBar
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.screen.category.EditCategorySheet
import com.example.tudee.presentation.screen.category.model.CategoryData
import com.example.tudee.presentation.screen.category.model.toUiImage
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: CategoryTasksViewModel = koinViewModel()
) {
    val uiState by viewModel.categoryTasksUiState.collectAsState()
    val showSnackBar = remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var isEditCategorySheetVisible by remember { mutableStateOf(false) }

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
    TudeeScaffold(
        showTopAppBar = true,
        topAppBar = {
            TopAppBar(
                onBackButtonClicked = { navController.navigateUp() },
                showBackButton = true,
                title = uiState.categoryTasksUiModel?.title,
                trailingComposable = {
                    IconButton(onClick = {
                        isEditCategorySheetVisible = true
                    }) {
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
        }
    ) { paddingValues ->
        if (uiState.categoryTasksUiModel != null) {
            CategoryTasksContent(
                modifier = modifier.padding(paddingValues),
                categoryTaskUIState = uiState,
                isEditCategorySheetVisible = isEditCategorySheetVisible,
                onDeleteCategory = viewModel::deleteCategory,
                onSaveButtonClicked = { categoryData ->
                    viewModel.editCategory(categoryData)
                },
                onTabSelected = { index ->
                    selectedTabIndex = index
                    viewModel.updateSelectedIndex(index)
                },
                onBottomSheetDismissed = { isEditCategorySheetVisible = false },
                onCancelButtonClicked = { isEditCategorySheetVisible = false },
            )
        } else if (uiState.loading) {
            LoadingState(modifier = modifier)
        } else {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No tasks available")
            }
            if (showSnackBar.value) {
                SnackBarComponent(
                    message = stringResource(R.string.snack_bar_error_message),
                    iconPainter = painterResource(id = R.drawable.ic_error),
                    iconDescription = stringResource(R.string.snack_bar_error_message),
                    iconBackgroundColor = TudeeTheme.color.statusColors.errorVariant,
                    iconTint = TudeeTheme.color.statusColors.greenAccent,
                )
            }
        }
    }
}

@Composable
fun CategoryTasksContent(
    modifier: Modifier = Modifier,
    categoryTaskUIState: CategoryTasksUiState,
    isEditCategorySheetVisible: Boolean,
    onDeleteCategory: () -> Unit,
    onSaveButtonClicked: (CategoryData) -> Unit,
    onTabSelected: (Int) -> Unit,
    onBottomSheetDismissed: () -> Unit,
    onCancelButtonClicked: () -> Unit

) {
    if (categoryTaskUIState.categoryTasksUiModel != null) {
        SuccessState(
            modifier = modifier,
            categoryTaskUIState = categoryTaskUIState,
            categoryName = categoryTaskUIState.categoryTasksUiModel.title,
            categoryTasks = categoryTaskUIState.categoryTasksUiModel.tasks,
            categoryImage = categoryTaskUIState.categoryTasksUiModel.image,
            onDeleteCategory = onDeleteCategory,
            onSaveButtonClicked = onSaveButtonClicked,
            onTabSelected = { onTabSelected(it) },
            isEditCategorySheetVisible = isEditCategorySheetVisible,
            onBottomSheetDismissed = { onBottomSheetDismissed() },
            onCancelButtonClicked = { onCancelButtonClicked() }
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
    categoryTaskUIState: CategoryTasksUiState,
    categoryName: String,
    categoryImage: String,
    categoryTasks: List<TaskUIModel>,
    isEditCategorySheetVisible: Boolean,
    onDeleteCategory: () -> Unit,
    onSaveButtonClicked: (CategoryData) -> Unit,
    onTabSelected: (Int) -> Unit,
    onBottomSheetDismissed: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {

    Column(
        modifier = modifier
    ) {
        categoryTaskUIState.categoryTasksUiModel?.let {
            TabBarComponent(
                selectedTabIndex = it.selectedTabIndex,
                tabBarItems = it.listOfTabBarItem,
                onTabSelected = { index ->
                    onTabSelected(index)
                }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(categoryTasks) { categoryTask ->

                CategoryTaskComponent(
                    title = categoryTask.title,
                    description = categoryTask.description,
                    priority = stringResource(categoryTask.priority.labelRes),
                    priorityBackgroundColor = categoryTask.priority.getContainerColor(),
                    dateText = categoryTask.assignedDate,
                    taskIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_category_book_open), // this will be change
                            contentDescription = "Task Icon",
                            modifier = Modifier.size(32.dp),
                            tint = TudeeTheme.color.statusColors.purpleAccent
                        )
                    },
                    priorityIcon = painterResource(categoryTask.priority.drawableRes),
                    onClick = {

                    }
                )
            }
        }
        EditCategorySheet(
            isBottomSheetVisible = isEditCategorySheetVisible,
            onDeleteButtonClicked = onDeleteCategory,
            onBottomSheetDismissed = { onBottomSheetDismissed() },
            onCancelButtonClicked = { onCancelButtonClicked() },
            onSaveButtonClicked = { onSaveButtonClicked(it) },
            initialCategoryImage = categoryImage.toUiImage(),
            initialCategoryName = categoryName
        )
    }
}