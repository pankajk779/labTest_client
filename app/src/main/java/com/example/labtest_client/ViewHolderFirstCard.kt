package com.example.labtest_client

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ViewHolderFirstCard : Vh{

    private val TAG :String = this.javaClass.name

    constructor(view : View, bitmap1 :Bitmap, bitmap2 :Bitmap, bitmap3 :Bitmap) :super(view){

        val customView1 :CustomView = view.findViewById(R.id.cv_1)
        val customView2 :CustomView = view.findViewById(R.id.cv_2)
        val customView3 :CustomView = view.findViewById(R.id.cv_3)

        val bitmap1 : Bitmap = bitmap1
        val dark1 :String = "लैब टेस्ट"
        val normal1 :String = "Lab Tests"

        val bitmap2 : Bitmap = bitmap2
        val dark2 :String = "सैंपल"
        val normal2 :String = "Home Collect"

        val bitmap3 : Bitmap = bitmap3
        val dark3 :String = "मेरा खाता"
        val normal3 :String = "Login"

        customView1.setData(bitmap1, dark1, normal1)
        customView2.setData(bitmap2, dark2, normal2)
        customView3.setData(bitmap3, dark3, normal3)
    }
}