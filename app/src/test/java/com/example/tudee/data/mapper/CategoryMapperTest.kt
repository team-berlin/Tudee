package com.example.tudee.data.mapper

import com.example.tudee.data.model.TaskCategoryEntity
import com.example.tudee.domain.entity.TaskCategory
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TaskCategoryMapperTest {

    private lateinit var mockTaskCategory: TaskCategory
    private lateinit var mockTaskCategoryEntity: TaskCategoryEntity

    @Before
    fun setup() {
        mockTaskCategory = mockk<TaskCategory>(relaxed = true) {
            every { id } returns 1L
            every { title } returns "Test Category"
            every { image } returns "category_icon"
            every { isPredefined } returns true
        }

        mockTaskCategoryEntity = mockk<TaskCategoryEntity>(relaxed = true) {
            every { id } returns 1L
            every { title } returns "Test Category"
            every { image } returns "category_icon"
            every { isPredefined } returns true
        }
    }

    @Test
    fun `should return TaskCategory when mapping from TaskCategoryEntity`() {
        // Given
        val expectedTaskCategory = TaskCategory(
            id = 1L,
            title = "Test Category",
            image = "category_icon",
            isPredefined = true
        )

        // When
        val result = mockTaskCategoryEntity.toDomain()

        // Then
        assertEquals(expectedTaskCategory.id, result.id)
        assertEquals(expectedTaskCategory.title, result.title)
        assertEquals(expectedTaskCategory.image, result.image)
        assertEquals(expectedTaskCategory.isPredefined, result.isPredefined)
    }

    @Test
    fun `should return TaskCategoryEntity when mapping from TaskCategory`() {
        // Given
        val expectedTaskCategoryEntity = TaskCategoryEntity(
            id = 1L,
            title = "Test Category",
            image = "category_icon",
            isPredefined = true
        )

        // When
        val result = mockTaskCategory.toEntity()

        // Then
        assertEquals(expectedTaskCategoryEntity.id, result.id)
        assertEquals(expectedTaskCategoryEntity.title, result.title)
        assertEquals(expectedTaskCategoryEntity.image, result.image)
        assertEquals(expectedTaskCategoryEntity.isPredefined, result.isPredefined)
    }

}