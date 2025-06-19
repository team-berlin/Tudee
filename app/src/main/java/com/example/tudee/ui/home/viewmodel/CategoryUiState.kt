package com.example.tudee.ui.home.viewmodel

import androidx.annotation.DrawableRes

data class CategoryUiState(
    val id: String="",
    val title:String="",
    @DrawableRes
    val image: Int? = null,
    val isPredefined: Boolean= false
)