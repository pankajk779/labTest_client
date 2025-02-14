package com.example.labtest_client

class Data {

    private val TAG :String = this.javaClass.name

    private val viewType :Int

    constructor(viewType :Int){
        this.viewType = viewType
    }

    fun getViewType() :Int{
        return viewType
    }
}