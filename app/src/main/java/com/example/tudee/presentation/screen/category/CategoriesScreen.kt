package com.example.tudee.presentation.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.naviagtion.Destination
import com.example.tudee.presentation.components.CategoryItemWithBadge
import com.example.tudee.presentation.components.TopAppBar
import com.example.tudee.presentation.components.TudeeScaffold
import com.example.tudee.presentation.components.buttons.ButtonDefaults
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.FabButton
import com.example.tudee.presentation.screen.category.model.CategoriesUiState
import com.example.tudee.presentation.screen.category.model.TaskCategoryUiModel
import com.example.tudee.presentation.screen.category.model.UiImage
import com.example.tudee.presentation.screen.category.viewmodel.CategoriesViewModel
import com.example.tudee.presentation.screen.task_screen.ui.TaskScreenBottomAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    CategoriesScreenContent(
        state = state,
        onCategoryClick = { categoryId ->
            navigateToCategoryDetails(navController, categoryId)
        },
        onAddCategoryClick = { showAddCategoryBottomSheet() },
        currentRoute = currentRoute,
        navController = navController
    )

}

@Composable
fun CategoriesScreenContent(
    state: CategoriesUiState,
    onCategoryClick: (Long) -> Unit,
    onAddCategoryClick: () -> Unit,
    currentRoute: String,
    navController: NavHostController
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
            }
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

private fun navigateToCategoryDetails(navController: NavHostController, categoryId: Long) {
    //navController.navigate("${Destination.CategoryDetailsScreen.route}/$categoryId")
    navController.navigate(Destination.CategoryTasksScreen.createRoute(categoryId))
}

private fun showAddCategoryBottomSheet() {
    // TODO: Replace with your actual bottom sheet handling logic
}


@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    val navController = rememberNavController()

    val fakeCategories = listOf(
        TaskCategoryUiModel(
            id = 1,
            name = "Work",
            iconResId = UiImage.Drawable(R.drawable.tudee),
            tasksCount = 5,
            isPredefined = true
        ),
        TaskCategoryUiModel(
            id = 2,
            name = "Personal",
            iconResId = UiImage.Drawable(R.drawable.ic_category_book_open),
            tasksCount = 3,
            isPredefined = true
        ),
        TaskCategoryUiModel(
            id = 3,
            name = "Fitness",
            iconResId = UiImage.Drawable(R.drawable.tudee),
            tasksCount = 7,
            isPredefined = false
        ),
        TaskCategoryUiModel(
            id = 3,
            name = "Fitness",
            iconResId = UiImage.Drawable(R.drawable.ic_category_book_open),
            tasksCount = 7,
            isPredefined = false
        ),
        TaskCategoryUiModel(
            id = 3,
            name = "Fitness",
            iconResId = UiImage.Drawable(R.drawable.tudee),
            tasksCount = 7,
            isPredefined = false
        )
    )

    val fakeUiState = CategoriesUiState(categories = fakeCategories)

    TudeeTheme {
        CategoriesScreenContent(
            state = fakeUiState,
            onCategoryClick = {},
            onAddCategoryClick = {},
            currentRoute = Destination.CategoriesScreen.route,
            navController = navController
        )
    }
}