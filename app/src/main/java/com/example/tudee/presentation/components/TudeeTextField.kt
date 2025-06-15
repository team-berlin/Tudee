package com.example.tudee.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun TudeeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    @DrawableRes leadingIcon: Int? = null,
    placeholder: String? = null,
    textStyle: TextStyle = TudeeTheme.textStyle.body.medium,
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor = if (isFocused) TudeeTheme.color.primary else TudeeTheme.color.stroke

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = borderColor
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        textStyle = textStyle.copy(color = TudeeTheme.color.textColors.body),
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top,
            ) {
                leadingIcon?.let {
                    LeadingContent(leadingIcon, isFocused)
                }

                TextFieldWithPlaceHolder(
                    innerTextField = innerTextField,
                    modifier = Modifier.weight(1f),
                    value = value,
                    placeholder = placeholder
                )
            }
        }
    )
}

@Composable
private fun LeadingContent(
    @DrawableRes leadingIcon: Int,
    isFocused: Boolean
) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(leadingIcon),
            contentDescription = null,
            tint = if (isFocused) TudeeTheme.color.textColors.body else TudeeTheme.color.textColors.hint,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .size(24.dp)
        )
        VerticalDivider(
            modifier = Modifier
                .size(width = 1.dp, height = 30.dp),
            color = TudeeTheme.color.stroke
        )
    }
}

@Composable
private fun TextFieldWithPlaceHolder(
    innerTextField: @Composable () -> Unit,
    modifier: Modifier,
    value: String,
    placeholder: String?
) {
    Box(
        modifier = modifier
            .padding(12.dp)
    ) {
        innerTextField()
        if (value.isEmpty() && placeholder != null) {
            Text(
                text = placeholder,
                style = TudeeTheme.textStyle.label.medium,
                color = TudeeTheme.color.textColors.hint
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NoFocusPreview() {
    var text by remember { mutableStateOf("") }

    TudeeTextField(
        modifier = Modifier,
        placeholder = "Full name",
        leadingIcon = R.drawable.user_icon,
        textStyle = TudeeTheme.textStyle.body.medium,
        value = text,
        onValueChange = { text = it }
    )
}

@Preview(showBackground = true)
@Composable
private fun NoLeadingIconPreview() {
    var text by remember { mutableStateOf("") }

    TudeeTextField(
        modifier = Modifier.height(100.dp),
        placeholder = "Full name",
        singleLine = false,
        textStyle = TudeeTheme.textStyle.body.medium,
        value = text,
        onValueChange = { text = it }
    )
}