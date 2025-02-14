package com.example.labtest_client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RvAdapterHorizontalTest : RecyclerView.Adapter<RvAdapterHorizontalTest.TestViewHolder>{

    private val TAG :String = this.javaClass.name

    constructor()

    override fun getItemViewType(position: Int): Int {
        if(position == 0 || position == 7){
            return 0
        }else return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        if(viewType == 0){

            val view :View = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_space, parent, false)
            return TestViewHolder(view)
        }else{
            val view :View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_test_2, parent, false)
            return TestViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 8
    }

    inner class TestViewHolder :ViewHolder{

        constructor(view : View) :super(view)
    }
}