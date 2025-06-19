package com.example.tudee.presentation.screen.task_screen


interface TaskScreenInteractor {
    fun onDayCardClicked(cardIndex: Int)
    fun onDeleteIconClicked(taskId:Long)
    fun onTabSelected(tabIndex: Int)
    fun onBottomSheetDismissed()
    fun onFloatingActionClicked()
    fun onTaskCardClicked(taskId:Long)
    fun onBottomSheetDeleteButtonClicked()
    fun onCancelButtonClicked()
    fun onDateCardClicked()
    fun onDismissDatePicker()
}