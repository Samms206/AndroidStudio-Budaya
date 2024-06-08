package com.example.projectakhirbudaya.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudayaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBudaya(budayaEntity: BudayaEntity)

    @Query("SELECT * FROM budayaentity ORDER BY id DESC")
    fun getAllBudaya() : LiveData<List<BudayaEntity>>

    @Delete
    fun deleteBudaya(budayaEntity: BudayaEntity)

    @Update
    fun updateBudaya(budayaEntity: BudayaEntity)
}