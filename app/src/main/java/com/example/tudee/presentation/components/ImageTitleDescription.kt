package com.example.tudee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.designsystem.theme.textstyle.TudeeTextStyle

@Composable
fun IconWithTitleAndDescription(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    titleImagePainter: Painter,
    description: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = Color(0x66FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color(0x66FFFFFF))
                .padding(horizontal = 6.2.dp, vertical = 7.2.dp)
                ,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null
            )
        }


        Column (verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Image(
                painter = titleImagePainter,
                contentDescription = null
            )

            Text(
                text = description,
                style = TudeeTextStyle.label.small,
                color = TudeeTheme.color.textColors.onPrimaryCaption
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x49BAF2
)
@Composable
private fun ImageTitleDescriptionPreview() {
    IconWithTitleAndDescription(
        imagePainter = painterResource(id = R.drawable.tudee),
        titleImagePainter = painterResource(id = R.drawable.app_name),
        description = stringResource(id = R.string.tudee_description)
    )
}