//package com.example.tudee.presentation.screen.task_screen.viewmodel
//
//import androidx.lifecycle.SavedStateHandle
//import com.example.tudee.domain.TaskCategoryService
//import com.example.tudee.domain.TaskService
//import com.example.tudee.domain.entity.Task
//import com.example.tudee.domain.entity.TaskPriority
//import com.example.tudee.domain.entity.TaskStatus
//import com.example.tudee.presentation.screen.task_screen.mappers.TaskPriorityUiState
//import com.example.tudee.presentation.screen.task_screen.mappers.TaskStatusUiState
//import com.example.tudee.presentation.screen.task_screen.mappers.taskToTaskUiState
//import com.example.tudee.presentation.screen.task_screen.mappers.toDomain
//import com.example.tudee.presentation.screen.task_screen.ui_states.DateCardUiState
//import com.example.tudee.presentation.screen.task_screen.ui_states.TaskDetailsUiState
//import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState
//import com.google.common.truth.Truth.assertThat
//import io.mockk.clearAllMocks
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.emptyFlow
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import kotlinx.datetime.LocalDate
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertTrue
//import org.junit.Before
//import org.junit.Test
//import java.time.format.TextStyle
//import java.util.Locale
//
//class TasksScreenViewModelTest {
//    private lateinit var tasksScreenViewModel: TasksScreenViewModel
//    private lateinit var taskService: TaskService
//    private lateinit var categoryService: TaskCategoryService
//    private val testDispatcher = StandardTestDispatcher()
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Before
//    fun setup() {
//        val savedStateHandle = SavedStateHandle().apply {
//            set("status", 1)
//        }
//        Dispatchers.setMain(testDispatcher)
//        taskService = mockk()
//        categoryService = mockk()
//        tasksScreenViewModel = TasksScreenViewModel(
//            taskService = taskService,
//            categoryService = categoryService,
//            savedStateHandle = savedStateHandle)
//    }
//
//
//    @Test
//    fun `should showBottomSheet when onDeleteIconClicked is called`() {
//        //when
//        tasksScreenViewModel.onDeleteIconClicked(1)
//        //then
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertTrue(state.isBottomSheetVisible)
//        assertEquals(1L, state.idOfTaskToBeDeleted)
//    }
//
//    @Test
//    fun `should hide bottom sheet when confirmDelete clicked `() {
//        //when
//        tasksScreenViewModel.onConfirmDelete()
//        //then
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertFalse(state.isBottomSheetVisible)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `should delete task when confirmDelete clicked`() = runTest {
//        val initialTasks = dummyTasks
//        val taskToDelete = initialTasks[0]
//        val updatedTasks = initialTasks - taskToDelete
//        val tasksFlow = MutableStateFlow(initialTasks)
//        coEvery { categoryService.getCategoryIconById(any()) } returns "test"
//
//        coEvery { taskService.getTasksByStatusAndDate(any(), any()) } returns tasksFlow
//        coEvery { taskService.deleteTask(taskToDelete.id) } answers {
//            tasksFlow.value = updatedTasks
//        }
//
//        coEvery { tasksScreenViewModel.getCategoryIconById(any()) } returns "test"
//
//        tasksScreenViewModel.onTabSelected(1)
//        advanceUntilIdle()
//
//        tasksScreenViewModel.onDeleteIconClicked(taskToDelete.id)
//        tasksScreenViewModel.onConfirmDelete()
//
//        advanceUntilIdle()
//        val listAfterDeletion = tasksScreenViewModel.taskScreenUiState.value.listOfTasksUiState
//        assertThat(listAfterDeletion).doesNotContain(taskToDelete.taskToTaskUiState("test"))
//
//    }
//
//    @Test
//    fun `should showBottomSheet when onFloatingActionClicked is called`() {
//        //when
//        tasksScreenViewModel.onFloatingActionClicked()
//        //then
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertTrue(state.isAddBottomSheetVisible)
//    }
//
//    @Test
//    fun `should detailsBottomSheet when onTaskCardClicked is called`() {
//        val taskUiState = dummyTasksUiState[0]
//        val taskuidetils = TaskDetailsUiState(
//            id = taskUiState.id,
//            title = taskUiState.title,
//            description = taskUiState.description,
//            categoryIconRes = taskUiState.categoryIcon,
//            priority =taskUiState.priority,
//            status = TaskStatusUiState.IN_PROGRESS
//        )
//        tasksScreenViewModel.onTaskCardClicked(taskUiState)
//        val state = tasksScreenViewModel.taskScreenUiState.value
//
//        assertEquals(taskuidetils, state.taskDetailsUiState)
//    }
//
//    @Test
//    fun `should hideDetailsBottomSheet when hideDetailsBottomSheet is called`() {
//        //when
//        tasksScreenViewModel.hideDetailsBottomSheet()
//        //then
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertTrue(state.taskDetailsUiState == null)
//    }
//
//    @Test
//    fun `should showSnackBar when showSnackBar is called`() {
//        tasksScreenViewModel.showSnackBar()
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertTrue(state.isSnackBarVisible)
//    }
//
//
//    @Test
//    fun `should hideSnackBar when hideSnackBar is called`() {
//        tasksScreenViewModel.hideSnackBar()
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertFalse(state.isSnackBarVisible)
//    }
//
//    @Test
//    fun `should hideBottomSheet when onCancelButtonClicked`() {
//        tasksScreenViewModel.onCancelButtonClicked()
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertFalse(state.isSnackBarVisible)
//    }
//
//    @Test
//    fun `should hideBottomSheet when onBottomSheetDismissed`() {
//        tasksScreenViewModel.onBottomSheetDismissed()
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertFalse(state.isSnackBarVisible)
//    }
//
//    @Test
//    fun `should return categoryIcon when getCategoryIconById is called`() = runTest {
//        coEvery { taskService.getTasksByStatusAndDate(any(), any()) } returns flowOf(emptyList())
//        coEvery { categoryService.getCategoryIconById(1L) } returns "ic_shopping_cart"
//        val categoryicn = tasksScreenViewModel.getCategoryIconById(1L)
//        assertEquals("ic_shopping_cart", categoryicn)
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `should update list when onTabSelected is called`() = runTest {
//        val statusUiState = TaskStatusUiState.entries[0]
//        val status = statusUiState.toDomain()
//        coEvery { taskService.getTasksByStatusAndDate(any(), any()) } returns emptyFlow()
//        coEvery { categoryService.getCategoryIconById(1L) } returns "ic_shopping_cart"
//
//        tasksScreenViewModel.onTabSelected(0)
//        advanceUntilIdle()
//        coVerify { taskService.getTasksByStatusAndDate(status, any()) }
//    }
//
//    @Test
//    fun `should show datePicker when onCalendarClicked called`() = runTest {
//        coEvery { taskService.getTasksByStatusAndDate(TaskStatus.TODO, any()) } returns flowOf(emptyList())
//
//        tasksScreenViewModel.onCalendarClicked()
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertTrue(state.dateUiState.isDatePickerVisible)
//    }
//
//    @Test
//    fun `should show datePicker when onDismissDatePicker called`() = runTest {
//        coEvery { taskService.getTasksByStatusAndDate(TaskStatus.TODO, any()) } returns flowOf(emptyList())
//
//        tasksScreenViewModel.onDismissDatePicker()
//        val state = tasksScreenViewModel.taskScreenUiState.value
//        assertFalse(state.dateUiState.isDatePickerVisible)
//    }
//
//    @Test
//    fun `onPreviousArrowClicked should update selectedMonth and daysCardsData`() = runTest {
//        val initialMonth = tasksScreenViewModel.taskScreenUiState.value.dateUiState.selectedYearMonth
//        val expectedMonth = initialMonth.minusMonths(1)
//        coEvery { taskService.getTasksByStatusAndDate(TaskStatus.TODO, any()) } returns flowOf(emptyList())
//        tasksScreenViewModel.onPreviousArrowClicked()
//
//        val state = tasksScreenViewModel.taskScreenUiState.value.dateUiState
//
//        assertEquals(expectedMonth, state.selectedYearMonth)
//
//        assertEquals(expectedMonth.lengthOfMonth(), state.daysCardsData.size)
//
//        assertEquals(1, state.daysCardsData.first().dayNumber)
//        assertEquals(
//            expectedMonth.atDay(1).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
//            state.daysCardsData.first().dayName
//        )
//
//        assertTrue(state.daysCardsData.any { it.isSelected })
//    }
//
//    @Test
//    fun `onNextArrowClicked should update selectedMonth and daysCardsData`() = runTest {
//        val initialMonth = tasksScreenViewModel.taskScreenUiState.value.dateUiState.selectedYearMonth
//        val expectedMonth = initialMonth.plusMonths(1)
//        coEvery { taskService.getTasksByStatusAndDate(TaskStatus.TODO, any()) } returns flowOf(emptyList())
//        tasksScreenViewModel.onNextArrowClicked()
//
//        val state = tasksScreenViewModel.taskScreenUiState.value.dateUiState
//
//
//        assertEquals(expectedMonth, state.selectedYearMonth)
//
//        assertEquals(expectedMonth.lengthOfMonth(), state.daysCardsData.size)
//
//        assertEquals(1, state.daysCardsData.first().dayNumber)
//        assertEquals(
//            expectedMonth.atDay(1).dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
//            state.daysCardsData.first().dayName
//        )
//
//        assertTrue(state.daysCardsData.any { it.isSelected })
//    }
//
//    @Test
//    fun `onDayCardClicked should update selected card only`() = runTest {
//        val initialCards = listOf(
//            DateCardUiState(dayNumber = 1, dayName = "Sat", isSelected = false),
//            DateCardUiState(dayNumber = 2, dayName = "Sun", isSelected = false),
//            DateCardUiState(dayNumber = 3, dayName = "Mon", isSelected = false),
//        )
//        val initialTasks = dummyTasks
//        coEvery { taskService.getTasksByStatusAndDate(any(), any()) } returns MutableStateFlow(initialTasks)
//        coEvery { categoryService.getCategoryIconById(any()) } returns "test"
//
//        tasksScreenViewModel.taskScreenUiState.update {
//            it.copy(dateUiState = it.dateUiState.copy(daysCardsData = initialCards))
//        }
//
//        // Act
//        tasksScreenViewModel.onDayCardClicked(1)
//
//        // Assert
//        val updated = tasksScreenViewModel.taskScreenUiState.value.dateUiState.daysCardsData
//        assertTrue(updated[1].isSelected)
//        assertFalse(updated[0].isSelected)
//        assertFalse(updated[2].isSelected)
//    }
//
//    @Test
//    fun `onConfirmDatePicker should update selected date and trigger effects`() = runTest {
//        val millis = 1752556800000L
//        val initialCards = (1..30).map {
//            DateCardUiState(dayNumber = it, dayName = "Sat", isSelected = false)
//        }
//        val initialTasks = dummyTasks
//        coEvery { taskService.getTasksByStatusAndDate(any(), any()) } returns MutableStateFlow(initialTasks)
//        coEvery { categoryService.getCategoryIconById(any()) } returns "test"
//        tasksScreenViewModel.taskScreenUiState.update {
//            it.copy(
//                dateUiState = it.dateUiState.copy(
//                    daysCardsData = initialCards
//                )
//            )
//        }
//        val initialTrigger = tasksScreenViewModel.triggerEffectVersion.value
//
//        tasksScreenViewModel.onConfirmDatePicker(millis)
//
//        val updatedUiState = tasksScreenViewModel.taskScreenUiState.value
//        val updatedDateState = updatedUiState.dateUiState
//
//        assertEquals("2025", updatedDateState.selectedYear)
//        assertTrue(updatedDateState.daysCardsData[14].isSelected)
//        assertFalse(updatedDateState.daysCardsData[0].isSelected)
//        assertEquals(initialTrigger + 1, tasksScreenViewModel.triggerEffectVersion.value)
//    }
//
//    @After
//    fun tearDown() {
//        clearAllMocks()
//        Dispatchers.resetMain()
//    }
//}
//
//val dummyTasksUiState = mutableListOf(
//    TaskUiState(
//        id = 1L,
//        title = "Buy groceries",
//        description = "Milk, eggs, bread, and coffee",
//        priority = TaskPriorityUiState.LOW,
//        status = TaskStatusUiState.IN_PROGRESS,
//        categoryTitle = "Shopping",
//        categoryIcon = "ic_shopping_cart"
//    ),
//    TaskUiState(
//        id = 2L,
//        title = "Workout",
//        description = "45 minutes of cardio and strength",
//        priority = TaskPriorityUiState.LOW,
//        status = TaskStatusUiState.IN_PROGRESS,
//        categoryTitle = "Health",
//        categoryIcon = "ic_fitness"
//    ),
//    TaskUiState(
//        id = 3L,
//        title = "Finish report",
//        description = "Complete the Q2 performance report",
//        priority = TaskPriorityUiState.LOW,
//        status = TaskStatusUiState.IN_PROGRESS,
//        categoryTitle = "Work",
//        categoryIcon = "ic_work"
//    ),
//    TaskUiState(
//        id = 4L,
//        title = "Read a book",
//        description = "Read at least 50 pages of the current book",
//        priority = TaskPriorityUiState.LOW,
//        status = TaskStatusUiState.IN_PROGRESS,
//        categoryTitle = "Leisure",
//        categoryIcon = "ic_book"
//    )
//)
//val dummyTasks = listOf(
//    Task(
//        id = 1L,
//        title = "Buy groceries",
//        description = "Milk, eggs, bread, and coffee",
//        priority = TaskPriority.HIGH,
//        categoryId = 1L,
//        status = TaskStatus.TODO,
//        assignedDate = LocalDate(2025, 6, 21)
//    ),
//    Task(
//        id = 2L,
//        title = "Workout",
//        description = "45 minutes of cardio and strength",
//        priority = TaskPriority.MEDIUM,
//        categoryId = 2L,
//        status = TaskStatus.IN_PROGRESS,
//        assignedDate = LocalDate(2025, 6, 21)
//    ),
//    Task(
//        id = 3L,
//        title = "Finish report",
//        description = "Complete the Q2 performance report",
//        priority = TaskPriority.HIGH,
//        categoryId = 3L,
//        status = TaskStatus.TODO,
//        assignedDate = LocalDate(2025, 6, 21)
//    ),
//    Task(
//        id = 4L,
//        title = "Read a book",
//        description = "Read at least 50 pages of the current book",
//        priority = TaskPriority.LOW,
//        categoryId = 4L,
//        status = TaskStatus.DONE,
//        assignedDate = LocalDate(2025, 6, 21)
//    )
//)
//
//
//
//
