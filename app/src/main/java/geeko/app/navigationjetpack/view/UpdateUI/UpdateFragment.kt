package geeko.app.navigationjetpack.view.UpdateUI

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import geeko.app.navigationjetpack.R
import geeko.app.navigationjetpack.closeSoftKeyboard
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import geeko.app.navigationjetpack.view.UpdateUI.UpdateFragmentArgs.fromBundle
import geeko.app.navigationjetpack.viewmodel.updateTask.UpdateTaskViewModel
import kotlinx.android.synthetic.main.updatetask_fragment.*

class UpdateFragment : Fragment(){


    private lateinit var viewModel: UpdateTaskViewModel

    private val noteId by lazy {
        arguments?.let { fromBundle(it).noteId } ?: throw IllegalArgumentException("Expected arguments")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.updatetask_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UpdateTaskViewModel::class.java)
        viewModel.observableCurrentNote.observe(this, Observer { currentNote ->
            currentNote?.let { initCurrentNote(currentNote) }
        })
        viewModel.observableEditStatus.observe(this, Observer { editStatus ->
            editStatus?.let { render(editStatus) }
        })

        viewModel.initNote(noteId)

//        setupEditNoteSubmitHandling()


        button_edit.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNoteText.text)) {
                editNoteText.setError("empty")
            } else if (TextUtils.isEmpty(editDateText.text)) {
                editDateText.setError("empty")
            } else if (TextUtils.isEmpty(editToDateText.text)) {
                editToDateText.setError("empty")
            }else if (TextUtils.isEmpty(edittaskByText.text)) {
                edittaskByText.setError("empty")
            }else {

                viewModel.updateTask(noteId,editNoteText.text.toString(),edittaskByText.text.toString(),editDateText.text.toString(),editToDateText.text.toString())
                it.closeSoftKeyboard()
            }

        }
    }

    private fun initCurrentNote(note: TaskEntityModel) {
        editNoteText.setText(note.task_name)
    }

    private fun render(editStatus: Boolean) {
        when (editStatus) {
            true -> {
                view?.let {
                    Navigation.findNavController(it).popBackStack()
                }
            }
            false -> editNoteText.error = getString(R.string.error_validating_note)
        }
    }



/*    private fun setupEditNoteSubmitHandling() {
        editNoteText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                viewModel.updateTask(noteId, v.text.toString())
                v.closeSoftKeyboard()
                true
            } else {
                false
            }
        }
    }*/

}