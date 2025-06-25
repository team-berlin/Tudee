package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.buttons.ButtonState
import com.example.tudee.presentation.components.buttons.NegativeButton
import com.example.tudee.presentation.components.buttons.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteConfirmationBottomSheet(
    isBottomSheetVisible: Boolean,
    title: String,
    subtitle: String,
    deleteButtonUiState: ButtonState,
    cancelButtonUiState: ButtonState,
    onBottomSheetDismissed: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,

    ) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            modifier = Modifier, containerColor = TudeeTheme.color.surface, onDismissRequest = {
                onBottomSheetDismissed()

            }, sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surface)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = title,
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title,

                    )
                Text(
                    text = subtitle,
                    style = TudeeTheme.textStyle.body.large,
                    color = TudeeTheme.color.textColors.body,
                )
                Image(
                    painter = painterResource(R.drawable.delete_task_bot),
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 107.dp, height = 100.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surfaceHigh)
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NegativeButton(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        onDeleteButtonClicked()

                    }, state = deleteButtonUiState
                ) {
                    Text(stringResource(R.string.delete))
                }
                SecondaryButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    onCancelButtonClicked()
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}