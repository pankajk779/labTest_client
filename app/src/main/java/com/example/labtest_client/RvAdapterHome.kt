package com.example.labtest_client

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class RvAdapterHome :RecyclerView.Adapter<ViewHolder>{

    private val TAG :String = this.javaClass.name
    private var context :Context
    private var holderList :ArrayList<Vh>


    constructor(context : Context, holderList :ArrayList<Vh>){

        this.context = context
        this.holderList = holderList
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) return Util.HOLDER_LOGIN_BUTTONS
        else if(position == 1) return Util.HOLDER_SEARCH_BAR
        else if(position == 2) return Util.HOLDER_SALE_BANNER
        else if(position == 3) return 4
        else if(position == 4) return 5
        else if(position == 11) return 6
        else return Util.HOLDER_DEFAULT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if(viewType == 1){

            return holderList.get(0)
        }else if(viewType == 2){

            return holderList.get(1)
        }else if(viewType == 3){

            return holderList.get(2)
        }else{

            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_home, parent, false)
            return Vh(view)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 12
    }

    fun updateData(data :Data){
        notifyDataSetChanged()
    }
}