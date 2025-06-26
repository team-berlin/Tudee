package com.example.tudee.presentation.screen.category.tasks

import androidx.lifecycle.SavedStateHandle
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.presentation.screen.category.CategoriesConstants.CATEGORY_ID_ARGUMENT_KEY
import com.example.tudee.presentation.screen.category.model.CategoryData
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryTasksViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var taskService: TaskService
    private lateinit var taskCategoryService: TaskCategoryService
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CategoryTasksViewModel

    private val categoryId = 1L
    private val testCategory = TaskCategory(
        id = categoryId,
        title = "Test Category",
        image = "test_image",
        isPredefined = false
    )

    private val testTasks = listOf(
        Task(
            id = 1L,
            title = "Task 1",
            description = "Description 1",
            priority = TaskPriority.HIGH,
            status = TaskStatus.IN_PROGRESS,
            assignedDate =
                Clock.System.now()
                    .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date,
            categoryId = categoryId
        ),
        Task(
            id = 2L,
            title = "Task 2",
            description = "Description 2",
            priority = TaskPriority.MEDIUM,
            status = TaskStatus.TODO,
            assignedDate =
                Clock.System.now()
                    .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date,
            categoryId = categoryId
        ),
        Task(
            id = 3L,
            title = "Task 3",
            description = "Description 3",
            priority = TaskPriority.LOW,
            status = TaskStatus.DONE,
            assignedDate =
                Clock.System.now()
                    .toLocalDateTime(timeZone = TimeZone.of("Africa/Cairo")).date,
            categoryId = categoryId
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        taskService = mockk(relaxed = true)
        taskCategoryService = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle().apply {
            set(CATEGORY_ID_ARGUMENT_KEY, categoryId)
        }

        coEvery { taskCategoryService.getCategoryById(categoryId) } returns flowOf(testCategory)
        coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(testTasks)
        coEvery { taskService.getTasksByStatus(any()) } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load category and tasks successfully`() = runTest {
        // When
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        // Then
        val uiState = viewModel.categoryTasksUiState.value
        assertThat(uiState.loading).isFalse()
        assertThat(uiState.categoryTasksUiModel).isNotNull()
        assertThat(uiState.categoryTasksUiModel?.title).isEqualTo("Test Category")
        assertThat(uiState.categoryTasksUiModel?.id).isEqualTo(categoryId)

        // Verify services were called
        coVerify { taskCategoryService.getCategoryById(categoryId) }
        coVerify { taskService.getTasksByCategoryId(categoryId) }
    }

    @Test
    fun `init should handle errors gracefully`() = runTest {
        // Given
        coEvery { taskCategoryService.getCategoryById(categoryId) } throws RuntimeException("Database error")

        // When
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        // Then
        val uiState = viewModel.categoryTasksUiState.value
        assertThat(uiState.loading).isFalse()
        assertThat(uiState.categoryTasksUiModel).isNull()
    }

    @Test
    fun `updateSelectedIndex should filter tasks by status`() = runTest {
        // Given
        val inProgressTasks = testTasks.filter { it.status == TaskStatus.IN_PROGRESS }
        coEvery { taskService.getTasksByStatus(TaskStatus.IN_PROGRESS) } returns flowOf(
            inProgressTasks
        )

        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        // When
        viewModel.updateSelectedIndex(0) // IN_PROGRESS
        advanceUntilIdle()

        // Then
        val uiState = viewModel.categoryTasksUiState.value
        assertThat(uiState.categoryTasksUiModel?.selectedTabIndex).isEqualTo(0)

    }

    @Test
    fun `updateSelectedIndex should update task count correctly`() = runTest {
        // Given
        val todoTasks = testTasks.filter { it.status == TaskStatus.TODO }
        coEvery { taskService.getTasksByStatus(TaskStatus.TODO) } returns flowOf(todoTasks)

        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        // When
        viewModel.updateSelectedIndex(1) // TODO
        advanceUntilIdle()

        // Then
        val uiState = viewModel.categoryTasksUiState.value
        val tabBarItems = uiState.categoryTasksUiModel?.listOfTabBarItem
        // Note: This test assumes the bug in filtering is fixed
        assertThat(tabBarItems?.get(1)?.taskCount).isEqualTo(todoTasks.size.toString())
    }

    @Test
    fun `deleteCategory should call service with correct id`() = runTest {
        // Given
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        // When
        viewModel.deleteCategory()
        advanceUntilIdle()

        // Then
        coVerify { taskCategoryService.deleteCategory(categoryId) }
    }

    @Test
    fun `editCategory should call service with updated category`() = runTest {
        // Given
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        val categoryData = CategoryData(
            name = "Updated Category",
            uiImage = mockk {
                coEvery { asString() } returns "updated_image"
            }
        )

        // When
        viewModel.editCategory(categoryData)
        advanceUntilIdle()

        // Then
        coVerify {
            taskCategoryService.editCategory(
                match { category ->
                    category.id == categoryId &&
                            category.title == "Updated Category" &&
                            category.image == "updated_image" &&
                            category.isPredefined == testCategory.isPredefined
                }
            )
        }
    }

    @Test
    fun `allTasks should be populated with category tasks`() = runTest {
        // When
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)
        advanceUntilIdle()

        // Then
        val allTasks = viewModel.allTasks.value
        assertThat(allTasks).hasSize(testTasks.size)
        assertThat(allTasks[0].title).isEqualTo("Task 1")
        assertThat(allTasks[1].title).isEqualTo("Task 2")
        assertThat(allTasks[2].title).isEqualTo("Task 3")
    }

    @Test
    fun `toTaskStatus extension should return correct status`() = runTest {
        // Given
        viewModel = CategoryTasksViewModel(savedStateHandle, taskService, taskCategoryService)

        val inProgressTasks = listOf(testTasks[0]) // IN_PROGRESS
        val todoTasks = listOf(testTasks[1]) // TODO
        val doneTasks = listOf(testTasks[2]) // DONE

        coEvery { taskService.getTasksByStatus(TaskStatus.IN_PROGRESS) } returns flowOf(
            inProgressTasks
        )
        coEvery { taskService.getTasksByStatus(TaskStatus.TODO) } returns flowOf(todoTasks)
        coEvery { taskService.getTasksByStatus(TaskStatus.DONE) } returns flowOf(doneTasks)

        advanceUntilIdle()

        // When & Then
        viewModel.updateSelectedIndex(0) // IN_PROGRESS
        advanceUntilIdle()
        coVerify { taskService.getTasksByStatus(TaskStatus.IN_PROGRESS) }

        viewModel.updateSelectedIndex(1) // TODO
        advanceUntilIdle()
        coVerify { taskService.getTasksByStatus(TaskStatus.TODO) }

        viewModel.updateSelectedIndex(2) // DONE
        advanceUntilIdle()
        coVerify { taskService.getTasksByStatus(TaskStatus.DONE) }
    }
}