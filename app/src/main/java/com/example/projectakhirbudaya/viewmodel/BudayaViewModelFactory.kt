package com.example.projectakhirbudaya.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirbudaya.room.BudayaRepository
import com.example.projectakhirbudaya.utils.DependencyInjection


class BudayaViewModelFactory private constructor(private val budayaRepository: BudayaRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BudayaViewModel::class.java)) {
            return BudayaViewModel(budayaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: BudayaViewModelFactory? = null
        fun getInstance(context: Context): BudayaViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BudayaViewModelFactory(DependencyInjection.provideRepository(context))
            }.also { instance = it }
    }
}