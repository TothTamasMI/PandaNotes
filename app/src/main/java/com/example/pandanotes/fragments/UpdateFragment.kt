package com.example.pandanotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pandanotes.R
import com.example.pandanotes.data.Note
import com.example.pandanotes.data.NoteDAO
import com.example.pandanotes.data.NoteDatabase
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {

    private lateinit var noteDb : NoteDatabase
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        noteDb = NoteDatabase.getDatabase(requireContext())

        view.updateEditTextTextNoteBody.setText(args.currentNote.noteBody)
        view.updateEditTextTextNoteTitle.setText(args.currentNote.title)

        view.updateButton.setOnClickListener {
            updateNote()
        }

        view.deleteButton.setOnClickListener {
            deleteNote()
        }

        return view
    }

    private fun deleteNote(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            GlobalScope.launch(Dispatchers.Main) {
                noteDb.noteDao().deleteNote(args.currentNote)
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete ${args.currentNote.title}?")
        builder.setMessage("Are you sure want to delete ${args.currentNote.title}?")
        builder.create().show()
    }

    private fun updateNote(){
        val title = updateEditTextTextNoteTitle.text.toString()
        val body = updateEditTextTextNoteBody.text.toString()

        if(inputCheck(title, body)){
            val updatedNote = Note(args.currentNote.id, title, body)

            GlobalScope.launch(Dispatchers.Main) {
                noteDb.noteDao().updateNote(updatedNote)
            }

            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(noteTitle: String, noteBody: String):Boolean{
        return !(TextUtils.isEmpty(noteTitle) || TextUtils.isEmpty(noteBody))
    }
}