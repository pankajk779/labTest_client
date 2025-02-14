package com.example.labtest_client.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.example.labtest_client.Util

class CustomLayoutOrgans :ViewGroup{

    constructor(context : Context) :super(context)
    constructor(context : Context, attrs : AttributeSet) :super(context, attrs)
    constructor(context : Context, attrs : AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width :Int = MeasureSpec.getSize(widthMeasureSpec)
        var height :Int = 0
        for (i :Int in 0 until childCount){
            val child :View = getChildAt(i)
            child.measure(widthMeasureSpec, heightMeasureSpec)
            height = child.measuredHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var lastX :Int = Util.getDp12()
        for(i :Int in 0 until childCount){
            val child : View = getChildAt(i)
            val r :Int = lastX + child.measuredWidth
            child.layout(lastX, 0, r, child.measuredHeight)
            lastX = r + Util.getDp12()
        }
    }
}