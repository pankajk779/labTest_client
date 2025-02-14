package com.example.labtest_client

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RvAdapterHorizontal : RecyclerView.Adapter<RvAdapterHorizontal.HorizontalViewHolder> {

    private val TAG :String = this.javaClass.name
    private val context :Context

    constructor(context : Context){
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val view :View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_test_2, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 8
    }

    inner class HorizontalViewHolder :ViewHolder{

        constructor(view : View) :super(view){

        }
    }
}