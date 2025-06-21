package com.example.tudee.domain

import com.example.tudee.data.dao.AppEntryDao
import com.example.tudee.data.model.AppEntryEntity

class AppEntryImpl(
    private val appEntryDao: AppEntryDao
) : AppEntry {
    override suspend fun saveFirstEntry() {
        appEntryDao.insertAppEntry(AppEntryEntity())
    }

    override suspend fun isFirstEntry(): Boolean {
        return appEntryDao.getAppEntry() == null
    }
}