package com.example.tudee.ui.home.viewmodel


interface HomeActions {
      fun onInProgressArrowClicked()
      fun onTodoArrowClicked()
      fun onDoneArrowClicked()
      fun onTaskCardClicked()
      fun onFabClicked()
      fun onBottomSheetDismissed()
      fun editTask()
      fun changeStatus()
      fun onCreateTaskButtonClicked()
      fun onEditTaskButtonClicked()
      fun onCancelButtonClicked()
      fun hideSnackBar()
}