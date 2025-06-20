package com.example.tudee.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.tudee.presentation.composables.buttons.PrimaryButton
import com.example.tudee.presentation.composables.buttons.SecondaryButton
import com.example.tudee.presentation.screen.category.dashedBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategorySheet(
    modifier: Modifier = Modifier,
    isBottomSheetVisible: Boolean,
    title: String = "Add new category",
    onBottomSheetDismissed: () -> Unit,
    onCancelClick: () -> Unit,
    onAddClick: () -> Unit,
    onUploadImageClick: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var categoryName by remember { mutableStateOf("") }
    var isImageUploaded by remember { mutableStateOf(true) }
    val uploadedImage = if (isImageUploaded) painterResource(R.drawable.books) else null

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) sheetState.show()
        else sheetState.hide()
    }

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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = title,
                    style = TudeeTheme.textStyle.title.large,
                    color = TudeeTheme.color.textColors.title
                )

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
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Category image",
                        style = TudeeTheme.textStyle.title.medium,
                        color = TudeeTheme.color.textColors.title
                    )

                    UploadImagePlaceholder(
                        categoryImage = uploadedImage,
                        isImageUploaded = isImageUploaded,
                        onClick = {
                            onUploadImageClick()
                            isImageUploaded = true
                        }
                    )
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
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = onAddClick,
                    enabled = categoryName.isNotBlank()
                ) {
                    Text(text = "Add", style = TudeeTheme.textStyle.label.large)
                }

                SecondaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = onCancelClick
                ) {
                    Text(text = "Cancel", style = TudeeTheme.textStyle.label.large)
                }
            }
        }
    }
}

@Composable
fun UploadImagePlaceholder(
    categoryImage: Painter?,
    isImageUploaded: Boolean,
    onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x1A000000))
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
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isImageUploaded && categoryImage != null) {
            // Show uploaded image filling the whole box
            Image(
                painter = categoryImage,
                contentDescription = stringResource(R.string.upload_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )

            // Pencil icon
            IconButton(
                onClick = onClick,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.pencil_edit),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(TudeeTheme.color.surfaceHigh)
                        .padding(6.dp)
                        .size(20.dp),
                    contentDescription = stringResource(R.string.edit_uploaded_image),
                    tint = TudeeTheme.color.secondary
                )
            }

        } else {
            // Upload prompt
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_upload_image),
                    contentDescription = stringResource(R.string.upload_image),
                    tint = TudeeTheme.color.textColors.title,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.upload_image),
                    style = TudeeTheme.textStyle.label.medium,
                    color = TudeeTheme.color.textColors.title
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun AddCategorySheetPreview() {
    AddCategorySheet(
        isBottomSheetVisible = true,
        onBottomSheetDismissed = {},
        onCancelClick = {},
        onAddClick = {},
        onUploadImageClick = {}
    )
}