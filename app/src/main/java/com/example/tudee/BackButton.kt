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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle

typealias EmptyLambda = () -> Unit

@Composable
fun BackButton(
    onBackClicked: () -> Unit,
    label: String? = null,
    endComposable: (@Composable () -> Unit)? = null,
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
                style = TudeeTextStyle.title.medium,
            )
            if (label != null) Text(
                text = label,
                style = TudeeTextStyle.label.small,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        endComposable?.invoke()

    }
}

@Preview(showBackground = true)
@Composable
private fun BackButtonPreview() {
    BackButton(
        onBackClicked = {},
        label = "Label",
    ) {
        Icon(painterResource(R.drawable.back_button), null)
    }
}