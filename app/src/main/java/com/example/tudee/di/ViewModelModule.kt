package com.example.tudee.di

import com.example.tudee.presentation.screen.task_screen.viewmodel.TasksScreenViewModel
import com.example.tudee.presentation.screen.task_screen.viewmodel.AddTaskBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksScreenViewModel)
    viewModelOf(::AddTaskBottomSheetViewModel)
}