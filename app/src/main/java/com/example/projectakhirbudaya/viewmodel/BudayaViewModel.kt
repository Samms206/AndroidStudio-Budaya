package com.example.projectakhirbudaya.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.projectakhirbudaya.room.BudayaEntity
import com.example.projectakhirbudaya.room.BudayaRepository

class BudayaViewModel(private val budayaRepository: BudayaRepository) : ViewModel() {

    fun insertBudaya(budayaEntity: BudayaEntity) {
        budayaRepository.insertBudaya(budayaEntity)
    }

    fun getAllBudaya(): LiveData<List<BudayaEntity>> {
        return budayaRepository.getAllBudaya()
    }

    fun deleteBudaya(budayaEntity: BudayaEntity) {
        budayaRepository.deleteBudaya(budayaEntity)
    }

    fun updateBudaya(budayaEntity: BudayaEntity) {
        budayaRepository.updateBudaya(budayaEntity)
    }
}