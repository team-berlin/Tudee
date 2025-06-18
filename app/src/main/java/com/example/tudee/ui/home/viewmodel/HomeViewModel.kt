//package com.example.tudee.ui.home.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.tudee.domain.TaskService
//import com.example.tudee.domain.entity.Task
//import com.example.tudee.domain.entity.TaskPriority
//import com.example.tudee.domain.entity.TaskStatus
//import com.example.tudee.domain.request.TaskCreationRequest
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.datetime.Clock
//import kotlinx.datetime.TimeZone
//import kotlinx.datetime.toLocalDateTime
//
//val priorityMap = mapOf<TaskPriorityUiState, TaskPriority>(
//    TaskPriorityUiState.LOW to TaskPriority.LOW,
//    TaskPriorityUiState.MEDIUM to TaskPriority.MEDIUM,
//    TaskPriorityUiState.HIGH  to TaskPriority.HIGH
//)
//val statusMap = mapOf<TaskStatusUiState, TaskStatus>(
//    TaskStatusUiState.TODO to TaskStatus.TODO,
//    TaskStatusUiState.IN_PROGRESS  to TaskStatus.IN_PROGRESS,
//    TaskStatusUiState.DONE  to TaskStatus.DONE
//)
//
//class HomeViewModel(val taskService: TaskService) : HomeActions, ViewModel() {
//    private val _homeUiState = MutableStateFlow(HomeUiState())
//    val homeUiState = _homeUiState.asStateFlow()
//    init {
//        getTodayDate()
//        getMonth()
//        getYear()
//        getTasksDoneCount()
//        getTasksTodoCount()
//        getTasksInProgressCount()
//        getInProgressTasks()
//        getDoneTasks()
//        getTodoTasks()
//    }
//
//    private fun getTodayDate() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                taskTodayDateUiState = oldValue.taskTodayDateUiState.copy(
//                    todayDateNumber = Clock.System.now()
//                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.dayOfMonth.toString()
//                )
//            )
//        }
//    }
//
//    private fun getMonth() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                taskTodayDateUiState = oldValue.taskTodayDateUiState.copy(
//                    month = Clock.System.now()
//                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.month.toString()
//                )
//            )
//        }
//    }
//
//    private fun getYear() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                taskTodayDateUiState = oldValue.taskTodayDateUiState.copy(
//                    year = Clock.System.now()
//                        .toLocalDateTime(TimeZone.currentSystemDefault()).date.year.toString()
//                )
//            )
//        }
//    }
//
//     private fun getTasksDoneCount() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                tasksDoneCount = filterTasksByStatus(
//                    TaskStatus.DONE,
//                    taskService.getTasks()
//                ).size.toString()
//            )
//        }
//    }
//
//     private fun getTasksTodoCount() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                tasksDoneCount = filterTasksByStatus(
//                    TaskStatus.TODO,
//                    taskService.getTasks()
//                ).size.toString()
//            )
//        }
//    }
//
//     private fun getTasksInProgressCount() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                tasksDoneCount = filterTasksByStatus(
//                    TaskStatus.IN_PROGRESS,
//                    taskService.getTasks()
//                ).size.toString()
//            )
//        }
//    }
//
//     private fun getInProgressTasks() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                todayTasksInProgress = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS)
//            )
//        }
//    }
//
//    override fun getDoneTasks() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                todayTasksInProgress = taskService.getTasksByStatus(TaskStatus.DONE)
//            )
//        }
//    }
//
//    override fun getTodoTasks() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                todayTasksInProgress = taskService.getTasksByStatus(TaskStatus.TODO)
//            )
//        }
//    }
//
//    override fun onAddButtonClicked() {
//        viewModelScope.launch {
//            taskService.createTask(
//                taskCreationRequest = TaskCreationRequest(
//                    title = homeUiState.value.taskUiState.taskTitle,
//                    description = homeUiState.value.taskUiState.taskTitle,
//                    priority = priorityMap[homeUiState.value.taskUiState.taskPriority]
//                        ?: TaskPriority.LOW,
//                    categoryId = homeUiState.value.taskUiState.taskCategory.id.toLong(),
//                    status = statusMap[homeUiState.value.taskUiState.taskStatusUiState] ?: TaskStatus.TODO,
//                    assignedDate = homeUiState.value.taskUiState.taskAssignedDate
//                )
//            )
//        }
//    }
//
//    override fun navigateToTaskDetails() {
//        TODO("Not yet implemented")
//    }
//
//    override fun openBottomSheet() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                isBottomSheetVisible = true
//            )
//        }
//    }
//
//    override fun dismissBottomSheet() {
//        _homeUiState.update { oldValue ->
//            oldValue.copy(
//                isBottomSheetVisible = false
//            )
//        }
//    }
//
//    override fun viewTaskDetails() {
//        viewModelScope.launch {
//            taskService.getTaskById(homeUiState.value.taskUiState.taskId.toLong())
//        }
//    }
//
//    override fun onInProgressArrowClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onTodoArrowClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onDoneArrowClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onTaskCardClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onFabClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBottomSheetDismissed() {
//        TODO("Not yet implemented")
//    }
//
//    override fun editTask() {
//        TODO("Not yet implemented")
//    }
//
//    override fun changeStatus() {
//        when (homeUiState.value.taskUiState.taskStatusUiState) {
//            TaskStatus.TODO.name -> {
//                _homeUiState.update { oldValue ->
//                    oldValue.copy(
//                        taskUiState = oldValue.taskUiState.copy(
//                            taskStatusUiState = TaskStatus.IN_PROGRESS.name
//                        )
//                    )
//                }
//            }
//        }
//    }
//
//    override fun onCreateTaskButtonClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onEditTaskButtonClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCancelButtonClicked() {
//        TODO("Not yet implemented")
//    }
//
//    override fun hideSnackBar() {
//        TODO("Not yet implemented")
//    }
//
//    private fun filterTasksByStatus(taskStatus: TaskStatus, taskList: List<Task>): List<Task> {
//        return taskList.filter { it.status == taskStatus }
//    }
//}