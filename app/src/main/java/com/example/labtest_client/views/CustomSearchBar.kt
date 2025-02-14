package com.example.labtest_client.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.icu.util.Measure
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.labtest_client.R
import com.example.labtest_client.Util

class CustomSearchBar : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT
    private lateinit var clipRect : Rect
    private val textPaint :TextPaint
    private val text :String
    private lateinit var textPoint : Point

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    init{
        text = "Search bar"

        textPaint = TextPaint().apply {
            color = resources.getColor(R.color.black)
            textSize = Util.getPixels(24, resources.displayMetrics.density)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        prepareView(
            MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.clipRect(clipRect)
        canvas?.drawColor(resources.getColor(R.color.purple_200))
        canvas?.drawText(text, textPoint.x.toFloat(), textPoint.y.toFloat(), textPaint)
    }

    private fun prepareView(proposedWidth :Int, proposedHeight :Int){
        mWidth = proposedWidth
        mHeight = Util.getPixels(125, resources.displayMetrics.density).toInt()

        textPoint = Point()
        val textWidth :Float = textPaint.measureText(text)
        textPoint.x = (mWidth/2 - textWidth/2).toInt()
        textPoint.y = mHeight/2
        clipRect = Rect( 0, Util.getDp12(), mWidth, mHeight - Util.getDp12())
    }
}