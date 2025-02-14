package com.example.labtest_client

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyHolder {

    private val TAG :String = this.javaClass.name

    private val viewType :Int
    private val viewHolderList :ArrayList<ViewHolder>

    constructor(viewType :Int, viewHolderList : ArrayList<ViewHolder>){
        this.viewType = viewType
        this.viewHolderList = viewHolderList
    }

    fun getViewType() :Int{
        return viewType
    }

    fun getViewHolderList() :ArrayList<ViewHolder>{
        return viewHolderList
    }
}