package com.example.tudee.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.composables.buttons.FabButton

@Composable
fun TudeeScaffold(
    modifier: Modifier = Modifier,
    showTopAppBar: Boolean = false,
    topAppBar: @Composable (() -> Unit)? = null,
    showBottomBar: Boolean = false,
    bottomBarContent: @Composable (() -> Unit)? = null,
    showFab: Boolean = false,
    floatingActionButton: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = TudeeTheme.color.surface,
        topBar = {
            if (showTopAppBar && topAppBar != null) {
                topAppBar()
            }
        },
        bottomBar = {
            if (showBottomBar && bottomBarContent != null) {
                bottomBarContent()
            }
        },
        floatingActionButton = {
            if (showFab && floatingActionButton != null) {
                floatingActionButton()
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_4)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
private fun TudeeScaffoldPreview() {
    val topAppBar = @Composable {
        TopAppBar(
            title = "Tasks", trailingComposable = {
                Icon(
                    modifier = Modifier
                        .border(
                            1.dp, TudeeTheme.color.stroke, RoundedCornerShape(100.dp)
                        )
                        .padding(10.dp)
                        .size(20.dp),
                    painter = painterResource(R.drawable.filter_icon),
                    contentDescription = stringResource(R.string.back_button),
                    tint = TudeeTheme.color.textColors.body
                )
            }
        )
    }

    val bottomBar = @Composable {
        NavBar(
            navDestinations = listOf(
                BottomNavItem(
                    icon = painterResource(id = R.drawable.home),
                    selectedIcon = painterResource(id = R.drawable.home_select),
                    route = "home"
                ),
                BottomNavItem(
                    icon = painterResource(id = R.drawable.task),
                    selectedIcon = painterResource(id = R.drawable.task_select),
                    route = "search"
                ),
                BottomNavItem(
                    icon = painterResource(id = R.drawable.category),
                    selectedIcon = painterResource(id = R.drawable.category_select),
                    route = "profile"
                )
            ),
            currentRoute = "home",
            onNavDestinationClicked = {}
        )
    }

    val fabButton = @Composable {
        FabButton(
            onClick = {},
            content = {
                Icon(
                    painter = painterResource(R.drawable.note_add),
                    contentDescription = null
                )
            }
        )
    }

    TudeeTheme {
        TudeeScaffold(
            showTopAppBar = true,
            topAppBar = {
                topAppBar()
            },
            showBottomBar = true,
            bottomBarContent = bottomBar,
            showFab = true,
            floatingActionButton = fabButton
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
        }
    }
}
