package geeko.app.navigationjetpack.view.DeleteUI

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
import geeko.app.navigationjetpack.view.DisplayDetailTaskUI.DisplayDetailsFragmentArgs
import geeko.app.navigationjetpack.viewmodel.deleteTask.DeleteTaskViewModel
import kotlinx.android.synthetic.main.deletetask_fragment.*

class DeleteFragment : Fragment(){
    private lateinit var viewModel: DeleteTaskViewModel

    private val noteId by lazy {
        arguments?.let { DisplayDetailsFragmentArgs.fromBundle(it).noteId } ?: throw IllegalArgumentException("Expected arguments")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.deletetask_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeleteTaskViewModel::class.java)
        viewModel.observableCurrentNote.observe(this, Observer { currentNote ->
            currentNote?.let { initCurrentNote(currentNote) }
        })
        viewModel.observableDeleteStatus.observe(this, Observer { deleteStatus ->
            deleteStatus?.let { render(deleteStatus) }
        })

        viewModel.initNote(noteId)

        cancelDeleteButton.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        confirmDeleteButton.setOnClickListener {
            viewModel.deleteTask(noteId)
        }
    }

    private fun initCurrentNote(note: TaskEntityModel) {
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.task_name)
    }

    private fun render(deleteStatus: Boolean) {
        when (deleteStatus) {
            true -> {
                view?.let {
                    Navigation.findNavController(it).popBackStack(R.id.notesFragment, false)
                }
            }
            false -> Snackbar.make(confirmDeleteButton, R.string.error_deleting_note, Snackbar.LENGTH_LONG).show()
        }
    }
}