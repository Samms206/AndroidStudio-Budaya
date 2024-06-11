package com.example.projectakhirbudaya.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectakhirbudaya.room.BudayaRepository
import com.example.projectakhirbudaya.room.BudayaSavedRepository
import com.example.projectakhirbudaya.utils.DependencyInjection


class BudayaSavedViewModelFactory private constructor(private val budayaRepository: BudayaSavedRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BudayaSavedViewModel::class.java)) {
            return BudayaSavedViewModel(budayaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: BudayaSavedViewModelFactory? = null
        fun getInstance(context: Context): BudayaSavedViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: BudayaSavedViewModelFactory(DependencyInjection.provideBudayaSavedRepository(context))
            }.also { instance = it }
    }
}