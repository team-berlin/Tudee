package com.example.tudee.presentation.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme

@Composable
fun CategoryTaskComponent(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.default_task_title),
    description: String? = stringResource(R.string.default_task_description),
    priority: String = stringResource(R.string.default_priority),
    priorityBackgroundColor: Color = TudeeTheme.color.statusColors.yellowAccent,
    taskIcon: @Composable () -> Unit,
    priorityIcon: @Composable () -> Unit,
    dateIcon: (@Composable () -> Unit)? = null,
    dateText: String? = stringResource(R.string.default_date)
)
 {
    val colors = TudeeTheme.color
    val textStyles = TudeeTheme.textStyle

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp)
            .border(1.dp, TudeeTheme.color.surfaceHigh, RoundedCornerShape(16.dp))
            .background( TudeeTheme.color.surfaceHigh)
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
                            .background( TudeeTheme.color.surfaceHigh, RoundedCornerShape(100.dp))
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        dateIcon()
                        Text(
                            text = dateText,
                            style = textStyles.body.small.copy(
                                color = colors.textColors.body
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
                            color = TudeeTheme.color.surface
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
                style = textStyles.label.large.copy(
                    color = colors.textColors.body
                )
            )
            description?.let {
                Text(
                    text = it,
                    style = textStyles.label.small.copy(
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
@Preview(showBackground = true)
@Composable
fun CategoryTaskComponentPreview() {
    CategoryTaskComponent(
        title = stringResource(R.string.default_task_title),
        description = stringResource(R.string.default_task_description),
        priority = stringResource(R.string.default_priority),
        priorityBackgroundColor = TudeeTheme.color.statusColors.yellowAccent,
        taskIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_category_book_open),
                contentDescription = stringResource(R.string.task_icon_description),
                modifier = Modifier.size(32.dp),
                tint = TudeeTheme.color.statusColors.purpleAccent
            )
        },
        priorityIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_priority_medium),
                contentDescription = stringResource(R.string.priority_icon_description),
                modifier = Modifier.size(12.dp),
                tint = TudeeTheme.color.textColors.onPrimary
            )
        },
        dateIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = stringResource(R.string.date_icon_description),
                modifier = Modifier.size(12.dp),
                tint = TudeeTheme.color.textColors.body
            )
        },
        dateText = stringResource(R.string.default_date)
    )
}
