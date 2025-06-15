package com.example.tudee.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tudee.designsystem.theme.TudeeTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.tudee.R


@Composable
fun CategoryTaskComponent(
    title: String = "Organize Study Desk",
    description: String? = null,
    priority: String = "Medium",
    priorityBackgroundColor: Color = TudeeTheme.color.statusColors.yellowAccent,
    modifier: Modifier = Modifier,
    taskIcon: @Composable () -> Unit,
    priorityIcon: @Composable () -> Unit,
    dateIcon: (@Composable () -> Unit)? = null,
    dateText: String? = null
) {
    val colors = TudeeTheme.color
    val textStyles = TudeeTheme.textStyle

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp)
            .border(1.dp, colors.textColors.surfaceHigh, RoundedCornerShape(16.dp))
            .background(colors.textColors.surfaceHigh)
            .padding(top = 4.dp, start = 4.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center
            ) {
                taskIcon()
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(28.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (dateIcon != null && dateText != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(
                        modifier = Modifier
                            .width(98.dp)
                            .height(28.dp)
                            .background(Color(0xFFF9F9F9), RoundedCornerShape(100.dp))
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        dateIcon()
                        Text(
                            text = dateText,
                            style = textStyles.body.small.copy(
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color(0x991F1F1F),
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily(Font(R.font.nunito_medium))
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                Row(
                    modifier = Modifier
                        .height(28.dp)
                        .background(priorityBackgroundColor, RoundedCornerShape(100.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    priorityIcon()
                    Text(
                        text = priority,
                        style = textStyles.body.small.copy(
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = colors.textColors.surface
                        ),
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(37.dp)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = textStyles.title.medium.copy(
                    fontSize = 16.sp,
                    lineHeight = 19.sp,
                    color = colors.textColors.body
                )
            )
            description?.let { description ->
                Text(
                    text = description,
                    style = textStyles.body.small.copy(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = colors.textColors.hint
                    ),
                    modifier = Modifier
                        .width(296.dp)
                        .height(19.dp)
                )
            }
        }
    }
}
@Composable
fun CustomIcon(
    vectorId: Int,
    contentDescription: String,
    size: Dp,
    tint: Color
) {
    Icon(
        painter = painterResource(id = vectorId),
        contentDescription = contentDescription,
        modifier = Modifier.size(size),
        tint = tint
    )
}


@Preview(showBackground = true)
@Composable
fun CategoryTaskComponentPreview() {
    CategoryTaskComponent(
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        priority = "Medium",
        priorityBackgroundColor = TudeeTheme.color.statusColors.yellowAccent,
        taskIcon = {
            CustomIcon(
                vectorId = R.drawable.book_open1,
                contentDescription = "Task Icon",
                size = 32.dp,
                tint = TudeeTheme.color.statusColors.purpleAccent
            )
        },
        priorityIcon = {
            CustomIcon(
                vectorId = R.drawable.alert_error,
                contentDescription = "Priority Icon",
                size = 12.dp,
                tint = TudeeTheme.color.textColors.onPrimary
            )
        },
        dateIcon = {
            CustomIcon(
                vectorId = R.drawable.calendar_favorite1,
                contentDescription = "Date Icon",
                size = 12.dp,
                tint = TudeeTheme.color.textColors.body
            )
        },
        dateText = "12-03-2025"
    )
}
