package com.example.tudee.presentation.screen.categorytasks

sealed class SnackBarEvent {
    data object ShowError : SnackBarEvent()
}