package com.example.labtest_client

abstract class HolderList {

    constructor(viewType :Int, oList :ArrayList<Vh>){
    }

    abstract fun getViewHolder() :Vh?
}