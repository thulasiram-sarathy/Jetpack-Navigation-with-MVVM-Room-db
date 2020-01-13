package geeko.app.navigationjetpack.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_todo")
data class TaskEntityModel(var task_name:String?, var fromDate:String?, var toDate:String?, var task_by:String?){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}