package com.example.tudee.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    tasksCount :String,
    headers: List<String>,
    modifier: Modifier = Modifier,
    onTabSelected : (Int) -> Unit,

    ) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    PrimaryTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh)
        ,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(
                    selectedIndex,
                    matchContentSize = true
                ),
                height = 4.dp,
                color = TudeeTheme.color.secondary,
                shape = (RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                width = Dp.Unspecified,
            )

        }, divider = {TabBarHorizontalDivider() },
        containerColor = Color.Transparent,
        contentColor = Color.Unspecified
    ) {
        headers.forEachIndexed { index, header ->
            val isSelected=index == selectedIndex
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    onTabSelected(index)
                    selectedIndex = index
                },
                modifier = Modifier,
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = header,
                            modifier = Modifier.padding(end = 4.dp),
                            style = if(isSelected) TudeeTheme.textStyle.label.medium else TudeeTheme.textStyle.label.small,
                            color = if(isSelected) TudeeTheme.color.textColors.title else TudeeTheme.color.textColors.hint
                        )
                        if (index == selectedIndex) {
                            Box(
                                Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(TudeeTheme.color.surfaceLow),
                                contentAlignment = Alignment.Center

                            ) {
                                Text(tasksCount, style = TudeeTheme.textStyle.label.medium, color = TudeeTheme.color.textColors.body)
                            }
                        }
                    }
                },
            )

        }
    }
}

@Composable
private fun TabBarHorizontalDivider() {
    HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = TudeeTheme.color.stroke)
}

@Composable
private fun TabBarIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier
            .height(4.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(TudeeTheme.color.secondary)
    )
}

@Preview(showBackground = true)
@Composable
private fun TabBarIndicatorPreview() {
    TabBarIndicator()
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun TabBarComponentPreview(){
    TabBarComponent(

        modifier = Modifier,
        onTabSelected = {},
        tasksCount ="0",
        headers = listOf("In progress","To Do","Done"),
    )
}
