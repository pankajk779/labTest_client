package com.example.labtest_client

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RvAdapterBookTest : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val TAG :String = this.javaClass.name
    private val context :Context

    constructor(context : Context){
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) return 1
        if(position == 1) return 2
        if(position == 2) return 3
        if(position == 3) return 4
        if(position == 4) return 5
        else return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_book_test_card_1, parent, false)
            return Vh(view)
        }else if(viewType == 2){
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_book_test_card_2, parent, false)
            return Vh(view)
        }else if(viewType == 3){
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_book_test_card_3, parent, false)
            return Vh(view)
        }else if(viewType == 4){
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_book_test_card_4, parent, false)
            return Vh(view)
        }else if(viewType == 5){
            val view :View = LayoutInflater.from(parent.context).inflate(R.layout.layout_horizontal_recycler_view, parent, false)
            return Vh(view)
        }else{
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_test_category, parent, false)
            return Vh(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0 || position == 1 || position == 2){

        }else if(position == 3){

            val recycler :RecyclerView = holder.itemView.findViewById(R.id.rv_horizontal_card_4)
            val adapter : RvAdapterHorizontalTest = RvAdapterHorizontalTest()
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        }else if(position == 4){

            val recycler :RecyclerView = holder.itemView.findViewById(R.id.rv_horizontal)
            val adapter :RvAdapterHorizontal = RvAdapterHorizontal(context)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        }else{
//            val vh : Vh = holder as Vh
//            val imageView : ImageView = vh.itemView.findViewById(R.id.iv_home)
//            imageView.setImageDrawable(context.resources.getDrawable(R.drawable.image_test_1_1))
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    inner class Vh : RecyclerView.ViewHolder {
        constructor(view : View):super(view)
    }
}