package com.example.tudee.di

import com.example.tudee.presentation.screen.task_screen.viewmodel.TasksScreenViewModel
import com.example.tudee.presentation.themeViewModel.ThemeViewModel
import com.example.tudee.presentation.viewmodel.TaskBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val viewModelModule = module {
    viewModelOf(::TasksScreenViewModel)
    viewModelOf(::TaskBottomSheetViewModel)
    single { ThemeViewModel(get()) }
}