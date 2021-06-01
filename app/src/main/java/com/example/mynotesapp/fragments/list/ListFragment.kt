package com.example.mynotesapp.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotesapp.R
import com.example.mynotesapp.base.BaseApplication
import com.example.mynotesapp.model.Note
import com.example.mynotesapp.model.NoteState
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var filterRadioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.onItemClick = { item -> onNoteItemClicked(item) }

        //ViewModel
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        viewModel.getNotesByUser(BaseApplication.userName)
        viewModel.notesToShow.observe(viewLifecycleOwner, Observer { notes ->

            adapter.setData(notes ?: emptyList())
        })

        filterRadioGroup = view.filterRG

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setRadioGroup()

        return view
    }

    private fun setRadioGroup() {
        filterRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.allRB -> {
                    viewModel.filterNotes(null)
                }
                R.id.doneRB -> {
                    viewModel.filterNotes(NoteState.Done)
                }
                R.id.tbdRB -> {
                    viewModel.filterNotes(NoteState.WillBeDone)
                }
                R.id.ongoingRB -> {
                    viewModel.filterNotes(NoteState.Doing)
                }
            }
        }
    }

    private fun onNoteItemClicked(item: Note) {
        val bundle = Bundle()
        bundle.putParcelable("clickedNote", item)
        findNavController().navigate(R.id.action_listFragment_to_updateFragment, bundle)
    }
}