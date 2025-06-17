package com.example.tudee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.CategoryComponent
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeChip
import com.example.tudee.presentation.components.TudeeTextField
import kotlinx.coroutines.CoroutineScope

@Composable
fun AddNewTaskScreen(
    modifier: Modifier = Modifier,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ScrollableBottomSheetDemo(
    showSheetState: MutableState<Boolean> = mutableStateOf(false),
) {
    val sheetState = rememberModalBottomSheetState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (showSheetState.value) Color(0x99000000) else Color.Transparent) // 60% opacity
    ) {
        if (showSheetState.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showSheetState.value = false
                },
                sheetState = sheetState, // control the visibility of the sheet
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                containerColor = TudeeTheme.color.surface,
                modifier = Modifier.fillMaxHeight(0.8f)
            ) {

            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 852)
@Composable
fun BottomSheetContent(
    taskTitle: String = "Task 1",
    onTaskTitleChanged: (String) -> Unit = {},
    taskDescription: String = "This is a task description",
    onTaskDescriptionChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TudeeTextField(
            value = taskTitle,
            onValueChange = onTaskTitleChanged,
            leadingContent = { isFocused ->
                DefaultLeadingContent(
                    painter = painterResource(R.drawable.ic_notebook),
                    isFocused = isFocused
                )
            },
            placeholder = "Task title",
            textStyle = TudeeTheme.textStyle.label.medium
        )
        TudeeTextField(
            modifier = Modifier
                .height(168.dp),
            placeholder = "Description",
            singleLine = false,
            textStyle = TudeeTheme.textStyle.body.medium,
            value = taskDescription,
            onValueChange = onTaskDescriptionChanged
        )
        TudeeTextField(
            value = taskTitle,
            onValueChange = onTaskTitleChanged,
            leadingContent = { isFocused ->
                DefaultLeadingContent(
                    painter = painterResource(R.drawable.ic_add_calendar),
                    isFocused = isFocused
                )
            },
            placeholder = "set due date",
            textStyle = TudeeTheme.textStyle.label.medium
        )
        Text(
            text = "Priority",
            style = TudeeTheme.textStyle.title.medium,
            color = TudeeTheme.color.textColors.title
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val priorities = listOf("High", "Medium", "Low")
            var selected by remember { mutableStateOf("High") }
            priorities.forEach { label ->
                val isSelected = selected == label
                TudeeChip(
                    label = label,
                    modifier = Modifier.clickable {

                    },
                    labelColor = if (isSelected) TudeeTheme.color.textColors.onPrimary else TudeeTheme.color.textColors.hint,
                    backgroundColor = if (isSelected) TudeeTheme.color.statusColors.pinkAccent else TudeeTheme.color.surfaceLow,
                    icon = when (label) {
                        "High" -> painterResource(id = R.drawable.ic_priority_high)
                        "Medium" -> painterResource(id = R.drawable.ic_priority_medium)
                        "Low" -> painterResource(id = R.drawable.ic_priority_low)
                        else -> null
                    }, iconSize = 12.dp
                )
            }
        }
        Text(
            text = "Category",
            style = TudeeTheme.textStyle.title.medium,
            color = TudeeTheme.color.textColors.title
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 104.dp),
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(10) {
                CategoryComponent(
                    categoryPainter = painterResource(R.drawable.ic_education),
                    categoryImageContentDescription = "Education Category",
                    categoryName = "Education",
                )
            }
        }
    }
}
