package geeko.app.navigationjetpack.viewmodel.displayTask

import android.app.Application
import androidx.lifecycle.*
import geeko.app.navigationjetpack.room.AppDatabase
import geeko.app.navigationjetpack.room.TaskRepository
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.launch

/*private var repo: TaskRepository,*/

class DisplayTaskViewModel(application: Application) : AndroidViewModel(application){

    private val repo: TaskRepository
    val allWords: LiveData<List<TaskEntityModel>>
    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).todoDetailDao()
        repo = TaskRepository(wordsDao)
        allWords = repo.allWords
    }

    var allTaskData: LiveData<List<TaskEntityModel>> = repo.getAllTasks()

    suspend fun insertToDb(taskEntityModel: TaskEntityModel){
        repo.insert(taskEntityModel)
    }

    fun getDataFromDbTasks():LiveData<List<TaskEntityModel>>?{
        return allTaskData
    }



}