package com.example.projectakhirbudaya.room

import androidx.lifecycle.LiveData
import com.example.projectakhirbudaya.utils.AppExecutors

class BudayaRepository private constructor(private val budayaDao: BudayaDao, private val appExecutors: AppExecutors) {

    fun getAllBudaya(): LiveData<List<BudayaEntity>> = budayaDao.getAllBudaya()

    fun insertBudaya(budaya: BudayaEntity) {
        appExecutors.diskIO().execute { budayaDao.insertBudaya(budaya) }
    }

    fun deleteBudaya(budaya: BudayaEntity) {
        appExecutors.diskIO().execute { budayaDao.deleteBudaya(budaya) }
    }

    fun updateBudaya(budaya: BudayaEntity) {
        appExecutors.diskIO().execute { budayaDao.updateBudaya(budaya) }
    }

    companion object {
        @Volatile
        private var instance: BudayaRepository? = null

        fun getInstance(
            budayaDao: BudayaDao,
            appExecutors: AppExecutors
        ): BudayaRepository = instance ?: synchronized(this) {
                instance ?: BudayaRepository(budayaDao, appExecutors)
            }.also { instance = it }
    }
}