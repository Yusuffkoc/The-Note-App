package com.example.mynotesapp.fragments.updates

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.UserDatabase
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.model.User
import com.example.mynotesapp.repository.NoteRepository
import com.example.mynotesapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    init {
        val noteDao =
            UserDatabase.getDatabase(
                application
            ).noteDao()
        repository = NoteRepository(noteDao)
    }

    fun getNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoteById(id)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

}