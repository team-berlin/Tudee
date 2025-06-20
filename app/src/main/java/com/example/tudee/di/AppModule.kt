package com.example.tudee.di

import androidx.room.Room
import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.dao.TaskDao
import com.example.tudee.data.database.AppDatabase
import com.example.tudee.data.service.TaskCategoryServiceImpl
import com.example.tudee.data.service.TaskServiceImpl
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService

import org.koin.android.ext.koin.androidContext
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
    viewModelOf(::HomeViewModel)
    single<TaskService> { TaskServiceImpl(get()) }  // replace with your real implementation
    single<TaskCategoryService> { TaskCategoryServiceImpl(get()) }

    single< TaskCategoryService> {
        TaskCategoryServiceImpl(get())
    }


}
