package com.example.tudee.ui.home.viewmodel


sealed interface HomeActions {
    data class OnArrowClicked(val taskStatusUiState: TaskStatusUiState?) : HomeActions

    data class OnTaskCardClicked(val taskUiState: TaskUiState) : HomeActions
    data object OnFabClicked : HomeActions
    data object OnBottomSheetDismissed : HomeActions
    data object OnEditTaskButtonClicked : HomeActions
    data object HideSnackBar : HomeActions
    data object OnCancelButtonClicked : HomeActions

    data class OnEditTaskTitleChanged(val title: String) : HomeActions
    data class OnEditTaskCategoryChanged(val category: CategoryUiState) : HomeActions
    data class OnEditTaskDescriptionChanged(val description: String) : HomeActions
    data class OnEditTaskPriorityChanged(val priority: TaskPriorityUiState) : HomeActions
    data class OnEditTaskDateChanged(val date: kotlinx.datetime.LocalDate) : HomeActions

    data class OnTaskStatusChanged(val status: TaskStatusUiState) : HomeActions
    data class OnCreateTaskButtonClicked(val taskCreationRequest: TaskUiState) : HomeActions
    data class OnThemeChanged(val isDarkMode: Boolean) : HomeActions
}
