package com.example.tudee.domain.entity

import androidx.compose.ui.graphics.painter.Painter
import com.example.tudee.R

data class TaskCategory(
    val id: Long,
    val title: String,
    val isPredefined: Boolean,
    val image: String,
)