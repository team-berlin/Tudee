package com.example.tudee.di

import com.example.tudee.presentation.categories.viewmodel.CategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel {
        CategoriesViewModel(
            taskCategoryService = get(),
            taskService = get()
        )
    }
}