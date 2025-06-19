package com.example.tudee.presentation.screen.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.composables.buttons.ButtonState
import com.example.tudee.presentation.composables.buttons.PrimaryButton
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.presentation.composables.buttons.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategorySheet(
    modifier: Modifier = Modifier,
    title: String = "Edit category",
    isBottomSheetVisible: Boolean,
    deleteButtonUiState: ButtonState,
    saveButtonUiState: ButtonState,
    cancelButtonUiState: ButtonState,
    onBottomSheetDismissed: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    onEditCategoryImageClicked: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    var categoryName by remember { mutableStateOf("Reading novels") }
    val categoryImage = painterResource(R.drawable.books)

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            modifier = modifier,
            containerColor = TudeeTheme.color.surface,
            onDismissRequest = onBottomSheetDismissed,
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                density = LocalDensity.current,
                initialValue = SheetValue.Expanded
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SheetHeader(title, onDeleteButtonClicked)

                TudeeTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    leadingContent = { isFocused ->
                        DefaultLeadingContent(
                            painter = painterResource(R.drawable.menu_circle),
                            isFocused = isFocused
                        )
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Category image",
                        style = TudeeTheme.textStyle.title.medium,
                        color = TudeeTheme.color.textColors.title,
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0x1A000000))
                            .dashedBorder(
                                color = TudeeTheme.color.stroke,
                                shape = RoundedCornerShape(16.dp),
                                strokeWidth = 2.dp,
                                dashLength = 6.dp,
                                gapLength = 6.dp,
                                cap = StrokeCap.Round
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .height(113.dp),
                            painter = categoryImage,
                            contentDescription = null
                        )

                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(R.drawable.pencil_edit),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TudeeTheme.color.surfaceHigh)
                                    .padding(6.dp)
                                    .size(20.dp),
                                contentDescription = stringResource(R.string.back_button),
                                tint = TudeeTheme.color.secondary
                            )
                        }
                    }

                }
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(TudeeTheme.color.surfaceHigh)
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(), onClick = {
                        onDeleteButtonClicked()
                    }, state = deleteButtonUiState
                ) {
                    Text(
                        text = "Save",
                        style = TudeeTheme.textStyle.label.large
                    )
                }
                SecondaryButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    onCancelButtonClicked()
                }) {
                    Text(
                        text = "Cancel",
                        style = TudeeTheme.textStyle.label.large
                    )
                }
            }
        }
    }
}

@Composable
private fun SheetHeader(title: String, onDeleteButtonClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SheetTitle(title)
        DeleteButton(onDeleteButtonClicked)
    }
}

@Composable
private fun DeleteButton(onDeleteButtonClicked: () -> Unit) {
    TextButton(
        modifier = Modifier.wrapContentHeight(),
        onClick = onDeleteButtonClicked,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "Delete",
            style = TudeeTheme.textStyle.label.large,
            color = TudeeTheme.color.statusColors.error,
        )
    }
}

@Composable
private fun SheetTitle(title: String) {
    Text(
        text = title,
        style = TudeeTheme.textStyle.title.large,
        color = TudeeTheme.color.textColors.title,
    )
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun EditCategorySheetPreview() {
    EditCategorySheet(
        isBottomSheetVisible = true,
        deleteButtonUiState = ButtonState.IDLE,
        cancelButtonUiState = ButtonState.IDLE,
        onBottomSheetDismissed = {},
        onDeleteButtonClicked = {},
        onCancelButtonClicked = {},
        saveButtonUiState = ButtonState.IDLE,
        onSaveButtonClicked = {},
        onEditCategoryImageClicked = {}
    )
}