package geeko.app.navigationjetpack.viewmodel.deleteTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import geeko.app.navigationjetpack.room.AppDatabase
import geeko.app.navigationjetpack.room.TaskRepository
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.launch

class DeleteTaskViewModel (application: Application) : AndroidViewModel(application){

    private val currentTask = MutableLiveData<TaskEntityModel>()

    private val deleteStatus = MutableLiveData<Boolean>()
    private val repo: TaskRepository
    val observableCurrentNote: LiveData<TaskEntityModel>
        get() = currentTask

    val observableDeleteStatus: LiveData<Boolean>
        get() = deleteStatus

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).todoDetailDao()
        repo = TaskRepository(wordsDao)
    }

     fun deleteTask(id: Int)= viewModelScope.launch  {
        deleteStatus.value = try {
            repo.delete(id)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

     fun initNote(id: Int)= viewModelScope.launch  {
        currentTask.value = repo.getTask(id)
    }

}