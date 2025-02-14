package com.example.labtest_client

import android.content.Context
import android.graphics.*
import android.graphics.Paint.FontMetrics
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class CustomView : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT
    private var imposedHeight :Int = Util.INVALID_INT
    private var imposedWidth :Int = Util.INVALID_INT
    private lateinit var clipPath : Path
    private lateinit var imageBitmap : Bitmap
    private lateinit var srcRect :Rect
    private lateinit var dstRect :Rect
    private lateinit var imagePaint : Paint
    private var darkText :String = ""
    private var normalText :String = ""
    private lateinit var darkTextPaint :TextPaint
    private lateinit var normalTextPaint :TextPaint
    private lateinit var darkTextPoint :Point
    private lateinit var normalTextPoint :Point
    private lateinit var testRectPaint :Paint

    constructor(context : Context) :super(context){
        initView()
    }
    constructor(context : Context, attrs : AttributeSet) :super(context, attrs){
        initView()
    }
    constructor(context : Context, attrs : AttributeSet, deffUtils :Int) :super(context, attrs, deffUtils){
        initView()
    }

    private fun initView(){

        imagePaint = Paint().apply {
            style = Paint.Style.FILL
        }

        val hindiTypeface :Typeface = Typeface.createFromAsset(context.assets, "fonts/baloo2_medium.ttf")

        darkTextPaint = TextPaint().apply {
            isAntiAlias = true
            color = resources.getColor(R.color.black)
            textSize = Util.getPixels(14, resources.displayMetrics.density)
            typeface = hindiTypeface
        }

        normalTextPaint = TextPaint().apply {
            isAntiAlias = true
            color = resources.getColor(R.color.grey)
            textSize = Util.getPixels(14, resources.displayMetrics.density)
            typeface = hindiTypeface
        }

        darkTextPoint = Point()
        normalTextPoint = Point()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        imposedHeight = MeasureSpec.getSize(heightMeasureSpec)
        imposedWidth = MeasureSpec.getSize(widthMeasureSpec)

        mWidth = (imposedWidth - Util.getDp12()*4)/3
        prepareView(imposedWidth, imposedHeight)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()

        canvas?.clipPath(clipPath)
        canvas?.drawColor(resources.getColor(R.color.skyBlue))

        if(::imageBitmap.isInitialized){
            canvas?.drawBitmap(imageBitmap, srcRect, dstRect, imagePaint)
        }

        canvas?.drawText(darkText, darkTextPoint.x.toFloat(), darkTextPoint.y.toFloat(), darkTextPaint)

        canvas?.drawText(normalText, normalTextPoint.x.toFloat(), normalTextPoint.y.toFloat(), normalTextPaint)

        canvas?.restore()
    }

    private fun prepareView(imposedWidth :Int, imposedHeight :Int){

        val darkTextWidth :Float = darkTextPaint.measureText(darkText)
        val normalTextWidth :Float = normalTextPaint.measureText(normalText)

        if(::imageBitmap.isInitialized){
            srcRect = Rect(0, 0, imageBitmap.width, imageBitmap.height)
        }

        val dstL :Int = mWidth/2 - Util.getPixels(20, resources.displayMetrics.density).toInt()
        val dstT :Int = Util.getDp12()
        val dstR :Int = mWidth/2 + Util.getPixels(20, resources.displayMetrics.density).toInt()
        val dstB :Int = dstT + Util.getPixels(40, resources.displayMetrics.density).toInt()
        dstRect = Rect(dstL, dstT, dstR, dstB)

        val darkMetrics : FontMetrics = darkTextPaint.fontMetrics

        darkTextPoint.x = (mWidth/2 - darkTextWidth/2).toInt()
        darkTextPoint.y = (dstB + Util.getDp8() - darkMetrics.ascent).toInt()

        normalTextPoint.x = (mWidth/2 - normalTextWidth/2).toInt()
        normalTextPoint.y = (darkTextPoint.y +  - normalTextPaint.fontMetrics.top).toInt()

        testRectPaint = Paint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.black)
        }

        val bitmapHeight :Int = dstB - dstT
        val darkTextHeight :Int = (-darkTextPaint.fontMetrics.ascent + darkTextPaint.fontMetrics.descent).toInt()
        val normalTextHeight :Int = (-normalTextPaint.fontMetrics.ascent + normalTextPaint.fontMetrics.descent).toInt()

        mHeight = Util.getPixels(5, resources.displayMetrics.density).toInt() + bitmapHeight + Util.getDp8() + darkTextHeight + normalTextHeight + Util.getDp8()

        clipPath = getClipPath(mWidth.toFloat(), mHeight.toFloat())
    }

    private fun getClipPath(mWidth :Float, mHeight :Float) :Path{

        val path :Path = Path()
        val radius :Float = Util.getDp12().toFloat()
        var oval : RectF = RectF()

        path.moveTo(radius, 0f)
        path.lineTo(mWidth-(radius*2), 0f)

        oval.set(mWidth - radius*2, 0f, mWidth, radius*2)
        path.arcTo(oval, 270f, 90f)

        path.lineTo(mWidth, mHeight)

        oval.set(mWidth - radius*2, mHeight - radius*2, mWidth, mHeight)
        path.arcTo(oval, 0f, 90f)

        path.lineTo(radius*2, mHeight)

        oval.set(0f, mHeight - radius*2, radius*2, mHeight)
        path.arcTo(oval, 90f, 90f)

        path.lineTo(0f, radius*2)

        oval.set(0f, 0f, radius*2, radius*2)
        path.arcTo(oval, 180f, 90f)

        return path
    }

    fun setData(imageBitmap :Bitmap, darkText :String, normalText :String){
        if(!::imageBitmap.isInitialized)  this.imageBitmap = imageBitmap
        this.darkText = darkText
        this.normalText = normalText
        requestLayout()
    }
}