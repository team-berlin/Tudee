package com.example.tudee.presentation.screen.task_screen.viewmodel

import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.presentation.viewmodel.TaskBottomSheetViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskBottomSheetViewModelTest {
    private lateinit var viewModel: TaskBottomSheetViewModel
    private lateinit var taskService: TaskService
    private lateinit var categoryService: TaskCategoryService
    private val testDispatcher = StandardTestDispatcher()

    private val dummyCategories = listOf(
        TaskCategory(id = 1L, title = "Shopping", isPredefined = false, image = "ic_shopping"),
        TaskCategory(id = 2L, title = "Work", isPredefined = false, image = "ic_work")
    )

    private val dummyTask = Task(
        id = 1L,
        title = "Test Task",
        description = "Test Description",
        priority = TaskPriority.HIGH,
        categoryId = 1L,
        status = TaskStatus.TODO,
        assignedDate = LocalDate(2025, 6, 25)
    )

    private val dummyTaskCreationRequest = TaskCreationRequest(
        title = "New Task",
        description = "New Description",
        priority = TaskPriority.MEDIUM,
        categoryId = 2L,
        assignedDate = LocalDate(2025, 6, 26),
        status =  TaskStatus.TODO
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        taskService = mockk(relaxed = true)
        categoryService = mockk(relaxed = true)
        coEvery { categoryService.getCategories() } returns flowOf(dummyCategories)
        viewModel = TaskBottomSheetViewModel(taskService, categoryService)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load categories`() = runTest {
        // When
        val state = viewModel.uiState.value
        // Then
        assertThat(state.categories).isEqualTo(dummyCategories)
    }

    @Test
    fun `init should set taskPriorities correctly`() = runTest {
        // When
        val state = viewModel.uiState.value
        // Then
        assertThat(state.taskPriorities).containsExactly(
            TaskPriority.HIGH,
            TaskPriority.MEDIUM,
            TaskPriority.LOW
        ).inOrder()
    }

    @Test
    fun `isTaskValid should return false when fields are incomplete`() = runTest {
        // Given
        viewModel.onUpdateTaskTitle("Test")
        viewModel.onUpdateTaskDescription("Description")
        // When
        advanceUntilIdle()
        // Then
        assertThat(viewModel.isTaskValid.value).isFalse()
    }

    @Test
    fun `isTaskValid should return false when title is blank`() = runTest {
        // Given
        viewModel.onUpdateTaskTitle("")
        viewModel.onUpdateTaskDescription("Description")
        viewModel.onSelectTaskPriority(TaskPriority.HIGH)
        viewModel.onSelectTaskCategory(1L)
        // When
        advanceUntilIdle()
        // Then
        assertThat(viewModel.isTaskValid.value).isFalse()
    }

    @Test
    fun `isTaskValid should return false when description is blank`() = runTest {
        // Given
        viewModel.onUpdateTaskTitle("Test")
        viewModel.onUpdateTaskDescription("")
        viewModel.onSelectTaskPriority(TaskPriority.HIGH)
        viewModel.onSelectTaskCategory(1L)
        // When
        advanceUntilIdle()
        // Then
        assertThat(viewModel.isTaskValid.value).isFalse()
    }

    @Test
    fun `onUpdateTaskTitle should update taskTitle`() = runTest {
        // Given
        val newTitle = "New Title"
        // When
        viewModel.onUpdateTaskTitle(newTitle)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.taskTitle).isEqualTo(newTitle)
    }

    @Test
    fun `onUpdateTaskDescription should update taskDescription`() = runTest {
        // Given
        val newDescription = "New Description"
        // When
        viewModel.onUpdateTaskDescription(newDescription)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.taskDescription).isEqualTo(newDescription)
    }

    @Test
    fun `onUpdateTaskDueDate should update taskDueDate`() = runTest {
        // Given
        val date = LocalDate(2025, 6, 25)
        // When
        viewModel.onUpdateTaskDueDate(date)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.taskDueDate).isEqualTo(date.toString())
    }

    @Test
    fun `onSelectTaskPriority should update selectedTaskPriority`() = runTest {
        // Given
        val priority = TaskPriority.MEDIUM
        // When
        viewModel.onSelectTaskPriority(priority)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.selectedTaskPriority).isEqualTo(priority)
    }

    @Test
    fun `onSelectTaskCategory should update selectedCategoryId`() = runTest {
        // Given
        val categoryId = 2L
        // When
        viewModel.onSelectTaskCategory(categoryId)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.selectedCategoryId).isEqualTo(categoryId)
    }

    @Test
    fun `showButtonSheet should set isButtonSheetVisible to true`() = runTest {
        // When
        viewModel.showButtonSheet()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isButtonSheetVisible).isTrue()
    }

    @Test
    fun `hideButtonSheet should reset state and set isButtonSheetVisible to false`() = runTest {
        // Given
        viewModel.onUpdateTaskTitle("Test")
        viewModel.onUpdateTaskDescription("Description")
        viewModel.onSelectTaskPriority(TaskPriority.HIGH)
        viewModel.onSelectTaskCategory(1L)
        viewModel.onUpdateTaskDueDate(LocalDate(2025, 6, 25))
        viewModel.showButtonSheet()
        // When
        viewModel.hideButtonSheet()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isButtonSheetVisible).isFalse()
        assertThat(state.isEditMode).isFalse()
        assertThat(state.taskTitle).isEmpty()
        assertThat(state.taskDescription).isEmpty()
        assertThat(state.selectedTaskPriority).isNull()
        assertThat(state.selectedCategoryId).isNull()
        assertThat(state.taskDueDate).isNull()
        assertThat(state.snackBarMessage).isNull()
    }

    @Test
    fun `getTaskInfoById should populate state with task data`() = runTest {
        // Given
        coEvery { taskService.getTaskById(1L) } returns dummyTask
        // When
        viewModel.getTaskInfoById(1L)
        advanceUntilIdle()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isEditMode).isTrue()
        assertThat(state.taskId).isEqualTo(1L)
        assertThat(state.taskStatus).isEqualTo(TaskStatus.TODO)
        assertThat(state.taskTitle).isEqualTo("Test Task")
        assertThat(state.taskDescription).isEqualTo("Test Description")
        assertThat(state.selectedTaskPriority).isEqualTo(TaskPriority.HIGH)
        assertThat(state.selectedCategoryId).isEqualTo(1L)
        assertThat(state.taskDueDate).isEqualTo(dummyTask.assignedDate.toString())
    }

    @Test
    fun `onDateFieldClicked should show date picker`() = runTest {
        // When
        viewModel.onDateFieldClicked()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isDatePickerVisible).isTrue()
    }

    @Test
    fun `onDismissDatePicker should hide date picker`() = runTest {
        // Given&When
        viewModel.onDateFieldClicked()
        viewModel.onDismissDatePicker()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isDatePickerVisible).isFalse()
    }

    @Test
    fun `onConfirmDatePicker should update taskDueDate`() = runTest {
        // Given
        val millis = 1752556800000L
        // When
        viewModel.onConfirmDatePicker(millis)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.taskDueDate).isEqualTo("2025-07-15")
    }

    @Test
    fun `onConfirmDatePicker with null millis should not update taskDueDate`() = runTest {
        // Given
        val initialState = viewModel.uiState.value
        // When
        viewModel.onConfirmDatePicker(null)
        // Then
        val state = viewModel.uiState.value
        assertThat(state.taskDueDate).isEqualTo(initialState.taskDueDate)
    }

    @Test
    fun `onCancelClicked should call hideButtonSheet`() = runTest {
        // Given&When
        viewModel.onUpdateTaskTitle("Test")
        viewModel.onCancelClicked()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.isButtonSheetVisible).isFalse()
        assertThat(state.taskTitle).isEmpty()
    }

    @Test
    fun `updateTaskStatusToDone should handle exception and show error snackbar`() = runTest {
        // Given
        coEvery { taskService.getTaskById(1L) } throws Exception("Task not found")
        // When
        viewModel.updateTaskStatusToDone(1L)
        advanceUntilIdle()
        // Then
        val state = viewModel.uiState.value
        assertThat(state.snackBarMessage).isFalse()
    }
}