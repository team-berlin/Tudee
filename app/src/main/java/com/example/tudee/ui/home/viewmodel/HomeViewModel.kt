package com.example.tudee.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudee.domain.TaskService
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import com.example.tudee.ui.mapper.toTask
import com.example.tudee.ui.mapper.toTaskPriority
import com.example.tudee.ui.mapper.toTaskStatus
import com.example.tudee.ui.mapper.toTaskUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class HomeViewModel(val taskService: TaskService) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun init() {
        getTodayDate()
        getMonth()
        getYear()
        getInProgressTasks()
        getDoneTasks()
        getTodoTasks()
    }

    fun handleActions(actions: HomeActions) {
        when (actions) {
            HomeActions.HideSnackBar -> hideSnackBar()
            is HomeActions.OnArrowClicked -> onArrowClicked(actions.taskStatusUiState)
            HomeActions.OnBottomSheetDismissed -> onBottomSheetDismissed()
            HomeActions.OnCancelButtonClicked -> onCancelButtonClicked()
            is HomeActions.OnCreateTaskButtonClicked -> onCreateTaskButtonClicked(actions.taskCreationRequest)
            HomeActions.OnEditTaskButtonClicked -> onEditTaskButtonClicked()
            is HomeActions.OnEditTaskCategoryChanged -> onEditTaskCategoryChanged(actions.category)
            is HomeActions.OnEditTaskDescriptionChanged -> onEditTaskDescriptionChanged(actions.description)
            is HomeActions.OnEditTaskPriorityChanged -> onEditTaskPriorityChanged(actions.priority)
            is HomeActions.OnEditTaskTitleChanged -> onEditTaskTitleChanged(actions.title)
            HomeActions.OnFabClicked -> onFabClicked()
            is HomeActions.OnTaskCardClicked -> onTaskCardClicked(actions.taskUiState)
            is HomeActions.OnTaskStatusChanged -> onTaskStatusChanged(actions.status)
        }
    }

    private fun getTodayDate() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                taskTodayDateUiState = oldValue.taskTodayDateUiState.copy(
                    todayDateNumber = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.dayOfMonth.toString()
                )
            )
        }
    }

    private fun getMonth() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                taskTodayDateUiState = oldValue.taskTodayDateUiState.copy(
                    month = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.month.toString()
                )
            )
        }
    }

    private fun getYear() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                taskTodayDateUiState = oldValue.taskTodayDateUiState.copy(
                    year = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.year.toString()
                )
            )
        }
    }

    private fun updateTasksFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getTasks().collect { tasks ->
                _homeUiState.update { oldValue ->
                    oldValue.copy(
                        allTasks = tasks.map { it.toTaskUiState() },
                        tasksUiCount = oldValue.tasksUiCount.apply {
                            copy(
                                tasksDoneCount = filterTasksByStatus(
                                    TaskStatus.DONE,
                                    tasks
                                ).size.toString(),
                                tasksInProgressCount = filterTasksByStatus(
                                    TaskStatus.IN_PROGRESS,
                                    tasks
                                ).size.toString(),
                                tasksTodoCount = filterTasksByStatus(
                                    TaskStatus.TODO,
                                    tasks
                                ).size.toString()

                            )
                        },
                    )
                }
            }
        }
    }


    private fun getInProgressTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getTasksByStatus(TaskStatus.IN_PROGRESS).collect { tasks ->
                _homeUiState.update { oldValue ->
                    oldValue.copy(
                        todayTasksInProgress = tasks.map { it.toTaskUiState() }
                    )
                }
            }
        }
    }


    private fun getDoneTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getTasksByStatus(TaskStatus.DONE).collect { tasks ->
                _homeUiState.update { oldValue ->
                    oldValue.copy(
                        todayTasksDone = tasks.map { it.toTaskUiState() }
                    )
                }
            }
        }
    }

    private fun getTodoTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getTasksByStatus(TaskStatus.TODO).collect { tasks ->
                _homeUiState.update { oldValue ->
                    oldValue.copy(
                        todayTasksTodo = tasks.map { it.toTaskUiState() }
                    )
                }
            }
        }
    }

    private fun hideSnackBar() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                showSnackBar = false
            )
        }
    }

    private fun onArrowClicked(taskStatusUiState: TaskStatusUiState?) {
        when (taskStatusUiState) {
            TaskStatusUiState.TODO -> _homeUiState.update { oldValue ->
                oldValue.copy(
                    navigateTodoTasks = true,
                )
            }

            TaskStatusUiState.IN_PROGRESS -> _homeUiState.update { oldValue ->
                oldValue.copy(
                    navigateInProgressTasks = true,
                )
            }

            TaskStatusUiState.DONE -> _homeUiState.update { oldValue ->
                oldValue.copy(
                    navigateDoneTasks = true,
                )
            }

            else -> {
                _homeUiState.update { oldValue ->
                    oldValue.copy(
                        navigateTodoTasks = false,
                        navigateInProgressTasks = false,
                        navigateDoneTasks = false,
                    )
                }
            }
        }
        _homeUiState.update { oldValue ->
            oldValue.copy(
                // You might want to implement specific logic here
            )
        }
    }

    private fun onBottomSheetDismissed() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                isBottomSheetVisible = false,
                isEditTaskBottomSheetContentVisible = false
            )
        }
    }

    private fun onCancelButtonClicked() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                isBottomSheetVisible = false,
                isEditTaskBottomSheetContentVisible = false
            )
        }
    }

    private fun onCreateTaskButtonClicked(taskUiState: TaskUiState) {
        viewModelScope.launch {
            try {
                val taskCreationRequest = TaskCreationRequest(
                    title = taskUiState.taskTitle,
                    description = taskUiState.taskDescription,
                    priority = taskUiState.taskPriority.toTaskPriority(),
                    categoryId = taskUiState.taskCategory.id.toLong(),
                    status = taskUiState.taskStatusUiState.toTaskStatus(),
                    assignedDate = taskUiState.taskAssignedDate
                )
                taskService.createTask(taskCreationRequest)

                init()

                // Show success message
                _homeUiState.update { oldValue ->
                    oldValue.copy(
                        showSnackBar = true,
                        isBottomSheetVisible = false
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun onFabClicked() {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                isBottomSheetVisible = true,
                isEditTaskBottomSheetContentVisible = false
            )
        }
    }

    private fun onTaskCardClicked(taskUiState: TaskUiState) {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                isBottomSheetVisible = true,
                isEditTaskBottomSheetContentVisible = true,
                selectedTask = taskUiState
            )
        }
    }

    private fun onEditTaskButtonClicked() {
        viewModelScope.launch {
            try {
                val selectedTask = _homeUiState.value.selectedTask
                if (selectedTask != null) {
                    taskService.editTask(selectedTask.toTask())

                    init()

                    // Show success message and hide bottom sheet
                    _homeUiState.update { oldValue ->
                        oldValue.copy(
                            showSnackBar = true,
                            isBottomSheetVisible = false,
                            isEditTaskBottomSheetContentVisible = false
                        )
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun onEditTaskTitleChanged(title: String) {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                selectedTask = oldValue.selectedTask?.copy(
                    taskTitle = title
                )
            )
        }
    }

    private fun onEditTaskDescriptionChanged(description: String) {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                selectedTask = oldValue.selectedTask?.copy(
                    taskDescription = description
                )
            )
        }
    }

    private fun onEditTaskCategoryChanged(category: CategoryUiState) {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                selectedTask = oldValue.selectedTask?.copy(
                    taskCategory = category
                )
            )
        }
    }

    private fun onEditTaskPriorityChanged(priority: TaskPriorityUiState) {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                selectedTask = oldValue.selectedTask?.copy(
                    taskPriority = priority
                )
            )
        }
    }

    private fun onTaskStatusChanged(status: TaskStatusUiState) {
        _homeUiState.update { oldValue ->
            oldValue.copy(
                selectedTask = oldValue.selectedTask?.copy(
                    taskStatusUiState = status
                )
            )
        }
    }

    private fun filterTasksByStatus(taskStatus: TaskStatus, taskList: List<Task>): List<Task> {
        return taskList.filter { it.status == taskStatus }
    }

    fun resetStatus() {
        _homeUiState.update { state ->
            state.copy(
                navigateTodoTasks = false,
                navigateInProgressTasks = false,
                navigateDoneTasks = false,
                showSnackBar = false
            )
        }
    }
}
