//package com.example.tudee.presentation.screen.category.viewmodel
//
//import com.example.tudee.domain.TaskCategoryService
//import com.example.tudee.domain.TaskService
//import com.example.tudee.domain.entity.TaskCategory
//import com.example.tudee.presentation.screen.category.mapper.toUiModel
//import com.google.common.truth.Truth
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.just
//import io.mockk.mockk
//import io.mockk.runs
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//
//@OptIn(ExperimentalCoroutinesApi::class)
//@RunWith(JUnit4::class)
//class CategoriesViewModelTest {
//
//    private lateinit var taskCategoryService: TaskCategoryService
//    private lateinit var taskService: TaskService
//    private lateinit var viewModel: CategoriesViewModel
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//
//        taskCategoryService = mockk(relaxed = true)
//        taskService = mockk(relaxed = true)
//        viewModel = CategoriesViewModel(taskCategoryService, taskService)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `loadCategories should update state with categories and their task counts`() = runTest {
//        // Given
//        val categories = listOf(
//            TaskCategory(id = 1, title = "Work", isPredefined = false, image = "ic_work.png"),
//            TaskCategory(id = 2, title = "Code", isPredefined = false, image = "ic_code.png")
//        )
//
//        val taskCounts = mapOf(
//            1L to 3L,
//            2L to 5L
//        )
//
//        coEvery { taskCategoryService.getCategories() } returns flowOf(categories)
//        taskCounts.forEach { (id, count) ->
//            coEvery { taskService.getTasksCountByCategoryId(id) } returns count
//        }
//
//        // When
//        viewModel.loadCategories()
//        advanceUntilIdle()
//
//        // Then
//        val expectedUiModels = categories.map { category ->
//            category.toUiModel(taskCounts.getOrDefault(category.id, -1).toInt())
//        }
//
//        Truth.assertThat(viewModel.uiState.value.categories).isEqualTo(expectedUiModels)
//        Truth.assertThat(viewModel.uiState.value.error).isNull()
//    }
//
//    @Test
//    fun `loadCategories should set error message when exception has message`() = runTest {
//        // Given
//        coEvery { taskCategoryService.getCategories() } throws Exception("Failed to fetch categories")
//
//        // When
//        viewModel.loadCategories()
//        advanceUntilIdle()
//
//        // Then
//        Truth.assertThat(viewModel.uiState.value.error).isEqualTo("Failed to fetch categories")
//    }
//
//    @Test
//    fun `loadCategories should set unknown error message when exception has no message`() =
//        runTest {
//            // Given
//            coEvery { taskCategoryService.getCategories() } throws Exception()
//
//            // When
//            viewModel.loadCategories()
//            advanceUntilIdle()
//
//            // Then
//            Truth.assertThat(viewModel.uiState.value.error).isEqualTo("Unknown error")
//        }
//
//    @Test
//    fun `addCategory should create category when name is valid`() = runTest {
//        // Given
//        coEvery { taskCategoryService.createCategory(any()) } just runs
//        coEvery { taskCategoryService.getCategories() } returns flowOf(emptyList())
//        coEvery { taskService.getTasksCountByCategoryId(any()) } returns 0L
//
//        // When
//        viewModel.addCategory(name = "Test Category", iconUrl = "ic_test.png")
//        advanceUntilIdle()
//
//        // Then
//        coVerify {
//            taskCategoryService.createCategory(
//                match {
//                    it.title == "Test Category" && it.image == "ic_test.png" && it.isPredefined.not()
//                }
//            )
//        }
//    }
//
//    @Test
//    fun `addCategory should not create category when name is blank`() = runTest {
//        // When
//        viewModel.addCategory("", "ic_test.png")
//
//        advanceUntilIdle()
//
//        // Then
//        Truth.assertThat(viewModel.uiState.value.error).isEqualTo("Category name cannot be empty")
//        coVerify(exactly = 0) { taskCategoryService.createCategory(any()) }
//    }
//
//    @Test
//    fun `addCategory should set error message when exception has message`() = runTest {
//        // Given
//        val errorMessage = "Failed to create category"
//        coEvery { taskCategoryService.createCategory(any()) } throws Exception(errorMessage)
//
//        // When
//        viewModel.addCategory("Test Category", "ic_test.png")
//        advanceUntilIdle()
//
//        // Then
//        Truth.assertThat(viewModel.uiState.value.error).isEqualTo(errorMessage)
//    }
//
//    @Test
//    fun `addCategory should set unknown error message when exception has no message`() = runTest {
//        // Given
//        coEvery { taskCategoryService.createCategory(any()) } throws Exception()
//
//        // When
//        viewModel.addCategory("Test Category", "ic_test.png")
//        advanceUntilIdle()
//
//        // Then
//        Truth.assertThat(viewModel.uiState.value.error).isEqualTo("Unknown error")
//    }
//
//}