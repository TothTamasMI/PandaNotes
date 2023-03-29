package com.example.pandanotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.pandanotes.R
import kotlinx.android.synthetic.main.fragment_start.view.*

class StartFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        view.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_listFragment)
        }

        return view
    }
}