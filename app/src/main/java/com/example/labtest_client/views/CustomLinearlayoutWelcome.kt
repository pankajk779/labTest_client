package com.example.labtest_client.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.example.labtest_client.Util

/**
 * customized to work with CustomWelcomeBanner and two buttons
 */
class CustomLinearlayoutWelcome :LinearLayout{

    private val TAG :String = this.javaClass.name

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "onLayout: ")
        for(i :Int in 0 until childCount){
            val child : View = getChildAt(i)
            if(child is CustomWelcomeBanner){
                child.layout(l, t, r, b)
                
            }else if(child is Button){
                child.layout(
                    r - (Util.getPixels(12, resources.displayMetrics.density)).toInt() - child.measuredWidth,
                    b - Util.getNavBarHeight()!! - (Util.getPixels(12, resources.displayMetrics.density)).toInt() - Util.getPixels(48, resources.displayMetrics.density).toInt(),
                    r - (Util.getPixels(12, resources.displayMetrics.density)).toInt(),
                    b - Util.getNavBarHeight()!! - (Util.getPixels(12, resources.displayMetrics.density)).toInt()
                )
            }
        }
    }
}