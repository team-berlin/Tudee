package com.example.tudee.presentation.viewmodel

import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddTaskBottomSheetViewModelTest {
    private lateinit var viewModel: AddTaskBottomSheetViewModel
    private lateinit var taskService: TaskService
    private lateinit var categoryService: TaskCategoryService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        taskService = mockk()
        categoryService = mockk()
        every { categoryService.getCategories() } returns flowOf(
            listOf(TaskCategory(id = 1L, title = "Work", isPredefined = false, image = ""))
        )
        viewModel = AddTaskBottomSheetViewModel(taskService, categoryService)
    }

    @Test
    fun `updateTaskTitle should update task title when onUpdateTaskTitle is called`() {
        // Given
        val newTitle = "new task"

        // When
        viewModel.onUpdateTaskTitle(newTitle)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newTitle, state.taskTitle)
    }

    @Test
    fun `updateTaskDescription should update task description when onUpdateTaskDescription is called`() {
        // Given
        val newDescription = "task description"

        // When
        viewModel.onUpdateTaskDescription(newDescription)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newDescription, state.taskDescription)
    }

    @Test
    fun `updateTaskDueDate should update task due date when new date is selected`() {
        // Given
        val newDueDate = LocalDate(2025, 6, 21)

        // When
        viewModel.onUpdateTaskDueDate(newDueDate)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newDueDate.toString(), state.taskDueDate)
    }

    @Test
    fun `selectTaskPriority should update task priority when new priority is selected`() {
        // Given
        val newPriority = TaskPriority.HIGH

        // When
        viewModel.onSelectTaskPriority(newPriority)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newPriority, state.selectedTaskPriority)
    }

    @Test
    fun `selectTaskCategory should update category ID when new category is selected`() {
        // Given
        val newCategoryId = 1L

        // When
        viewModel.onSelectTaskCategory(newCategoryId)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newCategoryId, state.selectedCategoryId)
    }

    @Test
    fun `showButtonSheet should show bottom sheet when showButtonSheet is called`() {
        // Given
        val initialState = viewModel.uiState.value
        assertFalse(initialState.isButtonSheetVisible)

        // When
        viewModel.showButtonSheet()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state.isButtonSheetVisible)
    }

    @Test
    fun `hideButtonSheet should hide bottom sheet and reset state when hideButtonSheet is called`() {
        // Given
        viewModel.onUpdateTaskTitle("new title task")
        viewModel.onUpdateTaskDescription("new description")
        viewModel.showButtonSheet()

        // When
        viewModel.hideButtonSheet()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isButtonSheetVisible)
        assertFalse(state.isEditMode)
        assertEquals("", state.taskTitle)
        assertEquals("", state.taskDescription)
        assertEquals(null, state.selectedTaskPriority)
        assertEquals(null, state.selectedCategoryId)
        assertEquals(null, state.taskDueDate)
        assertEquals(null, state.snackBarMessage)
    }

    @Test
    fun `getTaskInfoById should fetch task info when task ID is provided`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "new title task",
            description = "new description",
            priority = TaskPriority.HIGH,
            categoryId = 1L,
            status = TaskStatus.TODO,
            assignedDate = LocalDate(2025, 6, 21)
        )

        coEvery { taskService.getTaskById(taskId) } returns task

        // When
        viewModel.getTaskInfoById(taskId)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state.isEditMode)
        assertEquals(taskId, state.taskId)
        assertEquals(task.title, state.taskTitle)
        assertEquals(task.description, state.taskDescription)
        assertEquals(task.priority, state.selectedTaskPriority)
        assertEquals(task.categoryId, state.selectedCategoryId)
        assertEquals(task.assignedDate.toString(), state.taskDueDate)
    }

    @Test
    fun `onDateFieldClicked should show date picker when date field is clicked`() {
        // Given
        val initialState = viewModel.uiState.value
        assertFalse(initialState.isDatePickerVisible)

        // When
        viewModel.onDateFieldClicked()

        // Then
        val state = viewModel.uiState.value
        assertTrue(state.isDatePickerVisible)
    }

    @Test
    fun `confirmDatePicker should update due date when date picker is confirmed with valid millis`() {
        // Given
        val millis = 1624233600000L // June 21, 2021
        val expectedDate = LocalDate(2021, 6, 21)

        // When
        viewModel.onConfirmDatePicker(millis)

        // Then
        val state = viewModel.uiState.value
        assertEquals(expectedDate.toString(), state.taskDueDate)
    }

    @Test
    fun `validateTask should return false when title is blank`() = runTest {
        // Given
        viewModel.onUpdateTaskTitle("")
        viewModel.onUpdateTaskDescription("Test Description")
        viewModel.onSelectTaskPriority(TaskPriority.HIGH)
        viewModel.onSelectTaskCategory(1L)
        advanceUntilIdle()

        // Then
        val isValid = viewModel.isTaskValid.first()
        assertFalse(isValid)
    }

    @Test
    fun `cancelClicked should reset state when cancel is clicked`() {
        // Given
        viewModel.onUpdateTaskTitle("Test Task")
        viewModel.onUpdateTaskDescription("Test Description")
        viewModel.showButtonSheet()

        // When
        viewModel.onCancelClicked()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isButtonSheetVisible)
        assertFalse(state.isEditMode)
        assertEquals("", state.taskTitle)
        assertEquals("", state.taskDescription)
        assertEquals(null, state.selectedTaskPriority)
        assertEquals(null, state.selectedCategoryId)
        assertEquals(null, state.taskDueDate)
        assertEquals(null, state.snackBarMessage)
    }
}