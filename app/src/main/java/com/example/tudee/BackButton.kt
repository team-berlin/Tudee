package com.example.tudee

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

typealias EmptyLambda = () -> Unit

@Preview(showBackground = true)
@Composable
private fun BackButton(
    onBackClicked: EmptyLambda = {},
    label: String? = "null",
    clickableIcon: Painter = painterResource(R.drawable.back_button),
    onOptionClicked: EmptyLambda = {},
    clickableIconContentDescription: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier,
        ) {
            Icon(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Back Button",
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = "Tasks",
                fontSize = 20.sp,
                lineHeight = 24.sp,
            )
            if (label != null) Text(
                text = label,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier,
        ) {
            Icon(
                painter = clickableIcon,
                contentDescription = clickableIconContentDescription,
            )
        }

    }
}