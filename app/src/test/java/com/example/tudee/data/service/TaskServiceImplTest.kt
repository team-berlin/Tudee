package com.example.tudee.data.service

import com.example.tudee.data.dao.TaskDao
import com.example.tudee.data.model.TaskEntity
import com.example.tudee.domain.entity.Task
import com.example.tudee.domain.entity.TaskPriority
import com.example.tudee.domain.entity.TaskStatus
import com.example.tudee.domain.request.TaskCreationRequest
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TaskServiceImplTest {

    private lateinit var taskDao: TaskDao
    private lateinit var taskService: TaskServiceImpl

    @Before
    fun setup() {
        taskDao = mockk(relaxed = true)
        taskService = TaskServiceImpl(taskDao)
    }

    @Test
    fun `createTask should call taskDao createTask with correct parameters`() = runTest {
        // Given
        val taskCreationRequest = TaskCreationRequest(
            title = "Test Task",
            description = "Test Description",
            priority = TaskPriority.HIGH,
            categoryId = 1L,
            status = TaskStatus.TODO,
            assignedDate = LocalDate(2023, 1, 1)
        )
        
        val taskEntitySlot = slot<TaskEntity>()
        coEvery { taskDao.createTask(capture(taskEntitySlot)) } just runs

        // When
        taskService.createTask(taskCreationRequest)

        // Then
        coVerify { taskDao.createTask(any()) }
        
        val capturedEntity = taskEntitySlot.captured
        Truth.assertThat(capturedEntity.title).isEqualTo(taskCreationRequest.title)
        Truth.assertThat(capturedEntity.description).isEqualTo(taskCreationRequest.description)
        Truth.assertThat(capturedEntity.priority).isEqualTo(taskCreationRequest.priority.name)
        Truth.assertThat(capturedEntity.categoryId).isEqualTo(taskCreationRequest.categoryId)
        Truth.assertThat(capturedEntity.status).isEqualTo(taskCreationRequest.status.name)
        Truth.assertThat(capturedEntity.assignedDate).isEqualTo(taskCreationRequest.assignedDate.toString())
    }

    @Test
    fun `deleteTask should call taskDao deleteTask with correct taskId`() = runTest {
        // Given
        val taskId = 1L
        coEvery { taskDao.deleteTask(taskId) } just runs

        // When
        taskService.deleteTask(taskId)

        // Then
        coVerify { taskDao.deleteTask(taskId) }
    }

    @Test
    fun `editTask should call taskDao editTask with correct task entity`() = runTest {
        // Given
        val task = Task(
            id = 1L,
            title = "Test Task",
            description = "Test Description",
            priority = TaskPriority.HIGH,
            categoryId = 1L,
            status = TaskStatus.TODO,
            assignedDate = LocalDate(2023, 1, 1)
        )
        
        coEvery { taskDao.editTask(any()) } just runs

        // When
        taskService.editTask(task)

        // Then
        coVerify { taskDao.editTask(any()) }
    }

    @Test
    fun `editTaskStatus should call taskDao editTaskStatus with correct parameters`() = runTest {
        // Given
        val taskId = 1L
        val status = TaskStatus.DONE
        
        coEvery { taskDao.editTaskStatus(taskId, status) } just runs

        // When
        taskService.editTaskStatus(taskId, status)

        // Then
        coVerify { taskDao.editTaskStatus(taskId, status) }
    }

    @Test
    fun `getTasks should return mapped domain tasks from taskDao`() = runTest {
        // Given
        val taskEntities = listOf(
            TaskEntity(
                id = 1L,
                title = "Task 1",
                description = "Description 1",
                priority = TaskPriority.HIGH.name,
                categoryId = 1L,
                status = TaskStatus.TODO.name,
                assignedDate = LocalDate(2023, 1, 1).toString()
            ),
            TaskEntity(
                id = 2L,
                title = "Task 2",
                description = "Description 2",
                priority = TaskPriority.MEDIUM.name,
                categoryId = 2L,
                status = TaskStatus.IN_PROGRESS.name,
                assignedDate = LocalDate(2023, 1, 2).toString()
            )
        )
        
        every { taskDao.getTasks() } returns flowOf(taskEntities)

        // When
        val result = taskService.getTasks()

        // Then
        result.collect { tasks ->
            Truth.assertThat(tasks.size).isEqualTo(2)
            Truth.assertThat(tasks[0].id).isEqualTo(1L)
            Truth.assertThat(tasks[0].title).isEqualTo("Task 1")
            Truth.assertThat(tasks[1].id).isEqualTo(2L)
            Truth.assertThat(tasks[1].title).isEqualTo("Task 2")
        }
    }

    @Test
    fun `getTaskById should return mapped domain task from taskDao`() = runTest {
        // Given
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "Task 1",
            description = "Description 1",
            priority = TaskPriority.HIGH.name,
            categoryId = 1L,
            status = TaskStatus.TODO.name,
            assignedDate = LocalDate(2023, 1, 1).toString()
        )
        
        coEvery { taskDao.getTaskById(taskId) } returns taskEntity

        // When
        val result = taskService.getTaskById(taskId)

        // Then
        Truth.assertThat(result.id).isEqualTo(taskId)
        Truth.assertThat(result.title).isEqualTo("Task 1")
        Truth.assertThat(result.description).isEqualTo("Description 1")
        Truth.assertThat(result.priority).isEqualTo(TaskPriority.HIGH)
        Truth.assertThat(result.status).isEqualTo(TaskStatus.TODO)
    }

    @Test
    fun `getTasksByCategoryId should return mapped domain tasks from taskDao`() = runTest {
        // Given
        val categoryId = 1L
        val taskEntities = flowOf(
            listOf(
                TaskEntity(
                    id = 1L,
                    title = "Task 1",
                    description = "Description 1",
                    priority = TaskPriority.HIGH.name,
                    categoryId = categoryId,
                    status = TaskStatus.TODO.name,
                    assignedDate = LocalDate(2023, 1, 1).toString()
                ),
                TaskEntity(
                    id = 2L,
                    title = "Task 2",
                    description = "Description 2",
                    priority = TaskPriority.MEDIUM.name,
                    categoryId = categoryId,
                    status = TaskStatus.IN_PROGRESS.name,
                    assignedDate = LocalDate(2023, 1, 2).toString()
                )
            )
        )
        
        every { taskDao.getTasksByCategoryId(categoryId) } returns taskEntities

        // When
        val result = taskService.getTasksByCategoryId(categoryId)

        // Then
        result.collect { tasks ->
            Truth.assertThat(tasks.size).isEqualTo(2)
            Truth.assertThat(tasks[0].categoryId).isEqualTo(categoryId)
            Truth.assertThat(tasks[1].categoryId).isEqualTo(categoryId)
        }
    }

    @Test
    fun `getTasksByStatus should return mapped domain tasks from taskDao`() = runTest {
        // Given
        val status = TaskStatus.TODO
        val taskEntities = flowOf(
            listOf(
                TaskEntity(
                    id = 1L,
                    title = "Task 1",
                    description = "Description 1",
                    priority = TaskPriority.HIGH.name,
                    categoryId = 1L,
                    status = status.name,
                    assignedDate = LocalDate(2023, 1, 1).toString()
                ),
                TaskEntity(
                    id = 2L,
                    title = "Task 2",
                    description = "Description 2",
                    priority = TaskPriority.MEDIUM.name,
                    categoryId = 2L,
                    status = status.name,
                    assignedDate = LocalDate(2023, 1, 2).toString()
                )
            )
        )
        
        every { taskDao.getTasksByStatus(status) } returns taskEntities

        // When
        val result = taskService.getTasksByStatus(status)

        // Then
        result.collect { tasks ->
            Truth.assertThat(tasks.size).isEqualTo(2)
            Truth.assertThat(tasks[0].status).isEqualTo(status)
            Truth.assertThat(tasks[1].status).isEqualTo(status)
        }
    }

    @Test
    fun `getTasksByAssignedDate should return mapped domain tasks from taskDao`() = runTest {
        // Given
        val date = LocalDate(2023, 1, 1)
        val taskEntities = flowOf(
            listOf(
                TaskEntity(
                    id = 1L,
                    title = "Task 1",
                    description = "Description 1",
                    priority = TaskPriority.HIGH.name,
                    categoryId = 1L,
                    status = TaskStatus.TODO.name,
                    assignedDate = date.toString()
                ),
                TaskEntity(
                    id = 2L,
                    title = "Task 2",
                    description = "Description 2",
                    priority = TaskPriority.MEDIUM.name,
                    categoryId = 2L,
                    status = TaskStatus.IN_PROGRESS.name,
                    assignedDate = date.toString()
                )
            )
        )
        
        every { taskDao.getTasksByAssignedDate(date.toString()) } returns taskEntities

        // When
        val result = taskService.getTasksByAssignedDate(date)

        // Then
        result.collect { tasks ->
            Truth.assertThat(tasks.size).isEqualTo(2)
            Truth.assertThat(tasks[0].assignedDate).isEqualTo(date)
            Truth.assertThat(tasks[1].assignedDate).isEqualTo(date)
        }
    }

    @Test
    fun `getTasksCountByCategoryId should return count from taskDao`() = runTest {
        // Given
        val categoryId = 1L
        val count = 5L
        
        coEvery { taskDao.getTasksCountByCategoryId(categoryId) } returns count

        // When
        val result = taskService.getTasksCountByCategoryId(categoryId)

        // Then
        Truth.assertThat(result).isEqualTo(count)
        coVerify { taskDao.getTasksCountByCategoryId(categoryId) }
    }
}