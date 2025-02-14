package com.example.labtest_client.views

import android.content.Context
import android.os.Trace
import android.util.AttributeSet
import android.widget.LinearLayout

class TestLinearLayout :LinearLayout{

    private val TAG :String = this.javaClass.name

    constructor(context : Context) :super(context)
    constructor(context : Context, attrs : AttributeSet) :super(context, attrs)
    constructor(context : Context, attrs : AttributeSet, defUtils :Int)
            :super(context, attrs, defUtils)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Trace.beginSection(TAG + "_onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Trace.endSection()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Trace.beginSection(TAG + "_onLayout")
        super.onLayout(changed, l, t, r, b)
        Trace.endSection()
    }
}