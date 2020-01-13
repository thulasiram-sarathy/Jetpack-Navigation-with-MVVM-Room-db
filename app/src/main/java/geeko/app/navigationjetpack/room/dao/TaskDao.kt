package geeko.app.navigationjetpack.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import geeko.app.navigationjetpack.room.entity.TaskEntityModel

@Dao
interface TaskDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(experienceDetailModel: TaskEntityModel)

    @Delete
    fun deleteTask(experienceEntityModel: TaskEntityModel)

    @Query("SELECT * from notes_todo")
    fun getAllTasks():LiveData<List<TaskEntityModel>>

    /*@Update
    fun updateTask(vararg experienceEntityModel: TaskEntityModel)*/

    @Query("DELETE FROM notes_todo WHERE id = :Id")
    suspend fun deleteByUserId(Id: Int)

    @Query("SELECT * from notes_todo WHERE id = :Id")
    suspend fun getTask(Id: Int):TaskEntityModel

    @Query("UPDATE notes_todo SET task_name=:taskName,fromDate=:taskFromDate,toDate=:taskToDate,task_by=:taskBy WHERE id = :id")
    suspend fun update(taskName: String?, id: Int,taskBy: String,taskFromDate: String?, taskToDate: String?)
}

