package com.example.tudee.presentation.screen.task_screen.interactors

import com.example.tudee.presentation.screen.task_screen.ui_states.TaskUiState

interface TaskScreenInteractor {
    fun onDayCardClicked(cardIndex: Int)
    fun onDeleteIconClicked(taskId:Long)
    fun onTabSelected(tabIndex: Int)
    fun onBottomSheetDismissed()
    fun onFloatingActionClicked()
    fun onTaskCardClicked(taskUiState: TaskUiState)
    fun onConfirmDelete()
    fun onCancelButtonClicked()
    fun onCalendarClicked()
    fun onDismissDatePicker()
}