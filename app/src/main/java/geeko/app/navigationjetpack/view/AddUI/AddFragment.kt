package geeko.app.navigationjetpack.view.AddUI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import geeko.app.navigationjetpack.R
import geeko.app.navigationjetpack.closeSoftKeyboard
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import geeko.app.navigationjetpack.viewmodel.addTask.AddTaskViewModel
import kotlinx.android.synthetic.main.addtask_fragment.*

class AddFragment : Fragment(){

    private lateinit var viewModel: AddTaskViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.addtask_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddTaskViewModel::class.java)
        viewModel.observableStatus.observe(this, Observer { status ->
            status?.let { render(status) }
        })

        var sample = TaskEntityModel("","","","")

        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(addNoteText.text)) {
                addNoteText.setError("empty")
            } else if (TextUtils.isEmpty(addDateText.text)) {
                addDateText.setError("empty")
            } else if (TextUtils.isEmpty(addToDateText.text)) {
                addToDateText.setError("empty")
            }else if (TextUtils.isEmpty(taskByDateText.text)) {
                taskByDateText.setError("empty")
            }else {
                sample = TaskEntityModel(addNoteText.text.toString(),addDateText.text.toString(),addToDateText.text.toString(),taskByDateText.text.toString())
                viewModel.insert(sample)
                it.closeSoftKeyboard()
            }

        }

/*        addNoteText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                viewModel.insertToDb(sample)
                v.closeSoftKeyboard()
                true
            } else {
                false
            }
        }*/
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    Navigation.findNavController(it).popBackStack()
                }
            }
            false -> addNoteText.error = getString(R.string.error_validating_note)
        }
    }

}