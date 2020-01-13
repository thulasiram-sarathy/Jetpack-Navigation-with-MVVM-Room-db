package geeko.app.navigationjetpack.viewmodel.addTask

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import geeko.app.navigationjetpack.room.AppDatabase
import geeko.app.navigationjetpack.room.TaskRepository
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.launch

class AddTaskViewModel(application: Application) : AndroidViewModel(application){

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    private val repo: TaskRepository
    val allWords: LiveData<List<TaskEntityModel>>

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).todoDetailDao()
        repo = TaskRepository(wordsDao)
        allWords = repo.allWords
    }

    fun insert(taskEntityModel: TaskEntityModel) = viewModelScope.launch {
        status.value = try {
            repo.insert(taskEntityModel)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

}