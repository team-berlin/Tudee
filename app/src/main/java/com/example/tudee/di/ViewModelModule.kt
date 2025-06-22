package com.example.tudee.di

import androidx.lifecycle.SavedStateHandle
import com.example.tudee.presentation.screen.task_screen.viewmodel.TasksScreenViewModel
import com.example.tudee.presentation.screen.task_screen.viewmodel.AddTaskBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (handle: SavedStateHandle) ->
        TasksScreenViewModel(
            taskService = get(),
            categoryService = get(),
            savedStateHandle = handle
        )
    }
    viewModelOf(::AddTaskBottomSheetViewModel)
}