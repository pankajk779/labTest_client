package com.example.labtest_client

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel :ViewModel {

    private val TAG :String = this.javaClass.name
    private var preName :String? = null
    private var preGender :String? = null

    val _holderList1 : MutableLiveData<ArrayList<RvWelcome2Adapter.MyViewHolderWelcome2>> = MutableLiveData(null)
    val holderList : LiveData<ArrayList<RvWelcome2Adapter.MyViewHolderWelcome2>> = _holderList1

    val _holderList :MutableLiveData<ArrayList<HolderList>> = MutableLiveData(null)



    //TODO context leak
    var _freagmentTestLayout :View? = null
    var bottomNavView :View? = null
    var fragmentHomeLayout : View? = null
    var bitmapMicroscope :Bitmap? = null
    var bitmapHomeCollection :Bitmap? = null
    var bitmapLogin :Bitmap? = null
    var bitmapFruitsTable :Bitmap? = null

    val bitmapList :ArrayList<Bitmap> = ArrayList()

    constructor()

    fun setPreName(name :String){
        preName = name
    }

    fun setPreGender(gender :String){
        preGender = gender
    }

    fun getPreName() :String?{
        return preName
    }

    fun getPreGender() :String?{
        return preGender
    }
}