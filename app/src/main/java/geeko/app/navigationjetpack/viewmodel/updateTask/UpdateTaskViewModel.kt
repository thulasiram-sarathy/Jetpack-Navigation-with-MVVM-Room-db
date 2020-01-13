package geeko.app.navigationjetpack.viewmodel.updateTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import geeko.app.navigationjetpack.room.AppDatabase
import geeko.app.navigationjetpack.room.TaskRepository
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.launch

class UpdateTaskViewModel (application: Application) : AndroidViewModel(application){


    private val currentTask = MutableLiveData<TaskEntityModel>()
    private val repo: TaskRepository
    private val editStatus = MutableLiveData<Boolean>()

    val observableCurrentNote: LiveData<TaskEntityModel>
        get() = currentTask

    val observableEditStatus: LiveData<Boolean>
        get() = editStatus

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).todoDetailDao()
        repo = TaskRepository(wordsDao)
    }

    fun updateTask(id: Int, taskName: String,taskBy:String,taskFromDate:String,taskToDate:String)= viewModelScope.launch {
        editStatus.value = try {
            repo.updateTask(id, taskName,taskBy,taskFromDate,taskToDate)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun initNote(id: Int)= viewModelScope.launch {
        currentTask.value = repo.getTask(id)
    }
}