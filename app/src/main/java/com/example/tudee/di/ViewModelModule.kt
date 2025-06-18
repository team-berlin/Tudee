package com.example.tudee.di

import com.example.tudee.presentation.screen.task_screen.TasksScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksScreenViewModel)
}