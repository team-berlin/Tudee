package com.example.tudee.presentation.viewmodel.uistate

data class SnackBar(
    val message: Int,
    val icon: Int,
    val description: String,
    val backgroundColor: androidx.compose.ui.graphics.Color,
    val tint: androidx.compose.ui.graphics.Color
)