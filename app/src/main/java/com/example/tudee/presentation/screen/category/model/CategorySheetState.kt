package com.example.tudee.presentation.screen.category.model

data class CategorySheetState(
    val isVisible: Boolean = false,
    val mode: CategorySheetMode,
    val title: String,
    val initialData: CategoryData
) {
    companion object {
        fun add(
            isVisible: Boolean = false,
            title: String = "Add new category",
            initialData: CategoryData = CategoryData("")
        ) = CategorySheetState(
            isVisible = isVisible,
            mode = CategorySheetMode.Add,
            title = title,
            initialData = initialData
        )

        fun edit(
            isVisible: Boolean = false,
            title: String = "Edit category",
            initialData: CategoryData
        ) = CategorySheetState(
            isVisible = isVisible,
            mode = CategorySheetMode.Edit,
            title = title,
            initialData = initialData
        )
    }
}

enum class CategorySheetMode {
    Add,
    Edit
}