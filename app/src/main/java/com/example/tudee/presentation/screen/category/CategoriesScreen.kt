package com.example.tudee.presentation.screen.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.components.CategoryItemWithBadge
import com.example.tudee.presentation.components.SnackBarComponent
import com.example.tudee.presentation.components.TopAppBar
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.components.buttons.ButtonDefaults
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.FabButton
import com.example.tudee.presentation.screen.category.component.CategorySheet
import com.example.tudee.presentation.screen.category.model.CategoriesUiState
import com.example.tudee.presentation.screen.category.model.CategoryData
import com.example.tudee.presentation.screen.category.model.CategorySheetState
import com.example.tudee.presentation.screen.category.tasks.SnackBarEvent
import com.example.tudee.presentation.screen.category.viewmodel.CategoriesViewModel
import com.example.tudee.presentation.screen.task_screen.ui.TaskScreenBottomAppBar
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val isEditCategorySheetVisible by viewModel.isEditCategorySheetVisible.collectAsState()
    var showSnackBar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.snackBarEvent.collect { event ->
            when (event) {
                is SnackBarEvent.ShowError -> {}
                is SnackBarEvent.ShowSuccess -> {
                    showSnackBar = true
                }
            }
        }
    }

    CategoriesScreenContent(
        state = state,
        onCategoryClick = { categoryId ->
            navController.navigate(Destination.CategoryTasksScreen(categoryId))
        },
        onAddCategoryClick = viewModel::showEditCategorySheet,
        navController = navController,
        isEditCategorySheetVisible = isEditCategorySheetVisible,
        onDismissEditCategorySheet = viewModel::hideEditCategorySheet,
        onConfirmEditCategorySheet = { categoryData ->
            categoryData.uiImage?.let {
                viewModel.addCategory(categoryData.name, it.asString())
            }
        },
        onCancelCategorySheet = viewModel::hideEditCategorySheet,
        showSnackBar = showSnackBar,
        hideSnackBar = { showSnackBar = false }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreenContent(
    state: CategoriesUiState,
    onCategoryClick: (Long) -> Unit,
    onAddCategoryClick: () -> Unit,
    navController: NavHostController,
    isEditCategorySheetVisible: Boolean,
    onDismissEditCategorySheet: () -> Unit,
    onConfirmEditCategorySheet: (CategoryData) -> Unit,
    onCancelCategorySheet: () -> Unit,
    showSnackBar: Boolean,
    hideSnackBar: () -> Unit
) {
    TudeeScaffold(
        floatingActionButton = {
            CategoriesFab(onAddCategoryClick)
        },
        showTopAppBar = true,
        topAppBar = {
            TopAppBar(
                title = "Categories",
                showBackButton = false,
                modifier = Modifier.background(TudeeTheme.color.surfaceHigh),
                titleStyle = TudeeTheme.textStyle.title.large
            )
        },
        showFab = true,
        showBottomBar = true,
        bottomBarContent = { TaskScreenBottomAppBar(navController = navController) },
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${state.error}")
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(padding)
                ) {
                    items(
                        items = state.categories
                    ) { category ->
                        CategoryItemWithBadge(
                            categoryPainter = category.iconResId.asPainter(),
                            categoryName = category.name,
                            badgeCount = category.tasksCount,
                            categoryImageContentDescription = category.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    onCategoryClick(category.id)
                                }
                        )
                    }
                }

                CategorySheet(
                    state = CategorySheetState.add(
                        isVisible = isEditCategorySheetVisible
                    ),
                    onDismiss = onDismissEditCategorySheet,
                    onConfirm = onConfirmEditCategorySheet,
                    onCancel = onCancelCategorySheet
                )
            }
        }
    }

    SnackBarSection(
        isSnackBarVisible = showSnackBar,
        hideSnackBar = hideSnackBar
    )
}

@Composable
private fun SnackBarSection(
    isSnackBarVisible: Boolean, hideSnackBar: () -> Unit
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
                message = stringResource(R.string.snack_bar_category_added),
                iconPainter = painterResource(id = R.drawable.ic_success),
                iconDescription = stringResource(R.string.snack_bar_category_added),
                iconBackgroundColor = TudeeTheme.color.statusColors.greenVariant,
                iconTint = TudeeTheme.color.statusColors.greenAccent
            )
        }
    }
}

@Composable
private fun CategoriesFab(
    onClick: () -> Unit
) {
    FabButton(
        onClick = onClick,
        state = ButtonState.IDLE,
        modifier = Modifier.size(64.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        buttonColors = ButtonDefaults.colors().copy(
            backgroundGradient = Brush.horizontalGradient(
                TudeeTheme.color.primaryGradient
            )
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_categories_fab),
            contentDescription = "Add Category",
            modifier = Modifier.size(28.dp)
        )
    }
}
