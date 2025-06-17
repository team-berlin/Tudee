package com.example.tudee.presentation.screen

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.tudee.presentation.components.CategoryTaskComponent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.viewmodel.taskuistate.TaskDetailsUiState
import com.example.tudee.presentation.utils.Constant.PRIORITY_HIGH
import com.example.tudee.presentation.utils.Constant.STATUS_DONE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FakeHomeScreen() {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<TaskDetailsUiState?>(null) }

    val sampleTask = TaskDetailsUiState(
        id = 3L,
        title = "Study Jetpack Compose",
        description = "Finish the layout chapter and preview tips",
        categoryIconRes = R.drawable.ic_category_book_open,
        priority = PRIORITY_HIGH,
        status = STATUS_DONE,
    )

    CategoryTaskComponent(
        title = sampleTask.title,
        description = sampleTask.description,
        priority =sampleTask.priority,
        priorityBackgroundColor = TudeeTheme.color.statusColors.yellowAccent,
        taskIcon = {
            Icon(
                painter = painterResource(id = sampleTask.categoryIconRes),
                contentDescription = "Task Icon",
                modifier = Modifier.size(32.dp),
                tint = TudeeTheme.color.statusColors.purpleAccent
            )
        },
        priorityIcon = painterResource(id =R.drawable.ic_priority_high),
        onClick = {
            selectedTask = sampleTask
            showBottomSheet = true
        }
    )

    if (showBottomSheet && selectedTask != null) {
        val bottomSheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState,
            containerColor = TudeeTheme.color.surface,
        ) {
            TaskDetailsScreen(taskDetailsState = selectedTask!!)
        }
    }
}
