package com.example.tudee.di

import com.example.tudee.presentation.screen.category.viewmodel.CategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel {
        CategoriesViewModel(
            taskCategoryService = get(),
            taskService = get(),
            taskDao = get()
        )
    }
}