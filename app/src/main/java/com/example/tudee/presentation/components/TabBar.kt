package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarComponent(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabBarItems: List<TabBarItem>,
    onTabSelected: () -> Unit,
    tabContent: @Composable (tab: TabBarItem) -> Unit = { DefaultTabContent(it) },
    indicatorColor: Color = TudeeTheme.color.secondary,
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh),
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    selectedTabIndex,
                    matchContentSize = true
                ),
                height = 4.dp,
                color = indicatorColor,
                shape = (RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                width = Dp.Unspecified,
            )

        }, divider = { TabBarHorizontalDivider() },
        containerColor = Color.Transparent,
        contentColor = Color.Unspecified
    ) {
        tabBarItems.forEachIndexed { index, tabItem ->
            Tab(
                selected = tabItem.isSelected,
                onClick = {
                    onTabSelected()
                },
                modifier = Modifier,
                text = {
                    tabContent(tabItem)
                },
            )

        }
    }
}

@Composable
private fun DefaultTabContent(
    tabBarItem: TabBarItem
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tabBarItem.title,
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


@Composable
private fun TabBarHorizontalDivider() {
    HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = TudeeTheme.color.stroke)
}

@Preview(showBackground = true)
@Composable
fun TabBarComponentPreview() {

    val defaultTabBarHeaders = listOf<TabBarItem>(
        TabBarItem(
            title = "In Progress",
            taskCount = "0",
            isSelected = true
        ),
        TabBarItem(
            title = "To DO",
            taskCount = "0",
            isSelected = false
        ),
        TabBarItem(
            title = "Done",
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
            tabContent = { DefaultTabContent(it) }
        )
    }


}

data class TabBarItem(
    val title: String,
    val taskCount: String,
    val isSelected: Boolean,
)

