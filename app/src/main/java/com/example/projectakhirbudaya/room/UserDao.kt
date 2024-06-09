package com.example.projectakhirbudaya.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userentity ORDER BY id ASC")
    fun getAllUser() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM userentity WHERE email = :email LIMIT 1")
    fun getUserByEmail(email: String): UserEntity?

    @Update
    fun updateUser(user: UserEntity)
}
