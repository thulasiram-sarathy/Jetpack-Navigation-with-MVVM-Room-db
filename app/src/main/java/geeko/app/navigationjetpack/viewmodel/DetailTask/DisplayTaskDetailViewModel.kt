package geeko.app.navigationjetpack.viewmodel.DetailTask

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import geeko.app.navigationjetpack.room.AppDatabase
import geeko.app.navigationjetpack.room.TaskRepository
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.launch

class DisplayTaskDetailViewModel (application: Application) : AndroidViewModel(application){

    private val note = MutableLiveData<TaskEntityModel>()
    private val repo: TaskRepository
    val observableNote: LiveData<TaskEntityModel>
        get() = note

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).todoDetailDao()
        repo = TaskRepository(wordsDao)
    }

    fun getTask(id: Int)= viewModelScope.launch {
        note.value = repo.getTask(id)
    }
}