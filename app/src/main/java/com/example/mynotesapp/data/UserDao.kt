package com.example.mynotesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotesapp.model.User

@Dao
interface UserDao {

    //Same user ignore
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user:User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table")
    fun readAllData():LiveData<List<User>>

    @Query("SELECT * FROM user_table")
    fun getAllUsers():List<User>

}