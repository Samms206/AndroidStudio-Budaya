package com.example.projectakhirbudaya.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BudayaEntity::class], version = 1)

@TypeConverters(AppConverter::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun budayaDao(): BudayaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    )
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}