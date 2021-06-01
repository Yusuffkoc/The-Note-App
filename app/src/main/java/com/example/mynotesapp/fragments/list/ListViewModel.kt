package com.example.mynotesapp.fragments.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.UserDatabase
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.model.NoteState
import com.example.mynotesapp.model.User
import com.example.mynotesapp.repository.NoteRepository
import com.example.mynotesapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    val notesToShow: MutableLiveData<List<Note>> = MutableLiveData()
    private val allNotes: ArrayList<Note> = ArrayList()
    private var lastCheckedFilter : NoteState? = null

    init {
        val noteDao =
            UserDatabase.getDatabase(
                application
            ).noteDao()
        repository = NoteRepository(noteDao)
    }

    fun getNotesByUser(userId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            allNotes.clear()
            allNotes.addAll(repository.getNotesByUser(userId))
            filterNotes(lastCheckedFilter)
        }
    }

    fun filterNotes(state: NoteState?) {
        lastCheckedFilter = state
        if (state == null) {
            notesToShow.postValue(allNotes)
        } else {
            notesToShow.postValue(allNotes.filter { it.state == state })
        }
    }
}