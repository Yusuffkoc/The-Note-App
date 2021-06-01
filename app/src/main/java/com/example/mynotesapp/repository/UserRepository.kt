package com.example.mynotesapp.repository

import com.example.mynotesapp.data.UserDao
import com.example.mynotesapp.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun getAllUsers() : List<User> {
        return userDao.getAllUsers()
    }
}