package com.example.labtest_client.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest_client.Util
import com.google.android.material.button.MaterialButton

class CustomLayoutWelcome2 :ViewGroup{

    private val TAG :String = this.javaClass.name

    constructor(context : Context) :super(context)
    constructor(context : Context, attrs : AttributeSet) :super(context, attrs)
    constructor(context : Context, attrs : AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for(i :Int in 0 until childCount){
            val child : View = getChildAt(i)
            //make the height to 16:9
            if(child is RecyclerView){
                var left :Int = 0
                var top :Int = -1
                var right :Int = r
                var bottom :Int = -1

                //the middle card should be 9:164
                val totalHorizontalSpace :Int = r - l - child.paddingLeft - child.paddingRight - child.marginLeft - child.marginRight
                val proposedHeight :Int = (totalHorizontalSpace/9)*16
                val buttonTop :Int = b - Util.getPixels(12 + 48, resources.displayMetrics.density).toInt()
                val buttonBottom :Int = b - Util.getPixels(12, resources.displayMetrics.density).toInt()
                val btnAndNavSpace :Int = Util.getPixels(12, resources.displayMetrics.density).toInt()*2 + buttonBottom - buttonTop
                val availableVerticalSpace :Int = b - t - btnAndNavSpace
                top = availableVerticalSpace/2 - proposedHeight/2

                child.layout(
                    0, top, r, top + proposedHeight
                )
            }else if(child is MaterialButton){
                val child :View = getChildAt(i)
                child.layout(
                    Util.getPixels(12, resources.displayMetrics.density).toInt(),
                    b - Util.getPixels(12 + 48, resources.displayMetrics.density).toInt(),
                    r - Util.getPixels(12, resources.displayMetrics.density).toInt(),
                    b - Util.getPixels(12, resources.displayMetrics.density).toInt()
                )
            }
        }
    }
}