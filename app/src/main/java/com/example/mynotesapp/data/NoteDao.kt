package com.example.mynotesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotesapp.model.Note

@Dao
interface NoteDao {

    //Same user ignore
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_table where userId = :userId")
    suspend fun deleteAllNotes(userId: String)

    //List Page
    @Query("SELECT * FROM note_table WHERE userId = :userId")
    fun getNotesByUserId(userId: String): List<Note>

    //Update
    @Query("SELECT * FROM note_table WHERE id = :noteId")
    fun getNoteById(noteId: Int): LiveData<Note>

    @Query("SELECT * FROM note_table ")
    fun getNotes(): LiveData<List<Note>>

}