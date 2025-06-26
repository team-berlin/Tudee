package com.example.tudee

import com.example.tudee.domain.AppEntry
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.presentation.screen.onboarding.OnBoardingViewModel
import com.example.tudee.presentation.utils.predefinedCategories
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class OnBoardingViewModelTest {
    private lateinit var viewModel: OnBoardingViewModel
    private lateinit var appEntry: AppEntry
    private lateinit var taskCategoryService: TaskCategoryService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        appEntry = mockk()
        taskCategoryService = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isFirstEntry should emit true when appEntry returns true`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns true

        // When
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.isFirstEntry.value).isTrue()
    }

    @Test
    fun `isFirstEntry should emit false when appEntry returns false`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns false

        // When
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.isFirstEntry.value).isFalse()
    }

    @Test
    fun `saveFirstEntry should call appEntry saveFirstEntry`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns true
        coEvery { appEntry.saveFirstEntry() } just runs
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)

        // When
        viewModel.saveFirstEntry()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { appEntry.saveFirstEntry() }
    }

    @Test
    fun `loadInitialData should create all predefined categories`() = runTest {
        // Given
        coEvery { appEntry.isFirstEntry() } returns true
        coEvery { taskCategoryService.createCategory(any()) } just runs
        viewModel = OnBoardingViewModel(taskCategoryService, appEntry)

        // When
        viewModel.loadInitialData()
        advanceUntilIdle()

        // Then
        predefinedCategories.forEach { category ->
            coVerify(exactly = 1) { taskCategoryService.createCategory(category) }
        }
    }
}