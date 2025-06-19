package com.example.tudee.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme


data class TabBarItem(
    val title: Int,
    val taskCount: String,
    val isSelected: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    contentColor: Color = Color.Unspecified,
    selectedTabIndex: Int,
    tabBarItems: List<TabBarItem>,
    onTabSelected: (Int) -> Unit,
    tabContent: @Composable (tab: TabBarItem, isSelected: Boolean) -> Unit = { tab, isSelected ->
        DefaultTabContent(
            tabBarItem = tab,
            isSelected = isSelected,
            modifier = Modifier
        )
    },
    tabIndicator: @Composable TabIndicatorScope.() -> Unit = {
        DefaultTabIndicator(selectedTabIndex, Modifier)
    },
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh),
        indicator = tabIndicator
        , divider = { TabBarHorizontalDivider() },
        containerColor = backgroundColor,
        contentColor = contentColor
    ) {
        tabBarItems.forEachIndexed { index, tabItem ->

            Tab(
                selected = index == selectedTabIndex,
                onClick = {
                    onTabSelected(index)
                },
                text = {
                    tabContent(tabItem, index == selectedTabIndex)
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabIndicatorScope.DefaultTabIndicator(selectedTabIndex: Int, modifier: Modifier) =
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(1000)),
        exit = fadeOut(tween(1000))
    ) {

        TabRowDefaults.PrimaryIndicator(
            modifier = modifier.tabIndicatorOffset(
                selectedTabIndex,
                matchContentSize = true
            ),
            height = 4.dp,
            color = TudeeTheme.color.secondary,
            shape = (RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
            width = Dp.Unspecified
        )
    }
@Composable
private fun DefaultTabContent(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    tabBarItem: TabBarItem,

    ) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(tabBarItem.title.toInt()),
            modifier = Modifier.padding(end = 4.dp),
            style = if (tabBarItem.isSelected) TudeeTheme.textStyle.label.medium else TudeeTheme.textStyle.label.small,
            color = if (tabBarItem.isSelected) TudeeTheme.color.textColors.title else TudeeTheme.color.textColors.hint
        )
        if (isSelected) {
            Box(
                Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(TudeeTheme.color.surfaceLow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    tabBarItem.taskCount,
                    style = TudeeTheme.textStyle.label.medium,
                    color = TudeeTheme.color.textColors.body
                )
            }
        }
    }
}


@Composable
private fun TabBarHorizontalDivider() {
    HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = TudeeTheme.color.stroke)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TabBarComponentPreview() {
    val defaultTabBarHeaders = listOf<TabBarItem>(
        TabBarItem(
            title = 0,
            taskCount = "0",
            isSelected = true
        ),
        TabBarItem(
            title = 0,
            taskCount = "0",
            isSelected = false
        ),
        TabBarItem(
            title = 0,
            taskCount = "0",
            isSelected = false
        ),
    )
    TudeeTheme {
        TabBarComponent(
            modifier = Modifier,
            selectedTabIndex = 0,
            tabBarItems = defaultTabBarHeaders,
            onTabSelected = {},
//            tabContent = { DefaultTabContent(tabBarItem = it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultTabContentPreview(
    tabBarItem: TabBarItem = TabBarItem(0, "2", true)
) {
    TudeeTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(tabBarItem.title),
                modifier = Modifier.padding(end = 4.dp),
                style = if (tabBarItem.isSelected) TudeeTheme.textStyle.label.medium else TudeeTheme.textStyle.label.small,
                color = if (tabBarItem.isSelected) TudeeTheme.color.textColors.title else TudeeTheme.color.textColors.hint
            )
            if (tabBarItem.isSelected) {
                Box(
                    Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(100))
                        .background(TudeeTheme.color.surfaceLow),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        tabBarItem.taskCount,
                        style = TudeeTheme.textStyle.label.medium,
                        color = TudeeTheme.color.textColors.body
                    )
                }
            }
        }
    }
}

