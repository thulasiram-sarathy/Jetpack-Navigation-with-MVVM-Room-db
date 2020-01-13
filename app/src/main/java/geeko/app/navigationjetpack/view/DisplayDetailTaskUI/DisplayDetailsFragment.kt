package geeko.app.navigationjetpack.view.DisplayDetailTaskUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import geeko.app.navigationjetpack.R
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import geeko.app.navigationjetpack.view.DisplayDetailTaskUI.DisplayDetailsFragmentArgs.fromBundle
import geeko.app.navigationjetpack.view.DisplayDetailTaskUI.DisplayDetailsFragmentDirections.actionNoteDetailToDeleteNote
import geeko.app.navigationjetpack.view.DisplayDetailTaskUI.DisplayDetailsFragmentDirections.actionNoteDetailToEditNote
import geeko.app.navigationjetpack.viewmodel.DetailTask.DisplayTaskDetailViewModel
import kotlinx.android.synthetic.main.note_detail_fragment.*

class DisplayDetailsFragment : Fragment(){


    private lateinit var viewModel: DisplayTaskDetailViewModel

    private val noteId by lazy {
        arguments?.let { fromBundle(it).noteId } ?: throw IllegalArgumentException("Expected arguments")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DisplayTaskDetailViewModel::class.java)
        viewModel.observableNote.observe(this, Observer { note ->
            note?.let { render(note) } ?: renderNoteNotFound()
        })

        editNoteButton.setOnClickListener {
            val navDirections = actionNoteDetailToEditNote(noteId)
            Navigation.findNavController(it).navigate(navDirections)
        }

        deleteNoteButton.setOnClickListener {
            val navDirections = actionNoteDetailToDeleteNote(noteId)
            Navigation.findNavController(it).navigate(navDirections)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTask(noteId)
    }

    private fun render(note: TaskEntityModel) {
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.task_name)
    }

    private fun renderNoteNotFound() {
        noteIdView.visibility = View.GONE
        noteText.visibility = View.GONE
        view?.let {
            Snackbar.make(it, R.string.error_loading_note, Snackbar.LENGTH_LONG).show()
        }
    }

}