package com.example.tudee.presentation.screen.category.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.screen.category.component.CategorySheet
import com.example.tudee.presentation.screen.category.model.CategoryData
import com.example.tudee.presentation.screen.category.model.CategorySheetState
import com.example.tudee.presentation.screen.category.model.UiImage
import com.example.tudee.presentation.screen.task_screen.component.DeleteConfirmationBottomSheet
import com.example.tudee.presentation.screen.task_screen.ui.NotTaskForTodayDialogue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: CategoryTasksViewModel = koinViewModel()
) {
    val uiState by viewModel.categoryTasksUiState.collectAsState()
    var showSnackBar by remember { mutableStateOf(false) }
    var snackBarMessageId by remember { mutableIntStateOf(0) }
    var snackBarIconId by remember { mutableIntStateOf(0) }
    val allTasks by viewModel.allTasks.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.snackBarEvent.collect { event ->
            showSnackBar = when (event) {
                CategoryTasksSnackBarEvent.ShowDeleteError -> {
                    snackBarMessageId = R.string.failed_to_delete_category
                    snackBarIconId = R.drawable.ic_error
                    true
                }

                CategoryTasksSnackBarEvent.ShowDeleteSuccess -> {
                    snackBarMessageId = R.string.delete_category_successfully
                    snackBarIconId = R.drawable.ic_success
                    true
                }

                CategoryTasksSnackBarEvent.ShowEditError -> {
                    snackBarMessageId = R.string.failed_to_edit_category
                    snackBarIconId = R.drawable.ic_error
                    true
                }

                CategoryTasksSnackBarEvent.ShowEditSuccess -> {
                    snackBarMessageId = R.string.edited_category_successfully
                    snackBarIconId = R.drawable.ic_success
                    true
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
                    uiState.categoryTasksUiModel?.isPredefined?.let {
                        if (!(it)) {
                            IconButton(
                                onClick = viewModel::showEditCategorySheet

                            ) {
                                Icon(
                                    modifier = Modifier
                                        .border(
                                            1.dp,
                                            TudeeTheme.color.stroke,
                                            RoundedCornerShape(100.dp)
                                        )
                                        .padding(10.dp)
                                        .size(20.dp),
                                    painter = painterResource(R.drawable.pencil_edit),
                                    contentDescription = null,
                                    tint = TudeeTheme.color.textColors.body
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (uiState.categoryTasksUiModel != null) {
            CategoryTasksContent(
                modifier = modifier.padding(paddingValues),
                categoryTaskUIState = uiState,
                isEditCategorySheetVisible = uiState.isEditCategorySheetVisible,
                isDeleteCategorySheetVisible = uiState.isDeleteCategorySheetVisible,
                allTasks = allTasks,
                onDeleteCategory = viewModel::deleteCategory,
                onSaveButtonClicked = { categoryData ->
                    viewModel.editCategory(categoryData)
                },
                onTabSelected = { index ->
                    viewModel.updateSelectedIndex(index)
                },
                onBottomSheetDismissed = viewModel::hideEditCategorySheet,
                onCancelButtonClicked = viewModel::hideEditCategorySheet,
                showDeleteCategorySheet = viewModel::showDeleteCategorySheet,
                hideDeleteCategorySheet = viewModel::hideDeleteCategorySheet
            )
        } else if (uiState.loading) {
            LoadingState(modifier = modifier)
        }
    }
    CategoryTasksSnackBar(
        isSnackBarVisible = showSnackBar,
        hideSnackBar = {
            showSnackBar = false
            coroutineScope.launch(Dispatchers.Main) {
                delay(3000)
                navController.navigateUp()
            }
        },
        snackBarStringId = snackBarMessageId,
        snackBarIconId = snackBarIconId,

        )
}


@Composable
fun CategoryTasksContent(
    modifier: Modifier = Modifier,
    categoryTaskUIState: CategoryTasksUiState,
    isEditCategorySheetVisible: Boolean,
    isDeleteCategorySheetVisible: Boolean,
    allTasks: List<TaskUIModel>,
    showDeleteCategorySheet: () -> Unit,
    hideDeleteCategorySheet: () -> Unit,
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
            allTasks = allTasks,
            onTabSelected = { onTabSelected(it) },
            isEditCategorySheetVisible = isEditCategorySheetVisible,
            isDeleteCategorySheetVisible = isDeleteCategorySheetVisible,
            onBottomSheetDismissed = onBottomSheetDismissed,
            onCancelButtonClicked = onCancelButtonClicked,
            showDeleteCategorySheet = showDeleteCategorySheet,
            hideDeleteCategorySheet = hideDeleteCategorySheet
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
    categoryImage: UiImage,
    categoryTasks: List<TaskUIModel>,
    isEditCategorySheetVisible: Boolean,
    allTasks: List<TaskUIModel>,
    isDeleteCategorySheetVisible: Boolean,
    showDeleteCategorySheet: () -> Unit,
    hideDeleteCategorySheet: () -> Unit,
    onDeleteCategory: () -> Unit,
    onSaveButtonClicked: (CategoryData) -> Unit,
    onTabSelected: (Int) -> Unit,
    onBottomSheetDismissed: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
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
        if (allTasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                NotTaskForTodayDialogue(
                    title = stringResource(
                        R.string.no_tasks_in_category,
                        categoryName
                    ),
                    description = null
                )
            }
        } else if (categoryTasks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                NotTaskForTodayDialogue(
                    title = stringResource(
                        R.string.no_tasks,
                        categoryName
                    ),
                    description = null
                )
            }
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
                            painter = categoryImage.asPainter(),
                            contentDescription = "Task Icon",
                            modifier = Modifier.size(32.dp),
                            tint = TudeeTheme.color.statusColors.purpleAccent
                        )
                    },
                    priorityIcon = painterResource(categoryTask.priority.drawableRes)
                )
            }
        }

        CategorySheet(
            state = CategorySheetState.edit(
                isVisible = isEditCategorySheetVisible,
                initialData = CategoryData(
                    name = categoryName,
                    uiImage = categoryImage
                )
            ),
            onDismiss = onBottomSheetDismissed,
            onConfirm = { onSaveButtonClicked(it) },
            onCancel = onCancelButtonClicked,
            onDelete = showDeleteCategorySheet
        )

        DeleteConfirmationBottomSheet(
            isBottomSheetVisible = isDeleteCategorySheetVisible,
            title = stringResource(R.string.delete_category),
            subtitle = stringResource(R.string.delete_category_confirmation),
            deleteButtonUiState = ButtonState.IDLE,
            cancelButtonUiState = ButtonState.IDLE,
            onBottomSheetDismissed = hideDeleteCategorySheet,
            onDeleteButtonClicked = onDeleteCategory,
            onCancelButtonClicked = hideDeleteCategorySheet
        )
    }

}

@Composable
private fun CategoryTasksSnackBar(
    isSnackBarVisible: Boolean,
    hideSnackBar: () -> Unit,
    @StringRes snackBarStringId: Int,
    @DrawableRes snackBarIconId: Int
) {
    LaunchedEffect(isSnackBarVisible) {
        delay(3000)
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
            Modifier
                .statusBarsPadding()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            SnackBarComponent(
                message = stringResource(snackBarStringId),
                iconPainter = painterResource(id = snackBarIconId),
                iconDescription = stringResource(snackBarStringId),
                iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant,
                iconTint = TudeeTheme.color.statusColors.greenAccent
            )
        }
    }
}