package com.example.tudee.di

import androidx.room.Room
import com.example.tudee.data.dao.AppEntryDao
import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.dao.TaskDao
import com.example.tudee.data.database.AppDatabase
import com.example.tudee.data.service.TaskCategoryServiceImpl
import com.example.tudee.data.service.TaskServiceImpl
import com.example.tudee.domain.AppEntry
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.data.service.TaskCategoryServiceImpl
import com.example.tudee.data.service.TaskServiceImpl
import com.example.tudee.domain.AppEntry
import com.example.tudee.domain.AppEntryImpl
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.presentation.screen.category.tasks.CategoryTasksViewModel
import com.example.tudee.presentation.screen.onboarding.OnBoardingViewModel
import com.example.tudee.data.service.TaskCategoryServiceImpl
import com.example.tudee.data.service.TaskServiceImpl
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.ui.home.viewmodel.HomeViewModel

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val appModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "app_database"
        ).build()
    }
    single<TaskDao> {
        get<AppDatabase>().taskDao()
    }
    single<TaskCategoryDao> {
        get<AppDatabase>().taskCategoryDao()
    }
    viewModel<HomeViewModel>{
        HomeViewModel(get())
    }
    single<TaskService> { TaskServiceImpl(get()) }  // replace with your real implementation
    single<TaskCategoryService> { TaskCategoryServiceImpl(get()) }

    single< TaskCategoryService> {
        TaskCategoryServiceImpl(get())
    }

    single<CategoryTasksViewModel> { CategoryTasksViewModel(get(), get()) }
    single<OnBoardingViewModel> { OnBoardingViewModel(get()) }

    single<AppEntryDao> { get<AppDatabase>().appEntryDao() }
    single<AppEntry> { AppEntryImpl(get()) }

}