package com.example.labtest_client.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.example.labtest_client.Util

class CustomLastView : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        prepareView(
            MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun prepareView(proposedWidth :Int, proposedHeight :Int){
        mWidth = proposedWidth
        mHeight = Util.getPixels(200, resources.displayMetrics.density).toInt()
    }
}