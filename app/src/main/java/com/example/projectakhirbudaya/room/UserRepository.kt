package com.example.projectakhirbudaya.room

import androidx.lifecycle.LiveData
import com.example.projectakhirbudaya.utils.AppExecutors


class UserRepository private constructor(private val userDao: UserDao, private val appExecutors: AppExecutors) {

    fun getAllUser(): LiveData<List<UserEntity>> = userDao.getAllUser()

    fun insertUser(user: UserEntity) {
        appExecutors.diskIO().execute { userDao.insertUser(user) }
    }

    fun login(email: String, password: String, callback: (UserEntity?) -> Unit) {
        appExecutors.diskIO().execute {
            val user = userDao.getUserByEmail(email)
            appExecutors.mainThread().execute {
                if (user != null && user.password == password) {
                    callback(user)
                } else {
                    callback(null)
                }
            }
        }
    }

    fun updateUser(user: UserEntity) {
        appExecutors.diskIO().execute { userDao.updateUser(user) }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userDao: UserDao,
            appExecutors: AppExecutors
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userDao, appExecutors)
        }.also { instance = it }
    }
}
