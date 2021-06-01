package com.example.mynotesapp.fragments.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.UserDatabase
import com.example.mynotesapp.model.User
import com.example.mynotesapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel (application: Application) : AndroidViewModel(application){
    val canUserRegister = MutableLiveData<Boolean>()

    private val repository: UserRepository

    init {
        val userDao=
            UserDatabase.getDatabase(
                application
            ).userDao()
        repository= UserRepository(userDao)
    }

    fun checkUserCanRegister(userName: String, password: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val allData = repository.getAllUsers()
            val user: User? = allData.find { it.userName == userName && it.password == password }

            canUserRegister.postValue(user == null)
        }
    }

    fun createNewUser(user: User) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addUser(user)
        }
    }
}