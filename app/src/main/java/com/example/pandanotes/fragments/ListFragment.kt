package com.example.pandanotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pandanotes.ListAdapter
import com.example.pandanotes.R
import com.example.pandanotes.data.NoteDatabase
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_list2.view.*
import kotlinx.android.synthetic.main.note_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private lateinit var noteDb : NoteDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            //Disable to go back to StartFragment
        }

        val view = inflater.inflate(R.layout.fragment_list2, container, false)
        noteDb = NoteDatabase.getDatabase(requireContext())

        val adapter = ListAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        GlobalScope.launch(Dispatchers.Main) {
            noteDb.noteDao().readAllData().observe(viewLifecycleOwner, Observer {note ->
                adapter.setData(note)
            })
        }

        view.plusButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }

}