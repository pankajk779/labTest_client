package com.example.labtest_client.controller

import android.os.Handler
import android.os.Looper

class WorkerMainThread :Thread{

    private val TAG :String = this.javaClass.name
    private lateinit var mHandler :Handler

    constructor()
    constructor(name :String) :super(name)

    override fun run() {
        Looper.prepare()
        mHandler = Handler(Looper.myLooper()!!)
        Looper.loop()
    }

    fun getHandler() :Handler{
        return mHandler
    }
}