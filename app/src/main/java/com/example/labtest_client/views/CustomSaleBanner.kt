package com.example.labtest_client.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.labtest_client.R
import com.example.labtest_client.Util

class CustomSaleBanner : View {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT
    private lateinit  var normalTextPaint : TextPaint
    private lateinit  var boldTextPaint : TextPaint
    private lateinit var bitmapPaint :Paint
    private lateinit var nextBitmap : Bitmap
    private lateinit var giftboxBitmap : Bitmap
    private lateinit var srcNextRect :Rect
    private lateinit var dstNextRect :Rect
    private lateinit var srcGiftRect : Rect
    private lateinit var dstGiftRect :Rect
    private var circleRadius :Float = Util.INVALID_FLOAT
    private var circleX :Float = Util.INVALID_FLOAT
    private var circleY :Float = Util.INVALID_FLOAT
    private lateinit var circlePaint :Paint
    private lateinit var discountText :String
    private lateinit var discountSubText :String
    private lateinit var discountSubText2 :String
    private var discountTextX :Float = Util.INVALID_FLOAT
    private var discountTextY :Float = Util.INVALID_FLOAT
    private var discountSubTextX :Float = Util.INVALID_FLOAT
    private var discountSubTextY :Float = Util.INVALID_FLOAT
    private var discountSubText2X :Float = Util.INVALID_FLOAT
    private var discountSubText2Y :Float = Util.INVALID_FLOAT
    private lateinit var clipRect :Rect
    private lateinit var linePaint :Paint

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    init{

        discountText = "25% Discount"
        discountSubText = "Flat 25% off on test packages"
        discountSubText2 = "on your first order"

        normalTextPaint = TextPaint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.tea_green)
            textSize = resources.getDimension(R.dimen.font_14sp)
            isAntiAlias = true
        }

        boldTextPaint = TextPaint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.tea_green)
            textSize = resources.getDimension(com.google.android.material.R.dimen.abc_text_size_medium_material)
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        linePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = resources.getColor(R.color.tea_green)
            strokeWidth = Util.getPixels(2, resources.displayMetrics.density)
        }

        bitmapPaint = Paint().apply {
            style = Paint.Style.FILL
        }
        val nextDrawable : Drawable = resources.getDrawable(R.drawable.baseline_arrow_forward_24)
        val bitmap :Bitmap = Bitmap.createBitmap(nextDrawable.intrinsicWidth, nextDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas :Canvas = Canvas(bitmap)
        nextDrawable.setBounds(0, 0, nextDrawable.intrinsicWidth, nextDrawable.intrinsicHeight)
        nextDrawable.draw(canvas)
        nextBitmap = bitmap

        giftboxBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_giftbox)

        circlePaint = Paint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.tea_green)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        prepareView(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?){
        canvas?.clipRect(clipRect)
        canvas?.drawColor(resources.getColor(R.color.moss))


        canvas?.drawCircle(circleX, circleY, circleRadius, circlePaint)
        canvas?.drawBitmap(nextBitmap, srcNextRect, dstNextRect, bitmapPaint)
        canvas?.drawBitmap(giftboxBitmap, srcGiftRect, dstGiftRect, bitmapPaint)
        canvas?.drawText(discountText, discountTextX, discountTextY, boldTextPaint)
        canvas?.drawText(discountSubText, discountSubTextX, discountSubTextY, normalTextPaint)
        canvas?.drawText(discountSubText2, discountSubText2X, discountSubText2Y, normalTextPaint)

//        canvas?.drawLine(0f, paddingTop.toFloat(), mWidth.toFloat(), paddingTop.toFloat(), linePaint)
    }

    private fun prepareView(proposedWidth :Int, proposedHeight :Int){

        mWidth = proposedWidth
        setPadding(Util.getDp12(), Util.getDp12(), Util.getDp12(), Util.getDp12())

        circleRadius = Math.hypot(Util.getPixels(20, resources.displayMetrics.density).toDouble(),
            Util.getPixels(20, resources.displayMetrics.density).toDouble()).toFloat() / 2
        circleX = mWidth - Util.getDp12() - circleRadius

        srcGiftRect = Rect(0, 0, giftboxBitmap.width, giftboxBitmap.height)

        srcNextRect = Rect(0, 0, nextBitmap.width, nextBitmap.height)

        val availableSpace :Int = mWidth - paddingLeft - paddingRight - (circleRadius*2).toInt()

        val discountWidth :Int = boldTextPaint.measureText(discountText).toInt()
        discountTextY = paddingTop + paddingTop + (-boldTextPaint.fontMetrics.ascent)
        discountTextX = (paddingLeft + availableSpace/2 - discountWidth/2).toFloat()
        val boldTextHeight :Float = (-boldTextPaint.fontMetrics.ascent) + boldTextPaint.fontMetrics.descent

        val discountSubWidth :Int = normalTextPaint.measureText(discountSubText).toInt()
        discountSubTextY = discountTextY + (-normalTextPaint.fontMetrics.ascent + normalTextPaint.fontMetrics.descent) + Util.getDp8()
        discountSubTextX = (paddingLeft + availableSpace/2 - discountSubWidth/2).toFloat()
        val subTextHeight :Float = (-normalTextPaint.fontMetrics.ascent) + normalTextPaint.fontMetrics.descent

        val discountSub2Width :Int = normalTextPaint.measureText(discountSubText2).toInt()
        discountSubText2X = (paddingLeft + availableSpace/2 - discountSub2Width/2).toFloat()
        discountSubText2Y = discountSubTextY + (-normalTextPaint.fontMetrics.ascent + normalTextPaint.fontMetrics.descent)

        mHeight = (paddingTop + paddingTop + boldTextHeight + Util.getDp8() + subTextHeight + subTextHeight+ paddingBottom + paddingBottom).toInt()
        clipRect = Rect(0, paddingTop, mWidth, mHeight - paddingBottom)

        circleY = (mHeight/2).toFloat()

        val dp_8 :Int = Util.getPixels(8, resources.displayMetrics.density).toInt()
        val thirdEdge :Int = Math.sqrt((circleRadius*circleRadius - dp_8*dp_8).toDouble()).toInt()
        val dstNextL :Int = circleX.toInt() - thirdEdge
        val dstNextT :Int = circleY.toInt() - thirdEdge
        val dstNextR :Int = circleX.toInt() + thirdEdge
        val dstNextB :Int = circleY.toInt() + thirdEdge

        dstNextRect = Rect(dstNextL, dstNextT, dstNextR, dstNextB)

        val gdstT :Int = mHeight - paddingBottom - Util.getPixels(32, resources.displayMetrics.density).toInt()
        dstGiftRect = Rect(Util.getDp12(), gdstT,
             Util.getDp12() + Util.getDp40(),
            gdstT + Util.getDp40()
        )
    }
}