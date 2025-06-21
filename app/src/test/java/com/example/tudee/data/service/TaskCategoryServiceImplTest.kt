package com.example.tudee.data.service

import com.example.tudee.data.dao.TaskCategoryDao
import com.example.tudee.data.model.TaskCategoryEntity
import com.example.tudee.domain.entity.TaskCategory
import com.example.tudee.domain.request.CategoryCreationRequest
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TaskCategoryServiceImplTest {

    private lateinit var taskCategoryDao: TaskCategoryDao
    private lateinit var taskCategoryService: TaskCategoryServiceImpl

    @Before
    fun setup() {
        taskCategoryDao = mockk(relaxed = true)
        taskCategoryService = TaskCategoryServiceImpl(taskCategoryDao)
    }

    @Test
    fun `createCategory should call taskCategoryDao createCategory with correct parameters`() = runTest {
        // Given
        val categoryCreationRequest = CategoryCreationRequest(
            title = "Test Category",
            isPredefined = false,
            image = "ic_test.png"
        )
        
        val categoryEntitySlot = slot<TaskCategoryEntity>()
        coEvery { taskCategoryDao.createCategory(capture(categoryEntitySlot)) } just runs

        // When
        taskCategoryService.createCategory(categoryCreationRequest)

        // Then
        coVerify { taskCategoryDao.createCategory(any()) }
        
        val capturedEntity = categoryEntitySlot.captured
        Truth.assertThat(capturedEntity.title).isEqualTo(categoryCreationRequest.title)
        Truth.assertThat(capturedEntity.isPredefined).isEqualTo(categoryCreationRequest.isPredefined)
        Truth.assertThat(capturedEntity.image).isEqualTo(categoryCreationRequest.image)
    }

    @Test
    fun `editCategory should call taskCategoryDao editCategory with correct category entity`() = runTest {
        // Given
        val category = TaskCategory(
            id = 1L,
            title = "Test Category",
            isPredefined = false,
            image = "ic_test.png"
        )
        
        coEvery { taskCategoryDao.editCategory(any()) } just runs

        // When
        taskCategoryService.editCategory(category)

        // Then
        coVerify { taskCategoryDao.editCategory(any()) }
    }

    @Test
    fun `deleteCategory should call taskCategoryDao deleteCategory with correct categoryId`() = runTest {
        // Given
        val categoryId = 1L
        coEvery { taskCategoryDao.deleteCategory(categoryId) } just runs

        // When
        taskCategoryService.deleteCategory(categoryId)

        // Then
        coVerify { taskCategoryDao.deleteCategory(categoryId) }
    }

    @Test
    fun `getCategories should return mapped domain categories from taskCategoryDao`() = runTest {
        // Given
        val categoryEntities = listOf(
            TaskCategoryEntity(
                id = 1L,
                title = "Category 1",
                isPredefined = true,
                image = "ic_category1.png"
            ),
            TaskCategoryEntity(
                id = 2L,
                title = "Category 2",
                isPredefined = false,
                image = "ic_category2.png"
            )
        )
        
        every { taskCategoryDao.getCategories() } returns flowOf(categoryEntities)

        // When
        val result = taskCategoryService.getCategories()

        // Then
        val categories = result.first()
        Truth.assertThat(categories.size).isEqualTo(2)
        Truth.assertThat(categories[0].id).isEqualTo(1L)
        Truth.assertThat(categories[0].title).isEqualTo("Category 1")
        Truth.assertThat(categories[0].isPredefined).isTrue()
        Truth.assertThat(categories[0].image).isEqualTo("ic_category1.png")
        
        Truth.assertThat(categories[1].id).isEqualTo(2L)
        Truth.assertThat(categories[1].title).isEqualTo("Category 2")
        Truth.assertThat(categories[1].isPredefined).isFalse()
        Truth.assertThat(categories[1].image).isEqualTo("ic_category2.png")
    }

    @Test
    fun `getCategoryById should return mapped domain category from taskCategoryDao`() = runTest {
        // Given
        val categoryId = 1L
        val categoryEntity = TaskCategoryEntity(
            id = categoryId,
            title = "Category 1",
            isPredefined = true,
            image = "ic_category1.png"
        )
        
        every { taskCategoryDao.getCategoryById(categoryId) } returns flowOf(categoryEntity)

        // When
        val result = taskCategoryService.getCategoryById(categoryId)

        // Then
        val category = result.first()
        Truth.assertThat(category.id).isEqualTo(categoryId)
        Truth.assertThat(category.title).isEqualTo("Category 1")
        Truth.assertThat(category.isPredefined).isTrue()
        Truth.assertThat(category.image).isEqualTo("ic_category1.png")
    }

    @Test
    fun `getCategoryIconById should return icon from taskCategoryDao`() = runTest {
        // Given
        val categoryIconId = 1L
        val iconPath = "ic_category1.png"
        
        coEvery { taskCategoryDao.getCategoryIconById(categoryIconId) } returns iconPath

        // When
        val result = taskCategoryService.getCategoryIconById(categoryIconId)

        // Then
        Truth.assertThat(result).isEqualTo(iconPath)
        coVerify { taskCategoryDao.getCategoryIconById(categoryIconId) }
    }
}