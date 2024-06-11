package com.example.projectakhirbudaya.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudayaSavedDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBudayaSaved(budayaEntity: BudayaSavedEntity)

    @Query("SELECT * FROM budayasavedentity WHERE idUser = :userId ORDER BY id DESC")
    fun getAllBudayaByUserId(userId: String) : LiveData<List<BudayaSavedEntity>>

    @Delete
    fun deleteBudayaSaved(budayaEntity: BudayaSavedEntity)

    @Update
    fun updateBudaya(budayaEntity: BudayaSavedEntity)
}