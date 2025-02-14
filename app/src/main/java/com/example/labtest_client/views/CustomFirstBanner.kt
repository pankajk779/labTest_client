package com.example.labtest_client.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.labtest_client.R

class CustomFirstBanner : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = 0
    private var mHeight :Int = 0
    private lateinit var heading :String
    private lateinit var btnText :String
    private lateinit var staticLayout : StaticLayout
    private lateinit var textPaint : TextPaint
    private var staticLayoutHeight :Int = 0

    constructor(context : Context) :super(context)
    constructor(context : Context, attrs : AttributeSet) :super(context, attrs)
    constructor(context : Context, attrs : AttributeSet, deffUtils :Int) :super(context, attrs, deffUtils)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        initView()
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(resources.getColor(R.color.white))

        canvas?.save()
        canvas?.translate(36f, (mHeight/2 - staticLayoutHeight/2).toFloat())
        staticLayout.draw(canvas!!)
        canvas?.restore()

    }

    private fun initView(){
        heading = "नमस्कार\n\nLabTest.com के एप्प में आपका स्वागत है। हमारी सुविधाएं और इस एप्प की कुछ विशेषताओं के बारे में जानने के लिए कृपया दाएं स्क्रॉल करे।"
        btnText = "ऐप पर जाये"

        val hindiFont : Typeface = Typeface.createFromAsset(context.assets, "fonts/baloo2_semi_bold.ttf")

        textPaint = TextPaint().apply {
            color = resources.getColor(R.color.grey)
            isAntiAlias = true
            textSize = 60f
            typeface = hindiFont
        }

        staticLayout = StaticLayout(heading, textPaint, mWidth - 120, Layout.Alignment.ALIGN_NORMAL,
            0.8f, 0f, false)
        staticLayoutHeight = staticLayout.height
    }
}