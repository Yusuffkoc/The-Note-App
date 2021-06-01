package com.example.mynotesapp.repository

import com.example.mynotesapp.data.NoteDao
import com.example.mynotesapp.model.Note

class NoteRepository (private val noteDao: NoteDao) {

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    fun getNoteById(id: Int) {
        noteDao.getNoteById(id)
    }

    fun getNotesByUser(userId: String) : List<Note>{
        return noteDao.getNotesByUserId(userId)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

}