package com.example.tudee

import com.example.tudee.data.preferences.PreferencesManager
import com.example.tudee.domain.TaskCategoryService
import com.example.tudee.presentation.screen.onboarding.OnBoardingViewModel
import com.example.tudee.presentation.utils.predefinedCategories
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
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
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var taskCategoryService: TaskCategoryService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        preferencesManager = mockk()
        taskCategoryService = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isFirstEntry should emit true when preferences returns true`() = runTest {
        // Given
        every { preferencesManager.isOnboardingCompleted() } returns true

        // When
        viewModel = OnBoardingViewModel(taskCategoryService, preferencesManager)

        // Then
        assertThat(viewModel.isFirstEntry.value).isTrue()
    }

    @Test
    fun `isFirstEntry should emit false when preferences returns false`() = runTest {
        // Given
        every { preferencesManager.isOnboardingCompleted() } returns false

        // When
        viewModel = OnBoardingViewModel(taskCategoryService, preferencesManager)

        // Then
        assertThat(viewModel.isFirstEntry.value).isFalse()
    }

    @Test
    fun `saveFirstEntry should call preferences setOnboardingCompleted`() = runTest {
        // Given
        every { preferencesManager.isOnboardingCompleted() } returns true
        every { preferencesManager.setOnboardingCompleted() } just runs
        viewModel = OnBoardingViewModel(taskCategoryService, preferencesManager)

        // When
        viewModel.saveFirstEntry()

        // Then
        coVerify(exactly = 1) { preferencesManager.setOnboardingCompleted() }
    }

    @Test
    fun `loadInitialData should create all predefined categories`() = runTest {
        // Given
        every { preferencesManager.isOnboardingCompleted() } returns true
        coEvery { taskCategoryService.createCategory(any()) } just runs
        viewModel = OnBoardingViewModel(taskCategoryService, preferencesManager)

        // When
        viewModel.loadInitialData()
        advanceUntilIdle()

        // Then
        predefinedCategories.forEach { category ->
            coVerify(exactly = 1) { taskCategoryService.createCategory(category) }
        }
    }
}