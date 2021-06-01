package com.example.mynotesapp.fragments.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.base.BaseApplication
import com.example.mynotesapp.data.UserDatabase
import com.example.mynotesapp.model.User
import com.example.mynotesapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {


    val canUserLogIn = MutableLiveData<Boolean>()
    private val repository: UserRepository

    init {
        val userDao =
            UserDatabase.getDatabase(
                application
            ).userDao()
        repository = UserRepository(userDao)
    }

    fun checkUserIsExist(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val allData = repository.getAllUsers()
            val user: User? = allData.find { it.userName == userName && it.password == password }

            BaseApplication.userName = user?.userName ?: ""
            canUserLogIn.postValue(user != null)
        }
    }
}