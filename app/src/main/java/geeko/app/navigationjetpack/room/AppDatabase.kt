package geeko.app.navigationjetpack.room

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import geeko.app.navigationjetpack.room.dao.TaskDao
import geeko.app.navigationjetpack.room.entity.TaskEntityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TaskEntityModel::class],version = 1,exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun todoDetailDao(): TaskDao


/*    companion object{
        private var instance: AppDatabase?=null
        fun getInstance(context: Context): AppDatabase?{
            if(instance ==null){
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,"todo_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

    }*/

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.todoDetailDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(notesDao: TaskDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
//            wordDao.deleteAll()
Log.v("populateDatabase", " "+ "ssfs")
            var task = TaskEntityModel("task one","13-01-2020","","Ram")
            notesDao.insertTask(task)
            task = TaskEntityModel("task two","13-01-20202","","Mith")
            notesDao.insertTask(task)
        }
    }



}