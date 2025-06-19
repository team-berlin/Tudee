package com.example.tudee.presentation.screen.manipulateTask.editTask

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tudee.presentation.screen.manipulateTask.TaskBottomSheet
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun EditTaskBottomSheet(taskId: Long = 1L) {

    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet) TaskBottomSheet(taskId)

    Column(modifier = Modifier.padding(50.dp)) {
        Button(
            onClick = {
                Log.d("EditScreen", "Edit button clicked")
                showBottomSheet = true
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = true
        ) {
            Text("Edit Task", color = TudeeTheme.color.textColors.onPrimary)
        }
    }
}