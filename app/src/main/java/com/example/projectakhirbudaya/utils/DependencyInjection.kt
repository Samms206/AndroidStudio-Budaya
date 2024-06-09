package com.example.projectakhirbudaya.utils

import android.content.Context
import com.example.projectakhirbudaya.room.AppDatabase
import com.example.projectakhirbudaya.room.BudayaRepository
import com.example.projectakhirbudaya.room.UserRepository

object DependencyInjection {
    fun provideRepository(context: Context): BudayaRepository {
        val database = AppDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        val dao = database.budayaDao()
        return BudayaRepository.getInstance(dao, appExecutors)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val database = AppDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        val dao = database.userDao()
        return UserRepository.getInstance(dao, appExecutors)
    }
}