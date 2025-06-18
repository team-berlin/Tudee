package com.example.tudee.presentation.screen.task_screen

import kotlinx.coroutines.flow.update

interface TaskScreenInteractor {
    fun onDayCardClicked(cardIndex: Int)
    fun onDeleteIconClicked(taskId:Long?)
    fun onTabSelected(tabIndex: Int)
    fun onBottomSheetDismissed()
    fun onFloatingActionClicked()
    fun onTaskCardClicked(taskId:Long)
    fun onDeleteButtonClicked()
    fun onCancelButtonClicked()
    fun onDateCardClicked()
    fun onDismissDatePicker()
}