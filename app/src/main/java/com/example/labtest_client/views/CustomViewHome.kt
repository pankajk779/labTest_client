package com.example.labtest_client.views

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.labtest_client.R
import com.example.labtest_client.Util

class CustomViewHome : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT
    private lateinit var clipPath :Path
    private lateinit var bitmap : Bitmap
    private lateinit var srcRect :Rect
    private lateinit var dstRect :Rect
    private lateinit var bitmapPaint : Paint
    private lateinit var boldText :String
    private lateinit var boldTextPaint :TextPaint
    private lateinit var gradientRect :Rect
    private lateinit var gradientPaint :Paint
    private lateinit var boldTextPoint :Point
    private lateinit var boldTextPoint2 :Point
    private lateinit var boldTextPoint3 :Point
    private lateinit var boldTextPoint4 :Point
    private lateinit var strokeTextPaint :TextPaint

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_meditation_1_1)
        bitmapPaint = Paint().apply {
            style = Paint.Style.FILL
        }

        val hindiTypeface :Typeface = Typeface.createFromAsset(context.assets, "fonts/baloo2_bold.ttf")

        boldTextPaint = TextPaint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.white)
            textSize = Util.getPixels(42, resources.displayMetrics.density)
            typeface = hindiTypeface
            isAntiAlias = true
        }

        gradientPaint = Paint().apply {
            style = Paint.Style.FILL
        }

        gradientRect = Rect()
        srcRect = Rect()
        dstRect = Rect()
        boldText = "ध्यान का महत्व"

        boldTextPoint = Point()
        strokeTextPaint = TextPaint().apply {
            style = Paint.Style.FILL
            strokeWidth = Util.getPixels(1, resources.displayMetrics.density)
            color = resources.getColor(R.color.white)
            isAntiAlias = true
            typeface = hindiTypeface
            textSize = Util.getPixels(38, resources.displayMetrics.density)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val proposedWidth :Int = MeasureSpec.getSize(widthMeasureSpec)
        val proposedHeight :Int = MeasureSpec.getSize(heightMeasureSpec)

        prepareView(proposedWidth, proposedHeight)
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        canvas?.clipPath(clipPath)

        canvas?.drawBitmap(bitmap, srcRect, dstRect, bitmapPaint)
        canvas?.drawRect(gradientRect, gradientPaint)
        canvas?.drawText(boldText, boldTextPoint.x.toFloat(), boldTextPoint.y.toFloat(), boldTextPaint)

        strokeTextPaint.alpha = 70
        canvas?.drawText(boldText, boldTextPoint2.x.toFloat(), boldTextPoint2.y.toFloat(), strokeTextPaint)
        canvas?.drawText(boldText, boldTextPoint3.x.toFloat(), boldTextPoint3.y.toFloat(), strokeTextPaint)
        strokeTextPaint.alpha = 30
        canvas?.drawText(boldText, boldTextPoint4.x.toFloat(), boldTextPoint4.y.toFloat(), strokeTextPaint)


        canvas?.restore()
    }

    private fun prepareView(proposedWidth :Int, proposedHeight :Int){
        mWidth = proposedWidth
        mHeight = mWidth - Util.getDp12()*2

        clipPath = getClipPath(mWidth.toFloat(), mHeight.toFloat(), Util.getDp12())

        srcRect.left = 0; srcRect.top = 0
        srcRect.right = bitmap.width; srcRect.bottom = bitmap.height

        dstRect.left = Util.getDp12(); dstRect.top = 0
        dstRect.right = mWidth - Util.getDp12(); dstRect.bottom = mHeight

        gradientRect = Rect()
        gradientRect.left = Util.getDp12(); gradientRect.top = 0
        gradientRect.right = mWidth - Util.getDp12(); gradientRect.bottom = mHeight

        val gradient :LinearGradient = LinearGradient(0f, gradientRect.bottom.toFloat(), 0f, 0f,
            resources.getColor(R.color.black_60),
            resources.getColor(R.color.transparent),
            Shader.TileMode.CLAMP
        )
        gradientPaint.shader = gradient

        val boldTextWidth :Float = boldTextPaint.measureText(boldText)
        boldTextPoint.x = (mWidth /2 - boldTextWidth/2).toInt()
        boldTextPoint.y = mHeight - Util.getDp12()*4

        val strokeTextWidth :Float = strokeTextPaint.measureText(boldText)
        boldTextPoint2 = Point()
        boldTextPoint2.x = (mWidth/2 - strokeTextWidth/2).toInt()
        boldTextPoint2.y = (boldTextPoint.y - boldTextPaint.fontMetrics.ascent).toInt()

        boldTextPoint3 = Point()
        boldTextPoint3.x = boldTextPoint2.x
        boldTextPoint3.y = (boldTextPoint.y + boldTextPaint.fontMetrics.ascent).toInt()

        boldTextPoint4 = Point()
        boldTextPoint4.x = boldTextPoint2.x
        boldTextPoint4.y = (boldTextPoint3.y + boldTextPaint.fontMetrics.ascent).toInt()
    }

    private fun getClipPath(mWidth :Float, mHeight :Float, padding :Int) : Path {

        val path : Path = Path()
        val radius :Float = Util.getDp12().toFloat()
        var oval : RectF = RectF()

        path.moveTo(padding + radius, 0f)
        path.lineTo(mWidth- padding - (radius*2), 0f)

        oval.set(mWidth - padding - radius*2, 0f, mWidth - padding, radius*2)
        path.arcTo(oval, 270f, 90f)

        path.lineTo(mWidth - padding, mHeight)

        oval.set(mWidth - padding - radius*2, mHeight - radius*2, mWidth - padding, mHeight)
        path.arcTo(oval, 0f, 90f)

        path.lineTo(padding + radius*2, mHeight)

        oval.set(padding.toFloat(), mHeight - radius*2, padding + radius*2, mHeight)
        path.arcTo(oval, 90f, 90f)

        path.lineTo(padding.toFloat(), radius*2)

        oval.set(padding.toFloat(), 0f, padding + radius*2, radius*2)
        path.arcTo(oval, 180f, 90f)

        return path
    }
}