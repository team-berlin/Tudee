package com.example.tudee.presentation.screen.task_screen.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.tudee.R
import com.example.tudee.designsystem.theme.TudeeTheme
import com.example.tudee.presentation.components.CategoryTaskComponent
import com.example.tudee.presentation.screen.task_screen.ui.NotTaskForTodayDialogue
import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState
import com.example.tudee.presentation.utils.toCategoryIcon
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TasksListContent(
    modifier: Modifier = Modifier,
    listOfTasks: List<TaskUiState>,
    onTaskCardClicked: (TaskUiState) -> Unit,
    onDeleteIconClick: (Long) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (listOfTasks.isEmpty()) {
            Box(
                Modifier.align(Alignment.Center)
            ) {
                NotTaskForTodayDialogue()
            }
        }
        AnimatedContent(
            targetState = listOfTasks,
            transitionSpec = {
                fadeIn(tween(500)) togetherWith fadeOut(tween(500))
            },
        ) { listOfTasks ->
            LazyColumn(
                modifier.padding(
                    start = 16.dp, end = 16.dp
                ), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(listOfTasks, key = { it.id }) { task ->
                    SwipeableCardWrapper(
                        onDeleteIconClick = { onDeleteIconClick(task.id) },
                    ) {
                        CategoryTaskComponent(
                            Modifier.clip(RoundedCornerShape(16.dp)),
                            title = task.title,
                            description = task.description,
                            priority = stringResource(task.priority.label),
                            priorityBackgroundColor = task.priority.containerColor,
                            taskIcon = {
                                Icon(
                                    painter = painterResource(task.categoryIcon.toCategoryIcon()),
                                    contentDescription = "Task Icon",
                                    modifier = Modifier.size(32.dp),
                                    tint = Color.Unspecified
                                )
                            },
                            onClick = { onTaskCardClicked(task) },
                            priorityIcon = painterResource(task.priority.icon),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeableCardWrapper(
    onDeleteIconClick: () -> Unit, content: @Composable () -> Unit
) {
    var hiddenIconWidth by remember { mutableFloatStateOf(0f) }
    var neededOffset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl

    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(TudeeTheme.color.statusColors.errorVariant),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(
            modifier = Modifier
                .onSizeChanged {
                    hiddenIconWidth = it.width.toFloat()
                }
                .padding(horizontal = 16.dp), onClick = {
            onDeleteIconClick()
        }) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.delete_ic),
                tint = TudeeTheme.color.statusColors.error,
                contentDescription = "delete icon"
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    val offsetX = if (isRtl) -neededOffset.value else -neededOffset.value
                    IntOffset(x = offsetX.roundToInt(), y = 0)
                }
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(hiddenIconWidth, isRtl) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val adjustedDrag = if (isRtl) -dragAmount else -dragAmount
                                val newOffset = if (isRtl) {
                                    (neededOffset.value - adjustedDrag)
                                        .coerceIn(0f, hiddenIconWidth)
                                } else {
                                    (neededOffset.value + adjustedDrag)
                                        .coerceIn(0f, hiddenIconWidth)
                                }
                                neededOffset.snapTo(newOffset)

                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                if (neededOffset.value >= hiddenIconWidth / 2) {
                                    neededOffset.animateTo(hiddenIconWidth)
                                } else {
                                    neededOffset.animateTo(0f)
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}

