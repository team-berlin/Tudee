package com.example.tudee.presentation.viewmodel.taskuistate

data class TaskDetailsUiState(
    val id: Long,
    val title: String,
    val description: String,
    val categoryIconRes: Int,
    val priority: String,
    val status:String,
)