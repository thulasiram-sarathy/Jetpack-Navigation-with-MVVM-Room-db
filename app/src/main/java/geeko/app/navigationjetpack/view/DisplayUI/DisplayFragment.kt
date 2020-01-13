package geeko.app.navigationjetpack.view.DisplayUI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import geeko.app.navigationjetpack.R
import kotlinx.android.synthetic.main.note_list_fragment.*
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import geeko.app.navigationjetpack.room.TaskRepository
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import geeko.app.navigationjetpack.view.DisplayUI.DisplayFragmentDirections.actionNotesToAddNote
import geeko.app.navigationjetpack.view.DisplayUI.DisplayFragmentDirections.actionNotesToNoteDetail
import geeko.app.navigationjetpack.viewmodel.displayTask.DisplayTaskViewModel


class DisplayFragment : Fragment() {

    private val clickListener: ClickListener = this::onNoteClicked

    private val recyclerViewAdapter = DisplayTaskAdapter(clickListener)

    private lateinit var viewModel: DisplayTaskViewModel
    var taskDetailRepos: TaskRepository?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProviders.of(this).get(DisplayTaskViewModel::class.java)
        viewModel.allTaskData.observe(this, Observer { notes ->
            notes?.let { render(notes) }
        })

        fab.setOnClickListener {
            findNavController(it).navigate(actionNotesToAddNote())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDataFromDbTasks()
    }

    private fun render(noteList: List<TaskEntityModel>) {

        recyclerViewAdapter.updateNotes(noteList)
        if (noteList.isEmpty()) {
            notesRecyclerView.visibility = View.GONE
            notesNotFound.visibility = View.VISIBLE
        } else {
            notesRecyclerView.visibility = View.VISIBLE
            notesNotFound.visibility = View.GONE
        }
    }

    private fun onNoteClicked(note: TaskEntityModel) {
        val navDirections = actionNotesToNoteDetail(note.id)
        view?.let {
            findNavController(it).navigate(navDirections)
        }
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(this.context)
        notesRecyclerView.adapter = recyclerViewAdapter
        notesRecyclerView.setHasFixedSize(true)
    }
}