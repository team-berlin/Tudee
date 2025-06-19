package com.example.tudee.data.mapper

import com.example.tudee.data.model.TaskEntity
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.ui.home.viewmodel.TaskPriorityUiState
import com.example.tudee.ui.home.viewmodel.TaskStatusUiState
import com.example.tudee.ui.home.viewmodel.TaskUiState
import com.example.tudee.ui.mapper.toTaskStatusUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.toLocalDate




