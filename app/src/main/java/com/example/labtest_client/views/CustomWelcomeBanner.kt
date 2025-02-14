package com.example.labtest_client.views

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.labtest_client.R
import com.example.labtest_client.Util

class CustomWelcomeBanner : View{

    private val TAG :String = this.javaClass.name
    private var mPreviousWidth :Int = 0
    private var mPreviousHeight :Int = 0
    private var bitmap :Bitmap? =null
    private var bitmap2 :Bitmap? =null
    private var logoBitmap :Bitmap? = null
    private lateinit var srcRect :Rect
    private lateinit var srcRect2 :Rect
    private lateinit var dstRect :Rect
    private lateinit var logoBitmapDstRect :Rect
    private lateinit var logoBitmapSrcRect :Rect
    private lateinit var bitmapPaint :Paint
    private lateinit var bitmapPaint2 :Paint
    private lateinit var logoPaint :Paint
    private lateinit var textPaint :TextPaint
    private lateinit var head2Paint :TextPaint
    private lateinit var staticLayout :StaticLayout
    private lateinit var gradientPaint :Paint
    private lateinit var staticLayoutPoint :Point


    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    init {
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width :Int = MeasureSpec.getSize(widthMeasureSpec)
        val height :Int = MeasureSpec.getSize(heightMeasureSpec)
        Log.d(TAG, "onMeasure: ")
        measureView(mPreviousWidth != width || mPreviousHeight != height
            ,width, height)

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec)
            ,MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas?) {

        bitmap?.let {
            canvas?.drawBitmap(it, srcRect, dstRect, bitmapPaint)
        }

        canvas?.drawRect(
            0f,
            logoBitmapDstRect.top - Util.getPixels(50, resources.displayMetrics.density),
            width.toFloat(),
            height.toFloat(), gradientPaint
        )

        logoBitmap?.let {
            canvas?.drawBitmap(it, logoBitmapSrcRect, logoBitmapDstRect, logoPaint)
        }

        if(::staticLayout.isInitialized){
            canvas?.save()
            canvas?.translate(staticLayoutPoint.x.toFloat(), staticLayoutPoint.y.toFloat())
            staticLayout.draw(canvas!!)

            canvas?.restore()
        }
    }

    private fun initView(){

        bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_test_4_5)
        bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.image_test_1_1)
        logoBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_labtest_white)

        bitmapPaint = Paint().apply {
            style = Paint.Style.FILL
        }
        bitmapPaint2 = Paint().apply {
            style = Paint.Style.FILL
            alpha = 0
        }

        logoPaint = Paint().apply {
            style = Paint.Style.FILL
//            alpha = 0
        }

        val hindiFont :Typeface = Typeface.createFromAsset(context.assets, "fonts/baloo2_medium.ttf")

        textPaint = TextPaint().apply {
            isAntiAlias = true
            typeface = hindiFont
            textSize = 58f
            color = resources.getColor(R.color.white)
//            alpha = 0
        }

        head2Paint = TextPaint().apply {
            isAntiAlias = true
            typeface = hindiFont
            textSize = 60f
            color = resources.getColor(R.color.white)
//            alpha = 0
        }

        gradientPaint = Paint().apply {
            style = Paint.Style.FILL
            isDither = true
        }
    }

    private fun measureView(changed :Boolean, widthPixels :Int, heightPixels :Int){
        measureBitmaps(widthPixels, heightPixels)
        setLayout(widthPixels, heightPixels)
        mPreviousWidth = widthPixels
        mPreviousHeight = heightPixels
    }

    private fun measureBitmaps(widthPixels :Int, heightPixels :Int){

        bitmap?.let {
            dstRect = Rect(0, 0, widthPixels, heightPixels)

            val dstRatio :Float = widthPixels.toFloat() / heightPixels.toFloat()
            val srcRatio :Float = it.width.toFloat() / it.height.toFloat()
            if(dstRatio == srcRatio){
                srcRect = Rect(0, 0, it.width, it.height)
            }else{
                //cropping the width.
                val newWidth :Float = (it.height.toFloat() / heightPixels.toFloat()) * widthPixels.toFloat()
                srcRect = Rect(0, 0, newWidth.toInt(), it.height)
            }
        }

        bitmap2?.let {
            val dstRatio :Float = widthPixels.toFloat() / heightPixels.toFloat()
            val srcRatio :Float = it.width.toFloat() / it.height.toFloat()
            if(dstRatio == srcRatio){
                srcRect2 = Rect(0, 0, it.width, it.height)
            }else{
                //cropping the width.
                val newWidth :Float = (it.height.toFloat() / 16) * 9
                srcRect2 = Rect(0, 0, newWidth.toInt(), it.height)
            }
        }

        logoBitmap?.let {
            logoBitmapSrcRect = Rect(0, 0, it.width, it.height)
//            logoBitmapDstRect = Rect(60, 150, it.width/3 + 60, it.height/3 + 150)
        }

        staticLayout = StaticLayout("हमारा उद्देश्य सभी नागरिको तक किफायती तथा गुणवत्तापूर्ण चिकित्सा सुविधाएँ पहुँचाना ।।"
            , head2Paint
            , 900
            , Layout.Alignment.ALIGN_NORMAL
            , 0.8f, 0f, false
        )
    }

    private fun setLayout(width :Int, height :Int){

        staticLayoutPoint = Point()
        staticLayoutPoint.x = Util.getPixels(50, resources.displayMetrics.density).toInt()
        staticLayoutPoint.y = height - Util.getNavBarHeight()!! -
                Util.getPixels(12, resources.displayMetrics.density).toInt() -
                Util.getPixels(55, resources.displayMetrics.density).toInt() -
                Util.getPixels(12, resources.displayMetrics.density).toInt() -
                staticLayout.height

        val logoBitmapL :Int = Util.getPixels(50, resources.displayMetrics.density).toInt()
        val logoBitmapT :Int = staticLayoutPoint.y - Util.getPixels(8, resources.displayMetrics.density).toInt() - logoBitmap!!.height
        val logoBitmapR :Int = logoBitmapL + logoBitmap!!.width * 4/5
        val logoBitmapB :Int = logoBitmapT + logoBitmap!!.height * 4/5

        logoBitmapDstRect = Rect( logoBitmapL, logoBitmapT, logoBitmapR, logoBitmapB)

        val gradient :LinearGradient = LinearGradient(0f, height.toFloat(), 0f, logoBitmapDstRect.top - Util.getPixels(50, resources.displayMetrics.density),
            resources.getColor(R.color.black),
            resources.getColor(R.color.transparent),
            Shader.TileMode.CLAMP
        )
        gradientPaint.shader = gradient

        bitmapPaint.alpha = 0
        logoPaint.alpha = 0
        head2Paint.alpha = 0

    }

    fun positionDstRect(y :Int){
        if(::dstRect.isInitialized){
            dstRect.left = y
            dstRect.right = y + width
        }
        bitmapPaint.alpha = 255 - y*3
    }

    fun positionLogoDst(y :Int){
        if(::logoBitmapDstRect.isInitialized){
            logoBitmapDstRect.left = 36 + y
            logoBitmapDstRect.right = 36 + y + logoBitmap!!.width *4/5
        }
        logoPaint.alpha = 255 - y*3
    }

    fun positionStaticX(y :Int){
        if(::staticLayoutPoint.isInitialized){
            staticLayoutPoint.x = 36 + y
        }
        head2Paint.alpha = 255 - y*3
    }
}