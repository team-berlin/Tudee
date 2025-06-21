package com.example.tudee.presentation.screen.home.viewmodel

import androidx.annotation.DrawableRes

data class CategoryUiState(
    val id: String="0",
    val title:String="",
    @DrawableRes
    val image: Int? = null,
    val isPredefined: Boolean= false
)