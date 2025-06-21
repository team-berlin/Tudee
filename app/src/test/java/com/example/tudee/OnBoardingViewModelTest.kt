package com.example.tudee

import app.cash.turbine.test
import com.example.tudee.data.mapper.predefinedCategories
import com.example.tudee.domain.AppEntry
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.presentation.screen.onboarding.OnBoardingViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coVerify
import io.mockk.just

@OptIn(ExperimentalCoroutinesApi::class)
class OnBoardingViewModelTest {
    private lateinit var viewModel: OnBoardingViewModel
    private lateinit var appEntry: AppEntry
    private lateinit var taskCategoryService: TaskCategoryService

    @Before
    fun setup() {
        appEntry = mockk()
        taskCategoryService = mockk()
    }

    @Test
    fun `isFirstEntry should emit true when appEntry returns null`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns true

        // When
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)

        // Then
        viewModel.isFirstEntry.test {
            assertThat(awaitItem()).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit false when not first app entry`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns false

        // When
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)

        // Then
        viewModel.isFirstEntry.test {
            assertThat(awaitItem()).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should call saveFirstEntry when saveFirstEntry is triggered`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns true
        coEvery { appEntry.saveFirstEntry() } just Runs

        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)

        // When
        viewModel.saveFirstEntry()

        // Then
        coVerify(exactly = 1) { appEntry.saveFirstEntry() }
    }

    @Test
    fun `should call createCategory for each predefined category`() = runTest {
        // Given
        val predefinedCount = predefinedCategories.size
        coEvery { taskCategoryService.createCategory(any()) } returns Unit
        coEvery { appEntry.isFirstEntry() } returns true
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)

        // When
        viewModel.loadInitialData()

    }
}