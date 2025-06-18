package com.example.tudee.di

import androidx.room.Room
import com.example.tudee.AddNewTaskViewModel
import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.dao.TaskDao
import com.example.tudee.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module{
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
    viewModelOf(::AddNewTaskViewModel)}