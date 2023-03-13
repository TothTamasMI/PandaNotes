package com.example.pandanotes.data

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDAO: NoteDAO) {

    val readAllData: LiveData<List<Note>> = noteDAO.readAllData()

    suspend fun addNote(note: Note){
        noteDAO.addNote(note)
    }
}