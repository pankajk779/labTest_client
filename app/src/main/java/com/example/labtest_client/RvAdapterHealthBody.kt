package com.example.labtest_client

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RvAdapterHealthBody : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val TAG :String = this.javaClass.name
    constructor()

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view :View = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_card_2, parent, false)
        return Vh(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class Vh : RecyclerView.ViewHolder {
        constructor(view : View):super(view)
    }
}