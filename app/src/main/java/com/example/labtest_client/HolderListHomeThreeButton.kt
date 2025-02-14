package com.example.labtest_client

import com.example.labtest_client.controller.MyApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HolderListHomeThreeButton :HolderList {

    private val viewType :Int
    private val oList :ArrayList<Vh>

    constructor(viewType :Int, oList :ArrayList<Vh>) : super(viewType, oList) {
        this.viewType = viewType
        this.oList = oList
    }

    override fun getViewHolder() :Vh?{

        if(oList.size <= 3){
            createMoreViewHolders()
        }
        try {
            return oList.removeAt(0)
        }catch (e :Exception){
            return null
        }

    }

    private fun createMoreViewHolders(){

        MyApp.getWorkerMainHandler().post {

            GlobalScope.launch{


                synchronized(oList){

                }
            }
        }
    }
}