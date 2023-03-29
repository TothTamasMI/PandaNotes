package com.example.pandanotes.fragments

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pandanotes.R
import com.example.pandanotes.StartingActivity
import com.example.pandanotes.data.Note
import com.example.pandanotes.data.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.coroutines.*

class AddFragment : Fragment() {
    private lateinit var noteDb : NoteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        noteDb = NoteDatabase.getDatabase(requireContext())

        view.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val noteTitle = editTextTextNoteTitle.text.toString()
        val noteBody = editTextTextNoteBody.text.toString()

        if(inputCheck(noteTitle, noteBody)){
            val note = Note(0, noteTitle, noteBody )


            GlobalScope.launch(Dispatchers.Main) {
                noteDb.noteDao().addNote(note)
            }

            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(noteTitle: String, noteBody: String):Boolean{
        return !(TextUtils.isEmpty(noteTitle) || TextUtils.isEmpty(noteBody))
    }
}