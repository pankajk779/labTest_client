package com.example.labtest_client

import android.graphics.Bitmap
import android.os.Trace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest_client.views.CustomViewBanner

class RvWelcome2Adapter : RecyclerView.Adapter<RvWelcome2Adapter.MyViewHolderWelcome2> {

    private val TAG :String = this.javaClass.name
    val context :FragmentActivity
    var holderList :ArrayList<MyViewHolderWelcome2>? = null
    var bitmapList :ArrayList<Bitmap>? = null
    var holderCounter :Int = Util.INVALID_INT

    constructor(context : FragmentActivity) : super(){
        this.context = context
        this.holderList = ArrayList()
        this.bitmapList = ArrayList()
    }

    fun updateList(list :ArrayList<MyViewHolderWelcome2>, bitmapList :ArrayList<Bitmap>){
        holderList!!.clear()
        this.holderList = list
        this.bitmapList!!.clear()
        this.bitmapList = bitmapList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {

        if(holderList == null) return 10
        else{
            if(position == 0 || position == 7) return position
            else return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderWelcome2 {

        if (viewType == 0) {
            holderCounter++
            val holder :FirstViewHolder = holderList?.let { it.get(holderCounter) } as FirstViewHolder
            return holder
        } else if (viewType == 7) {
            holderCounter++
            val holder :EndViewHolder = holderList?.let { it.get(holderCounter) } as EndViewHolder
            return holder
        } else {
            holderCounter++
            val holder :MyViewHolder = holderList?.let { it.get(holderCounter) } as MyViewHolder
            return holder
        }
    }

    override fun onBindViewHolder(holder: MyViewHolderWelcome2, position: Int) {

        if(holder is MyViewHolder){
            val oHolder :MyViewHolder = holder as MyViewHolder

            when(position){
                1 ->{
                    val id :Int = R.drawable.image_india_gate_9_16
                    bitmapList!![0].prepareToDraw()
                    oHolder.custBanner.setImage(bitmapList!![0])
                    oHolder.custBanner.setText("दिल्ली एवं राजधानी छेत्र(NCR)", "60 मिनट मैं अपने घर से सैंपल जमा कीजिये")
                    oHolder.custBanner.setTextColor(R.color.white)
                }
                2 ->{
                    val id :Int = R.drawable.image_women_9_16
                    bitmapList!!.get(1).prepareToDraw()
                    oHolder.custBanner.setImage(bitmapList!!.get(1))
                    oHolder.custBanner.setText("आसान", "सभी के लिए आसान प्रक्रिया")
                    oHolder.custBanner.setTextColor(R.color.white)
                }
                3->{
                    val id :Int = R.drawable.image_report_9_16
                    bitmapList!!.get(2).prepareToDraw()
                    oHolder.custBanner.setImage(bitmapList!!.get(2))
                    oHolder.custBanner.setText("रिपोर्ट्स", "10 घंटो में अपनी रिपोर्ट्स डाउनलोड करे")
                    oHolder.custBanner.setTextColor(R.color.white)
                }
                4 ->{
                    val id :Int = R.drawable.image_discount_9_16
                    bitmapList!!.get(3).prepareToDraw()
                    oHolder.custBanner.setImage(bitmapList!!.get(3))
                    oHolder.custBanner.setText("दरों में छूट", "पहले भुगतान पर 20% दर की छूट")
                    oHolder.custBanner.setTextColor(R.color.black)
                }
                5 ->{
                    val id :Int = R.drawable.image_payment_9_16
                    bitmapList!!.get(4).prepareToDraw()
                    oHolder.custBanner.setImage(bitmapList!!.get(4))
                    oHolder.custBanner.setText("सुरक्षित भुगतान", "अपनी सुविधा अनुसार नगद एवं ऑनलाइन भुगतान करे")
                    oHolder.custBanner.setTextColor(R.color.black)
                }
                6 ->{
                    val id :Int = R.drawable.image_contact_9_16
                    bitmapList!!.get(5).prepareToDraw()
                    oHolder.custBanner.setImage(bitmapList!!.get(5))
                    oHolder.custBanner.setText("संपर्क", "ऐप से चैट करे या हमें कॉल करे 1800-XX-XXXX")
                    oHolder.custBanner.setTextColor(R.color.white)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if(holderList == null) return 0
        else return holderList!!.size
    }

    open class MyViewHolderWelcome2 : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    class FirstViewHolder : MyViewHolderWelcome2 {
        constructor(itemView: View) : super(itemView){

        }
    }

    class MyViewHolder : MyViewHolderWelcome2{

        val custBanner :CustomViewBanner

        constructor(itemView: View) : super(itemView) {
            custBanner = itemView.findViewById(R.id.cust_view_blank)
        }
    }

    class EndViewHolder : MyViewHolderWelcome2 {

        constructor(itemView: View) : super(itemView) {

        }
    }
}