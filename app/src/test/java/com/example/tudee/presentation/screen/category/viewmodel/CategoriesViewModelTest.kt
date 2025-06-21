package com.example.tudee.presentation.screen.category.viewmodel

import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.presentation.screen.category.mapper.toUiModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CategoriesViewModelTest {

    private lateinit var taskCategoryService: TaskCategoryService
    private lateinit var taskService: TaskService
    private lateinit var viewModel: CategoriesViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        taskCategoryService = mockk(relaxed = true)
        taskService = mockk(relaxed = true)
        viewModel = CategoriesViewModel(taskCategoryService, taskService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCategories should update state with categories and their task counts`() = runTest {
        // Given
        val categories = listOf(
            TaskCategory(id = 1, title = "Work", isPredefined = false, image = "work_icon.png"),
            TaskCategory(id = 2, title = "Code", isPredefined = false, image = "ic_code.png")
        )

        val categoriesFlow = flowOf(categories)
        coEvery { taskCategoryService.getCategories() } returns categoriesFlow
        coEvery { taskService.getTasksCountByCategoryId(1) } returns 3L
        coEvery { taskService.getTasksCountByCategoryId(2) } returns 5L

        // When
        viewModel.loadCategories()
        advanceUntilIdle()

        // Then
        val expectedUiModels = categories.mapIndexed { index, category ->
            category.toUiModel(if (index == 0) 3 else 5)
        }

        Truth.assertThat(viewModel.uiState.value.categories).isEqualTo(expectedUiModels)
        Truth.assertThat(viewModel.uiState.value.error).isNull()
    }

    @Test
    fun `loadCategories should update error state when exception occurs`() = runTest {
        // Given
        coEvery { taskCategoryService.getCategories() } throws Exception("Network error")

        // When
        viewModel.loadCategories()
        advanceUntilIdle()

        // Then
        Truth.assertThat(viewModel.uiState.value.error).isEqualTo("Network error")
    }

    @Test
    fun `addCategory should create category when name is valid`() = runTest {
        // Given
        coEvery {
            taskCategoryService.createCategory(any())
        } just runs
        coEvery { taskCategoryService.getCategories() } returns flowOf(emptyList())
        coEvery { taskService.getTasksCountByCategoryId(any()) } returns 0L

        // When
        viewModel.addCategory("Test Category", "test_icon.png")
        advanceUntilIdle()

        // Then
        coVerify {
            taskCategoryService.createCategory(
                match {
                    it.title == "Test Category" && it.image == "test_icon.png" && it.isPredefined.not()
                })
        }
    }

    @Test
    fun `addCategory should not create category when name is blank`() = runTest {
        // When
        viewModel.addCategory("", "test_icon.png")

        advanceUntilIdle()

        // Then
        Truth.assertThat(viewModel.uiState.value.error).isEqualTo("Category name cannot be empty")
        coVerify(exactly = 0) { taskCategoryService.createCategory(any()) }
    }

}