package com.example.pandanotes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNote()

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun readAllData(): LiveData<List<Note>>
}