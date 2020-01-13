package geeko.app.navigationjetpack.view.DisplayUI

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.calculateDiff
import androidx.recyclerview.widget.RecyclerView
import geeko.app.navigationjetpack.R
import geeko.app.navigationjetpack.room.entity.TaskEntityModel


typealias ClickListener = (TaskEntityModel) -> Unit

class DisplayTaskAdapter(
        private val clickListener: ClickListener
) : RecyclerView.Adapter<DisplayTaskAdapter.ViewHolder>() {

    private var noteList = emptyList<TaskEntityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemContainer = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false) as ViewGroup
        val viewHolder = ViewHolder(itemContainer)
        itemContainer.setOnClickListener {
            clickListener(noteList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.id.text = note.id.toString()
        holder.text.text = note.task_name
    }

    override fun getItemCount() = noteList.size

    fun updateNotes(noteList: List<TaskEntityModel>) {
        val diffResult = calculateDiff(TaskDiffCallback(this.noteList, noteList))
        this.noteList = noteList
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemViewGroup: ViewGroup) : RecyclerView.ViewHolder(itemViewGroup) {
        val id: TextView = itemViewGroup.findViewById(R.id.noteId)
        val text: TextView = itemViewGroup.findViewById(R.id.noteText)
    }
}