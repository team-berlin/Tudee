package com.example.tudee.presentation.screen.category.tasks

import androidx.lifecycle.SavedStateHandle
import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.category.model.CategoryData
import com.example.tudee.presentation.screen.category.model.UiImage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CategoryTasksViewModelTest {

    private lateinit var taskService: TaskService
    private lateinit var taskCategoryService: TaskCategoryService
    private lateinit var viewModel: CategoryTasksViewModel
    private lateinit var categoryDao: TaskCategoryDao
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        taskService = mockk(relaxed = true)
        taskCategoryService = mockk(relaxed = true)
        categoryDao = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle().apply { set("categoryId", 1) }
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should load tasks when getTasksByCategoryId is called with valid category Id`() =
        runTest {
            // Given
            val categoryId = 1L
            val category =
                TaskCategory(
                    id = 1L,
                    title = "Test Category",
                    image = "Image.png",
                    isPredefined = true

                )
            val testTasks = listOf(
                Task(
                    id = 1,
                    title = "Task 1",
                    description = "Description 1",
                    priority = TaskPriority.HIGH,
                    categoryId = categoryId,
                    status = TaskStatus.DONE,
                    assignedDate = Clock.System.now()
                        .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date
                )
            )
            coEvery { taskCategoryService.getCategoryById(categoryId) } returns flowOf((category))
            coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(testTasks)
            // When
            viewModel.getTasksByCategoryId(categoryId)
            advanceUntilIdle()

            // Then
            assertThat(viewModel.categoryTasksUiState.value.loading).isFalse()
            assertThat(viewModel.categoryTasksUiState.value.categoryTasksUiModel?.tasks).isEqualTo(
                testTasks.map { it.toTaskUIModel() }
            )
        }

    @Test
    fun `should return error state when getTasksByCategoryId fails`() = runTest {
        // Given
        val categoryId = 1L
        val collectedEvents = mutableListOf<SnackBarEvent>()
        coEvery { taskService.getTasksByCategoryId(categoryId) } throws Exception("Error")

        viewModel.snackBarEvent
            .onEach { collectedEvents.add(it) }
            .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        viewModel.getTasksByCategoryId(categoryId)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.categoryTasksUiState.value.loading).isFalse()
        assertThat(collectedEvents.last()).isEqualTo(SnackBarEvent.ShowError)
    }

    @Test
    fun `should delete category when deleteCategory is called`() = runTest {
        // Given
        val categoryId = 1L
        val category = TaskCategory(
            id = categoryId,
            title = "Test Category",
            image = "Image.png",
            isPredefined = true
        )
        val testTasks = listOf(
            Task(
                id = 1,
                title = "Task 1",
                description = "Description 1",
                priority = TaskPriority.HIGH,
                categoryId = categoryId,
                status = TaskStatus.DONE,
                assignedDate = Clock.System.now()
                    .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date
            )
        )

        coEvery { taskCategoryService.getCategoryById(categoryId) } returns flowOf(category)
        coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(testTasks)

        viewModel.getTasksByCategoryId(categoryId)
        advanceUntilIdle()

        // When
        viewModel.deleteCategory()
        advanceUntilIdle()

        // Then
        coVerify { taskCategoryService.deleteCategory(categoryId) }
    }

    @Test
    fun `should update category when editCategory is called with valid data`() = runTest {
        // Given
        val categoryId = 1L
        val testCategory = TaskCategory(
            id = categoryId,
            title = "category",
            image = "image.png",
            isPredefined = false
        )

        val testTask = Task(
            id = 1,
            title = "Task 1",
            description = "Description 1",
            priority = TaskPriority.HIGH,
            categoryId = categoryId,
            status = TaskStatus.DONE,
            assignedDate = Clock.System.now()
                .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date
        )

        val updatedCategoryData = CategoryData(
            name = "Updated Category",
            uiImage = UiImage.External("picture.png")
        )

        coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(listOf(testTask))
        coEvery { taskCategoryService.getCategoryById(categoryId) } returns flowOf(testCategory)

        viewModel.getTasksByCategoryId(categoryId)
        advanceUntilIdle()

        assertNotNull(viewModel.categoryTasksUiState.value.categoryTasksUiModel)

        // When
        viewModel.editCategory(updatedCategoryData)
        advanceUntilIdle()

        // Then
        coVerify {
            taskCategoryService.editCategory(
                TaskCategory(
                    id = categoryId,
                    title = "Updated Category",
                    image = "picture.png",
                    isPredefined = false
                )
            )
        }
    }

    @Test
    fun `should filter tasks by status when getTasksByStatus is called`() = runTest {
        // Given
        val categoryId = 1L
        val testCategory = TaskCategory(
            id = categoryId,
            title = "Test Category",
            image = "test.png",
            isPredefined = false
        )

        val todoTask = Task(
            id = 1,
            title = "Task 1",
            description = "Description 1",
            priority = TaskPriority.HIGH,
            categoryId = categoryId,
            status = TaskStatus.IN_PROGRESS,
            assignedDate = Clock.System.now()
                .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date
        )
        val doneTask = Task(
            id = 2,
            title = "Task 2",
            description = "Description 1",
            priority = TaskPriority.HIGH,
            categoryId = categoryId,
            status = TaskStatus.TODO,
            assignedDate = Clock.System.now()
                .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date
        )
        val testTasks = listOf(todoTask, doneTask)

        coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(testTasks)
        coEvery { taskCategoryService.getCategoryById(categoryId) } returns flowOf(testCategory)
        coEvery { taskService.getTasksByStatus(TaskStatusUiState.TODO.toDomain()) } returns flowOf(
            listOf(todoTask)
        )
        coEvery { taskService.getTasksByStatus(TaskStatusUiState.DONE.toDomain()) } returns flowOf(
            listOf(doneTask)
        )

        viewModel.getTasksByCategoryId(categoryId)
        advanceUntilIdle()

        viewModel.getTasksByStatus(viewModel.categoryTasksUiState.value, 0) // IN_PROGRESS index
        advanceUntilIdle()

        // Then
        val finalState = viewModel.categoryTasksUiState.value
        finalState.categoryTasksUiModel?.run {
            assertThat(selectedTabIndex).isEqualTo(0) // IN_PROGRESS index
            assertThat(tasks[0].title).isEqualTo("Task 1")
        }
    }

}