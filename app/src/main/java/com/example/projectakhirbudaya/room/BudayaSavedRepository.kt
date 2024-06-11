package com.example.projectakhirbudaya.room

import androidx.lifecycle.LiveData
import com.example.projectakhirbudaya.utils.AppExecutors

class BudayaSavedRepository private constructor(private val budayaDao: BudayaSavedDao, private val appExecutors: AppExecutors) {

    fun getAllBudayaByUserId(userId: String): LiveData<List<BudayaSavedEntity>> = budayaDao.getAllBudayaByUserId(userId)

    fun insertBudayaSaved(budaya: BudayaSavedEntity) {
        appExecutors.diskIO().execute { budayaDao.insertBudayaSaved(budaya) }
    }

    fun deleteBudayaSaved(budaya: BudayaSavedEntity) {
        appExecutors.diskIO().execute { budayaDao.deleteBudayaSaved(budaya) }
    }

    fun updateBudaya(budaya: BudayaSavedEntity) {
        appExecutors.diskIO().execute { budayaDao.updateBudaya(budaya) }
    }

    companion object {
        @Volatile
        private var instance: BudayaSavedRepository? = null

        fun getInstance(
            budayaDao: BudayaSavedDao,
            appExecutors: AppExecutors
        ): BudayaSavedRepository = instance ?: synchronized(this) {
                instance ?: BudayaSavedRepository(budayaDao, appExecutors)
            }.also { instance = it }
    }
}