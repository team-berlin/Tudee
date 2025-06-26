package com.example.tudee.presentation.screen.category.tasks

sealed class SnackBarEvent {
    data object ShowError : SnackBarEvent()
    data object ShowSuccess : SnackBarEvent()
}