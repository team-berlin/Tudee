package com.example.tudee.presentation.screen.category.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.DefaultLeadingContent
import com.example.tudee.presentation.components.TudeeTextField
import com.example.tudee.presentation.components.buttons.PrimaryButton
import com.example.tudee.presentation.components.buttons.SecondaryButton
import com.example.tudee.presentation.components.buttons.TextButton
import com.example.tudee.presentation.screen.category.dashedBorder
import com.example.tudee.presentation.screen.category.model.CategoryData
import com.example.tudee.presentation.screen.category.model.CategorySheetMode
import com.example.tudee.presentation.screen.category.model.CategorySheetState
import com.example.tudee.presentation.screen.category.model.UiImage
import com.example.tudee.presentation.screen.category.model.isNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySheet(
    modifier: Modifier = Modifier,
    state: CategorySheetState,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit,
    onConfirm: (CategoryData) -> Unit,
    onCancel: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    LaunchedEffect(state.isVisible) {
        if (state.isVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    if (state.isVisible) {
        ModalBottomSheet(
            modifier = modifier,
            containerColor = TudeeTheme.color.surface,
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            CategorySheetContent(
                state = state,
                onConfirm = onConfirm,
                onCancel = onCancel,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun CategorySheetContent(
    state: CategorySheetState,
    onConfirm: (CategoryData) -> Unit,
    onCancel: () -> Unit,
    onDelete: (() -> Unit)?
) {
    var categoryName by remember(state.initialData) {
        mutableStateOf(state.initialData.name)
    }

    var selectedUiImage by remember(state.initialData) {
        mutableStateOf(state.initialData.uiImage)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            selectedUiImage = UiImage.External(it.toString())
        }
    }

    val isFormValid = categoryName.isNotBlank() && selectedUiImage.isNotNull()

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategorySheetHeader(
                title = state.title,
                showDeleteButton = state.mode == CategorySheetMode.Edit,
                onDelete = onDelete
            )

            CategoryNameField(
                value = categoryName,
                onValueChange = { categoryName = it }
            )

            CategoryImageSection(
                selectedImage = selectedUiImage,
                onEditImage = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }

        CategorySheetActions(
            mode = state.mode,
            isFormValid = isFormValid,
            onConfirm = {
                onConfirm(
                    CategoryData(
                        name = categoryName.trim(),
                        uiImage = selectedUiImage
                    )
                )
            },
            onCancel = onCancel
        )
    }
}

@Composable
private fun CategorySheetHeader(
    title: String,
    showDeleteButton: Boolean,
    onDelete: (() -> Unit)?
) {
    if (showDeleteButton && onDelete != null) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TudeeTheme.textStyle.title.large,
                color = TudeeTheme.color.textColors.title,
            )

            TextButton(
                modifier = Modifier.wrapContentHeight(),
                onClick = onDelete,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Delete",
                    style = TudeeTheme.textStyle.label.large,
                    color = TudeeTheme.color.statusColors.error,
                )
            }
        }
    } else {
        Text(
            text = title,
            style = TudeeTheme.textStyle.title.large,
            color = TudeeTheme.color.textColors.title,
        )
    }
}

@Composable
private fun CategoryNameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TudeeTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = "Category title",
        leadingContent = { isFocused ->
            DefaultLeadingContent(
                painter = painterResource(R.drawable.menu_circle),
                isFocused = isFocused
            )
        }
    )
}

@Composable
private fun CategoryImageSection(
    selectedImage: UiImage?,
    onEditImage: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Category image",
            style = TudeeTheme.textStyle.title.medium,
            color = TudeeTheme.color.textColors.title,
        )

        CategoryImagePicker(
            image = selectedImage,
            onEditImage = onEditImage
        )
    }
}

@Composable
fun CategoryImagePicker(
    image: UiImage?,
    onEditImage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .height(113.dp)
            .width(112.dp)
            .dashedBorder(
                color = TudeeTheme.color.stroke,
                shape = RoundedCornerShape(16.dp),
                strokeWidth = 2.dp,
                dashLength = 6.dp,
                gapLength = 6.dp,
                cap = StrokeCap.Round
            )
            .clickable { onEditImage() },
        contentAlignment = Alignment.Center
    ) {
        if (image != null) {
            ImageWithOverlay(image = image)
        } else {
            EmptyImagePlaceholder()
        }
    }
}

@Composable
private fun ImageWithOverlay(image: UiImage) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x1A000000)),
            painter = image.asPainter(),
            contentDescription = stringResource(R.string.category_image_desc),
            contentScale = ContentScale.Crop
        )

        EditImageIcon(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun EditImageIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(R.drawable.pencil_edit),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(TudeeTheme.color.surfaceHigh)
            .padding(6.dp)
            .size(20.dp),
        contentDescription = "Edit image",
        tint = TudeeTheme.color.secondary
    )
}

@Composable
private fun EmptyImagePlaceholder() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_upload_image),
            contentDescription = "Add image",
            modifier = Modifier.size(22.dp),
            tint = TudeeTheme.color.textColors.hint
        )

        Text(
            text = "Upload",
            style = TudeeTheme.textStyle.label.medium,
            color = TudeeTheme.color.textColors.hint
        )
    }
}

@Composable
private fun CategorySheetActions(
    mode: CategorySheetMode,
    isFormValid: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(TudeeTheme.color.surfaceHigh)
            .padding(horizontal = 16.dp)
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onConfirm,
            enabled = isFormValid
        ) {
            Text(
                text = when (mode) {
                    CategorySheetMode.Add -> "Add"
                    CategorySheetMode.Edit -> "Save"
                },
                style = TudeeTheme.textStyle.label.large
            )
        }

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onCancel
        ) {
            Text(
                text = "Cancel",
                style = TudeeTheme.textStyle.label.large
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun EditCategorySheetPreview() {
    CategorySheet(
        state = CategorySheetState.edit(
            isVisible = true,
            initialData = CategoryData(
                name = "Reading",
                uiImage = UiImage.Drawable(R.drawable.books)
            )
        ),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            density = LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDismiss = { },
        onConfirm = { },
        onCancel = { }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun AddCategorySheetPreview() {
    CategorySheet(
        state = CategorySheetState.add(
            isVisible = true
        ),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            density = LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDismiss = { },
        onConfirm = { },
        onCancel = { }
    )
}