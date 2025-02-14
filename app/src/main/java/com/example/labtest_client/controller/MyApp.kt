package com.example.labtest_client.controller

import android.app.Application
import android.os.Handler
import com.example.labtest_client.Util

class MyApp :Application{

    constructor() :super()

    companion object{

        private val TAG :String = this.javaClass.name
        private lateinit var instance :MyApp
        private var mWorkerMainThread :WorkerMainThread? = null

        fun getWorkerMainHandler() :Handler {
            if(mWorkerMainThread == null){
                createWorkerMainThread()
            }
            return mWorkerMainThread!!.getHandler()
        }

        private fun createWorkerMainThread(){
            mWorkerMainThread = WorkerMainThread("WorkerMainThread")
            mWorkerMainThread?.start()
        }
    }


    override fun onCreate() {
        super.onCreate()
        createWorkerMainThread()
        initUtil()
    }





    private fun initUtil(){
        Util.setNavBarHeight(getNavBarHeight())
        Util.setStatusBarHeight(getStatusBarHeight())
        Util.setDP12((resources.displayMetrics.density * 12).toInt())
        Util.setDP8((resources.displayMetrics.density * 8).toInt())
        Util.setDP40((resources.displayMetrics.density * 40).toInt())
        Util.setDP50((resources.displayMetrics.density * 50).toInt())
    }

    private fun getNavBarHeight() :Int?{
        val resourceId :Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if(resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
        return null
    }

    private fun getStatusBarHeight() :Int?{
        val resourceId :Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if(resourceId > 0){
            return resources.getDimensionPixelSize(resourceId)
        }
        return null
    }
}