package com.example.projectakhirbudaya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.room.BudayaRepository
import com.example.projectakhirbudaya.room.BudayaSavedEntity
import com.example.projectakhirbudaya.room.BudayaSavedRepository

class BudayaSavedViewModel(private val budayaRepository: BudayaSavedRepository) : ViewModel() {

    fun insertBudayaSaved(budayaEntity: BudayaSavedEntity) {
        budayaRepository.insertBudayaSaved(budayaEntity)
    }

    fun getAllBudayaByUserId(userId: String): LiveData<List<BudayaSavedEntity>> {
        return budayaRepository.getAllBudayaByUserId(userId)
    }

    fun deleteBudayaSaved(budayaEntity: BudayaSavedEntity) {
        budayaRepository.deleteBudayaSaved(budayaEntity)
    }

    fun updateBudaya(budayaEntity: BudayaSavedEntity) {
        budayaRepository.updateBudaya(budayaEntity)
    }
}

