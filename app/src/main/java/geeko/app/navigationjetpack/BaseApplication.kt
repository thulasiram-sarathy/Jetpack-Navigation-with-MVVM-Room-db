package geeko.app.navigationjetpack

import android.app.Activity
import android.app.Application
import android.content.Context


class BaseApplication : Application() {

    companion object{

        fun get(context: Context):BaseApplication{
            return context.applicationContext as BaseApplication
        }
        fun create(context: Context):BaseApplication{
            return get(context)
        }

    }



}