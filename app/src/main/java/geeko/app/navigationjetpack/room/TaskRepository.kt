package geeko.app.navigationjetpack.room

import android.app.Application
import androidx.lifecycle.LiveData
import geeko.app.navigationjetpack.BaseApplication
import geeko.app.navigationjetpack.room.dao.TaskDao
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.CoroutineScope


class TaskRepository(private val taskDao: TaskDao){

    private var taskDetailDao: TaskDao?=null
    private var allTaskData: LiveData<List<TaskEntityModel>>?=null
    val allWords: LiveData<List<TaskEntityModel>> = taskDao.getAllTasks()
    var singleTask: LiveData<TaskEntityModel>?=null
/*    init {
        val database = AppDatabase.getDatabase(application.applicationContext,viewModelScope )
        taskDetailDao = database.todoDetailDao()
        allTaskData = taskDetailDao?.getAllTasks()


    }*/

    suspend fun insert(taskEntityModel: TaskEntityModel) {
        taskDao?.insertTask(taskEntityModel)
    }

    fun getAllTasks():LiveData<List<TaskEntityModel>>{
        return allWords
    }

    suspend fun delete(id : Int){
        taskDao.deleteByUserId(id)
    }

    suspend fun getTask(id : Int):TaskEntityModel{
        return taskDao.getTask(id)
    }

    suspend fun updateTask(id : Int, taskname:String, taskBy:String,taskFromDate:String,taskToDate:String){
        taskDao.update(taskname,id,taskBy,taskFromDate, taskToDate)
    }
}