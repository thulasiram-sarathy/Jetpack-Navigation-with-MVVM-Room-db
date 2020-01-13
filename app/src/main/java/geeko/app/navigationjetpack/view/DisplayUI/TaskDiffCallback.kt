package geeko.app.navigationjetpack.view.DisplayUI

import androidx.recyclerview.widget.DiffUtil
import geeko.app.navigationjetpack.room.entity.TaskEntityModel

class TaskDiffCallback(private val old: List<TaskEntityModel>,
                       private val new: List<TaskEntityModel>) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex].task_name == new[newIndex].task_name
    }

    override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex] == new[newIndex]
    }
}