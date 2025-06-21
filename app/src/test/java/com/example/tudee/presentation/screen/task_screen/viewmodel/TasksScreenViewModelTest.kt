package com.example.tudee.presentation.screen.task_screen.viewmodel

import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.After
import org.junit.Before

class TasksScreenViewModelTest {
    private lateinit var tasksScreenViewModel : TasksScreenViewModel
    private lateinit var taskService: TaskService
    private lateinit var categoryService: TaskCategoryService

    @Before
    fun setup() {
        tasksScreenViewModel = mockk()
        taskService = mockk()
        categoryService = mockk()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}