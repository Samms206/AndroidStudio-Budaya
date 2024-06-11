package com.example.projectakhirbudaya.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BudayaEntity::class, UserEntity::class, BudayaSavedEntity::class], version = 4)

@TypeConverters(AppConverter::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun budayaDao(): BudayaDao
    abstract fun userDao(): UserDao
    abstract fun budayaSavedDao(): BudayaSavedDao

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
                        .fallbackToDestructiveMigration() //ini ditambahkan jika memiliki leih dari 1 entitiy
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}