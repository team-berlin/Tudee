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
import io.mockk.core.ValueClassSupport.boxedValue
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
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
class AddTaskBottomSheetViewModelTest
{
    private lateinit var viewModel: AddTaskBottomSheetViewModel
    private lateinit var taskService: TaskService
    private lateinit var categoryService: TaskCategoryService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        taskService = mockk()
        categoryService = mockk()
        viewModel =  AddTaskBottomSheetViewModel(taskService, categoryService)
    }

    @Test
    fun `should update task title when onUpdateTaskTitle is called`() {
        // Given
        val newTitle = "new task"

        // When
        viewModel.onUpdateTaskTitle(newTitle)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newTitle, state.taskTitle)
    }

    @Test
    fun `should update task description when onUpdateTaskDescription is called`() {
        // Given
        val newDescription = "task descraption"

        // When
        viewModel.onUpdateTaskDescription(newDescription)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newDescription, state.taskDescription)
    }

    @Test
    fun `should update task due date when new date is selected`() {
        // Given
        val newDueDate = LocalDate(2025, 6, 21)

        // When
        viewModel.onUpdateTaskDueDate(newDueDate)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newDueDate.toString(), state.taskDueDate)
    }

    @Test
    fun `should update task priority when new priority is selected`() {
        // Given
        val newPriority = TaskPriority.HIGH

        // When
        viewModel.onSelectTaskPriority(newPriority)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newPriority, state.selectedTaskPriority)
    }

    @Test
    fun `should update category ID when new category is selected`() {
        // Given
        val newCategoryId = 1L

        // When
        viewModel.onSelectTaskCategory(newCategoryId)

        // Then
        val state = viewModel.uiState.value
        assertEquals(newCategoryId, state.selectedCategoryId)
    }

    @Test
    fun `should show bottom sheet when show button sheet is called`() {
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
    fun `should hide bottom sheet and reset state when hide button sheet is called`() {
        // Given
        viewModel.onUpdateTaskTitle("new title task")
        viewModel.onUpdateTaskDescription("new  description")
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
    fun `should fetch task info when task id is called`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "new title task",
            description = "new  description",
            priority = TaskPriority.HIGH,
            categoryId = 1L,
            status = TaskStatus.TODO,
            assignedDate = LocalDate(2025, 6, 21)
        )

        coEvery { taskService.getTaskById(taskId) } returns task
        coEvery { categoryService.getCategories() } throws Exception("Failed to fetch categories")


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
    fun `should create task and show snack bar when new task is added successfully`() = runTest{
        // Given
        val request = TaskCreationRequest(
            title = "new title task",
            description ="new  description task",
            priority = TaskPriority.HIGH,
            categoryId = 1L,
            assignedDate = LocalDate(2025, 6, 21),
            status = TaskStatus.TODO
        )
        coEvery { taskService.createTask(request) } returns Unit
        // When
        viewModel.onAddNewTaskClicked(request)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(null, state.snackBarMessage)
    }

    @Test
    fun `should show error snack bar when new task creation fails`() = runTest{
        // Give
        val request = TaskCreationRequest(
            title = "New Task",
            description = "Task Description",
            priority = TaskPriority.HIGH,
            categoryId = 1L,
            assignedDate = LocalDate(2025, 6, 21),
            status = TaskStatus.TODO
        )
        coEvery { taskService.createTask(request) } throws Exception("Creation failed")

        // When
        viewModel.onAddNewTaskClicked(request)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(null, state.snackBarMessage)
    }

    @Test
    fun `should edit task and show snac kbar when save is clicked successfully`() = runTest{
        // Given
        val task = Task(
            id = 1L,
            title = "edited Task",
            description = "edited description",
            priority = TaskPriority.MEDIUM,
            categoryId = 1L,
            status = TaskStatus.IN_PROGRESS,
            assignedDate = LocalDate(2025, 6, 21)
        )
        coEvery { taskService.editTask(task) } returns Unit

        // When
        viewModel.onSaveClicked(task)
        advanceUntilIdle()

        // Then
        coVerify { taskService.editTask(task) }
        val state = viewModel.uiState.value
        assertEquals(null, state.snackBarMessage)
    }

    @Test
    fun `should show date picker when date field is clicked`() {
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
    fun `should update due date when date picker is confirmed with valid millis`() {
        // Given
        val millis = 1624233600000L
        val expectedDate = LocalDate(2021, 6, 21)

        // When
        viewModel.onConfirmDatePicker(millis)

        // Then
        val state = viewModel.uiState.value
        assertEquals(expectedDate.toString(), state.taskDueDate)
    }

    @Test
    fun `should not update due date when date picker is confirmed with null millis`() {
        // Given
        val initialState = viewModel.uiState.value

        assertEquals(null, initialState.taskDueDate)

        // When
        viewModel.onConfirmDatePicker(null)

        // Then
        val state = viewModel.uiState.value
        assertEquals(null, state.taskDueDate)
    }

    @Test
    fun `should validate task as false when title is blank`() = runTest{
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
    fun `should reset state when cancel is clicked`() {
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