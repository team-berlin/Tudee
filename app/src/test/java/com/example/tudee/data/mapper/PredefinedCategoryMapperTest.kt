package com.example.tudee.data.mapper

import com.example.tudee.R
import org.junit.Assert.assertEquals
import org.junit.Test

class PredefinedCategoryMapperTest {

    @Test
    fun `should return education drawable ID when icon name is education`() {
        // Given
        val iconName = "education"
        val expectedDrawableId = R.drawable.education

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return shopping drawable ID when icon name is shopping`() {
        // Given
        val iconName = "shopping"
        val expectedDrawableId = R.drawable.shopping

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return medical drawable ID when icon name is medical`() {
        // Given
        val iconName = "medical"
        val expectedDrawableId = R.drawable.medical

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return gym drawable ID when icon name is gym`() {
        // Given
        val iconName = "gym"
        val expectedDrawableId = R.drawable.gym

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return entertainment drawable ID when icon name is entertainment`() {
        // Given
        val iconName = "entertainment"
        val expectedDrawableId = R.drawable.entertainment

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return event drawable ID when icon name is event`() {
        // Given
        val iconName = "event"
        val expectedDrawableId = R.drawable.event

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return work drawable ID when icon name is work`() {
        // Given
        val iconName = "work"
        val expectedDrawableId = R.drawable.work

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return budgeting drawable ID when icon name is budgeting`() {
        // Given
        val iconName = "budgeting"
        val expectedDrawableId = R.drawable.budgeting

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return self_care drawable ID when icon name is Self_care`() {
        // Given
        val iconName = "Self_care"
        val expectedDrawableId = R.drawable.self_care

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return adoration drawable ID when icon name is adoration`() {
        // Given
        val iconName = "adoration"
        val expectedDrawableId = R.drawable.adoration

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return fixing_bugs drawable ID when icon name is fixing_bugs`() {
        // Given
        val iconName = "fixing_bugs"
        val expectedDrawableId = R.drawable.fixing_bugs

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return cleaning drawable ID when icon name is cleaning`() {
        // Given
        val iconName = "cleaning"
        val expectedDrawableId = R.drawable.cleaning

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return travelling drawable ID when icon name is traveling`() {
        // Given
        val iconName = "traveling"
        val expectedDrawableId = R.drawable.travelling

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return agriculture drawable ID when icon name is agriculture`() {
        // Given
        val iconName = "agriculture"
        val expectedDrawableId = R.drawable.agriculture

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return coding drawable ID when icon name is coding`() {
        // Given
        val iconName = "coding"
        val expectedDrawableId = R.drawable.coding

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return coocking drawable ID when icon name is cooking`() {
        // Given
        val iconName = "cooking"
        val expectedDrawableId = R.drawable.coocking

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return family_friend drawable ID when icon name is family_friend`() {
        // Given
        val iconName = "family_friend"
        val expectedDrawableId = R.drawable.family_friend

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(expectedDrawableId, result)
    }

    @Test
    fun `should return -1 when icon name is unknown`() {
        // Given
        val iconName = "unknown_icon"

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(-1, result)
    }

    @Test
    fun `should return -1 when icon name is empty`() {
        // Given
        val iconName = ""

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(-1, result)
    }

    @Test
    fun `should return -1 when icon name has different case`() {
        // Given
        val iconName = "Education"

        // When
        val result = getCategoryIcon(iconName)

        // Then
        assertEquals(-1, result)
    }
}