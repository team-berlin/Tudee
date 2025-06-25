package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.tudee.R
import com.example.tudee.presentation.components.buttons.FabButton

@Composable
fun TaskScreenFloatingActionButton(onFloatingActionClicked: () -> Unit) {
    FabButton(onClick = {
        onFloatingActionClicked()
    }, content = {
        Icon(
            painter = painterResource(R.drawable.note_add), contentDescription = null
        )
    })
}