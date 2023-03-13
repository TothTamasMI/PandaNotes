package com.example.pandanotes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Note>>
}