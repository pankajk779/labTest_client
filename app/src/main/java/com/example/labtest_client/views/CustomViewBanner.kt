package com.example.labtest_client.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Trace
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.labtest_client.R
import com.example.labtest_client.Util

class CustomViewBanner : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Float = 0f
    private var mHeight :Float = 0f
    private lateinit var bitmapPaint : Paint
    private var bitmap : Bitmap? = null
    private lateinit var srcRect :Rect
    private lateinit var dstRect :RectF
    private lateinit var clipPath :Path
    private lateinit var heading_1Paint :TextPaint
    private lateinit var heading_2Paint :TextPaint
    private var mHeading1 :String = "heading_1"
    private lateinit var circlePaint :Paint
    private lateinit var forwardArrow :Drawable
    private lateinit var staticLayout :StaticLayout
    private var staticLayoutHeight :Int = 0
    private lateinit var gradientPaint :Paint
    private lateinit var gradientPaint2 :Paint
    private lateinit var gradientPaintRect :RectF
    private lateinit var gradientPaint2Rect :RectF

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int)
            :super(context, attrs, defUtils)

    init{
        initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Trace.beginSection(TAG+"_onMeasure")

        mWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        mHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        prepareView(mWidth, mHeight)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))

        Trace.endSection()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(clipPath)

        if(bitmap != null){
            canvas?.drawBitmap(bitmap!!, srcRect, dstRect, bitmapPaint)
        }

        canvas?.drawRect(gradientPaintRect, gradientPaint)
        canvas?.drawRect(gradientPaint2Rect, gradientPaint2)

        canvas?.drawText(mHeading1, Util.getPixels(12, resources.displayMetrics.density), Util.getPixels(12, resources.displayMetrics.density) - heading_1Paint.fontMetrics.top, heading_1Paint)

        canvas?.drawCircle(mWidth - Util.getDp12()- Util.getPixels(20, resources.displayMetrics.density), mHeight - Util.getDp12() - Util.getPixels(20, resources.displayMetrics.density), Util.getPixels(20, resources.displayMetrics.density), circlePaint)
        forwardArrow.draw(canvas!!)

        if(::staticLayout.isInitialized){
            canvas?.save()
            canvas?.translate(Util.getDp12().toFloat(), mHeight -Util.getDp12().toFloat() -Util.getDp12().toFloat() -staticLayoutHeight)
            staticLayout.draw(canvas!!)

            canvas?.restore()
        }
    }

    private fun initView(){

        bitmapPaint = Paint().apply {
            style = Paint.Style.FILL
        }

        val hindiFont :Typeface = Typeface.createFromAsset(context.assets, "fonts/baloo2_bold.ttf")

        heading_1Paint = TextPaint().apply {
            typeface = hindiFont
            textSize = 48f
            color = resources.getColor(R.color.black)
        }

        heading_2Paint = TextPaint().apply {
            typeface = hindiFont
            textSize = 78f
            color = resources.getColor(R.color.black)
            isAntiAlias = true
        }

        circlePaint = Paint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.black_60)
            isAntiAlias = true
        }

        forwardArrow = ContextCompat.getDrawable(context, R.drawable.baseline_info_24
        )!!
    }

    private fun prepareView(mWidth :Float, mHeight :Float){
        clipPath = getClipPath()

        if(bitmap != null){

            dstRect = RectF(0f, 0f, mWidth, mHeight)

            val dstRatio :Float = mWidth / mHeight
            val srcRatio :Float = bitmap!!.width.toFloat() / bitmap!!.height.toFloat()

            if(dstRatio == srcRatio){
                srcRect = Rect(0, 0, bitmap!!.width, bitmap!!.height)
            }else{
                //cropping the width.
                val newWidth :Float = (bitmap!!.height.toFloat() / mHeight) * mWidth
                srcRect = Rect(0, 0, newWidth.toInt(), bitmap!!.height)
            }
        }

        forwardArrow.setBounds(mWidth.toInt() - Util.getPixels(12 + 30 + 5, resources.displayMetrics.density).toInt(), mHeight.toInt() - Util.getPixels(12 + 30 + 5, resources.displayMetrics.density).toInt(), mWidth.toInt() - 36 -15, mHeight.toInt() - 36 -15)

        gradientPaintRect = RectF(0f, 0f, mWidth, Util.getPixels(100, resources.displayMetrics.density))
        gradientPaint2Rect = RectF(0f, (mHeight/2), mWidth, mHeight)

        val gradient :LinearGradient = LinearGradient(0f, 0f, 0f, gradientPaintRect.bottom,
            resources.getColor(R.color.black_20),
            resources.getColor(R.color.transparent),
            Shader.TileMode.CLAMP
        )

        gradientPaint = Paint().apply {
            style = Paint.Style.FILL
            isDither = true
            shader = gradient
        }

        val gradient2 :LinearGradient = LinearGradient(0f, mHeight, 0f, gradientPaint2Rect.top,
            resources.getColor(R.color.black_20),
            resources.getColor(R.color.transparent),
            Shader.TileMode.CLAMP
        )

        gradientPaint2 = Paint().apply {
            style = Paint.Style.FILL
            isDither = true
            shader = gradient2
        }
    }

    fun setTextColor(color :Int){

        heading_2Paint.color = resources.getColor(color)
        heading_1Paint.color = resources.getColor(color)
        invalidate()
    }

    fun setText(heading1 :String, heading2 :String){

        mHeading1 = heading1
        staticLayout = StaticLayout(heading2, heading_2Paint, 550, Layout.Alignment.ALIGN_NORMAL,
        0.8f, 0f, false)

        staticLayoutHeight = staticLayout.height
        invalidate()
    }

    fun setImage(bitmap :Bitmap){
        this.bitmap = bitmap
//        bitmap?.prepareToDraw()
        invalidate()
    }

    private fun getClipPath() :Path{
        val path :Path = Path()
        val radius :Float = Util.getDp12().toFloat()
        var oval :RectF = RectF()

        path.moveTo(radius, 0f)
        path.lineTo(mWidth-radius, 0f)

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
}