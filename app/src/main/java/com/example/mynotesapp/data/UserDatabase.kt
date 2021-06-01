package com.example.mynotesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.model.User

@Database(entities = [User::class, Note::class],version = 3,exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract  fun userDao():UserDao
    abstract  fun noteDao():NoteDao

    companion object{
        @Volatile
        private var INSTANCE:UserDatabase?=null

        fun getDatabase(context: Context):UserDatabase{
            val tempInstance= INSTANCE
            if (tempInstance != null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"


                ).build()
                INSTANCE=instance
                return  instance
            }
        }
    }


}