package com.example.mynotesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: String,
    val title: String,
    val subject: String,
    var state: NoteState,
    var imagePath: String
) : Parcelable

enum class NoteState {
    Done,
    Doing,
    WillBeDone
}