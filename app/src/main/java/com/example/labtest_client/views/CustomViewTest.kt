package com.example.labtest_client.views

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.example.labtest_client.R
import com.example.labtest_client.Util
import kotlin.math.roundToInt

class CustomViewTest : View, OnTouchListener {

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT
    private lateinit var clipPath :Path
    private lateinit var bitmapPaint : Paint
    private lateinit var iconBitmap :Bitmap
    private lateinit var srcRect : Rect
    private lateinit var dstRect :Rect
    private lateinit var textPaintDark : TextPaint
    private lateinit var textPaintLight : TextPaint
    private lateinit var staticLayoutDarkPoint :Point
    private lateinit var staticLayoutLightPoint :Point
    private lateinit var staticLayoutDark :StaticLayout
    private lateinit var staticLayoutLight :StaticLayout
    private lateinit var linePaint :Paint
    private lateinit var linePointStart :Point
    private lateinit var linePointEnd :Point
    private lateinit var discountedPrice :String
    private lateinit var mrp :String
    private lateinit var discountedPricePoint :Point
    private lateinit var mrpTextPaint :TextPaint
    private lateinit var mrpPoint :Point
    private lateinit var btnRect :RectF
    private lateinit var btnPaint :Paint
    private lateinit var btnStrokeRect :RectF
    private lateinit var btnStrokePaint :Paint


    private var touchAreaPadding :Float
    private val animationExpand :Runnable
    private val animationFade :Runnable
    private var rippleRadius :Float? = null
    private var rippleX :Float? = null
    private var rippleY :Float? = null
    private var maxRippleRadius :Float = Util.INVALID_INT.toFloat()
    private lateinit var ripplePaint :Paint

    constructor(context : Context) :super(context)
    constructor(context : Context, attrs : AttributeSet) :super(context, attrs)
    constructor(context : Context, attrs : AttributeSet, defUtils :Int)
            :super(context, attrs, defUtils)

    init {
        bitmapPaint = Paint().apply {
            style = Paint.Style.FILL
        }

        linePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = resources.getColor(R.color.grey)
            strokeWidth = 3f
            strokeCap = Paint.Cap.ROUND
        }

        discountedPrice = "₹400"
        mrp = "₹4̶5̶0̶"

        touchAreaPadding = Util.getDp12().toFloat()
        ripplePaint = Paint(0).apply {
            style = Paint.Style.FILL
        }

        animationExpand = object :Runnable{
            override fun run() {
                rippleRadius?.let{radius ->
                    if(radius < maxRippleRadius){
                        rippleRadius = radius + maxRippleRadius * 0.07f
                        invalidate()
                        postDelayed(this, 10)

                    }else{
                        ripplePaint.color.let {color ->

                            val alpha = getAlpha(color)
                            Log.d(TAG, "run: alpha = ${alpha}")
                            if( alpha > 0){
                                ripplePaint.color = color.adjustAlpha(0.01f)
                                invalidate()
                                postDelayed(this, 10)
                            }else{
                                rippleX = null
                                rippleY = null
                                rippleRadius = null
                                invalidate()
                            }
                        }
                    }
                }
            }
        }

        animationFade = object :Runnable{

            override fun run() {
                ripplePaint.color.let {color ->
                    val alpha = getAlpha(color)
                    Log.d(TAG, "run: alpha = ${alpha}")
                    if( alpha > 0){
                        ripplePaint.color = color.adjustAlpha(0.1f)
                        invalidate()
                        postDelayed(this, 10)
                    }else{
                        rippleX = null
                        rippleY = null
                        rippleRadius = null
                        invalidate()
                    }
                }
            }
        }

        setOnTouchListener(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        prepareView(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        canvas?.clipPath(clipPath)

        canvas?.drawColor(resources.getColor(R.color.skyBlue))
        canvas?.drawBitmap(iconBitmap, srcRect, dstRect, bitmapPaint)

        canvas?.save()
        canvas?.translate(staticLayoutDarkPoint.x.toFloat(), staticLayoutDarkPoint.y.toFloat())
        staticLayoutDark.draw(canvas)
        canvas?.restore()

        canvas?.save()
        canvas?.translate(staticLayoutLightPoint.x.toFloat(), staticLayoutLightPoint.y.toFloat())
        staticLayoutLight.draw(canvas)
        canvas?.restore()

        canvas?.drawLine(linePointStart.x.toFloat(), linePointStart.y.toFloat(), linePointEnd.x.toFloat(), linePointEnd.y.toFloat(), linePaint)
        canvas?.drawText(discountedPrice,discountedPricePoint.x.toFloat(), discountedPricePoint.y.toFloat(), textPaintDark)
        canvas?.drawText(mrp,mrpPoint.x.toFloat(), mrpPoint.y.toFloat(), mrpTextPaint)
        canvas?.drawRoundRect(btnRect, 75f, 75f, btnPaint)
        canvas?.drawRoundRect(btnStrokeRect, 36f, 36f, btnStrokePaint)

        val x :Float = rippleX ?: return
        val y :Float = rippleY ?: return
        val r :Float = rippleRadius ?: return
        canvas?.drawCircle(x, y, r, ripplePaint)

        canvas?.restore()
    }

    private fun prepareView(proposedWidth :Int, proposedHeight :Int){

        setPadding(Util.getDp12(), Util.getDp12(), Util.getDp12(), Util.getDp12())
        mWidth = proposedWidth

        maxRippleRadius = Math.hypot(mWidth.toDouble(), mHeight.toDouble()).toFloat()

        val hindiTypeface :Typeface = Typeface.createFromAsset(context.assets, "fonts/baloo2_medium.ttf")

        textPaintDark = TextPaint().apply {
            color = resources.getColor(R.color.black)
            isAntiAlias = true
            typeface = hindiTypeface
            textSize = Util.getPixels(20, resources.displayMetrics.density)
        }

        textPaintLight = TextPaint().apply {
            color = resources.getColor(R.color.grey)
            isAntiAlias = true
            typeface = hindiTypeface
            textSize = Util.getPixels(16, resources.displayMetrics.density)
        }

        mrpTextPaint = TextPaint().apply {
            color = resources.getColor(R.color.grey)
            isAntiAlias = true
            typeface = hindiTypeface
            textSize = Util.getPixels(16, resources.displayMetrics.density)
        }

        val dstHeightWidth :Float = Util.getPixels(40, resources.displayMetrics.density)
        val dstSpaceHeightWidth :Float = dstHeightWidth + Util.getPixels(5, resources.displayMetrics.density)*2
        val staticLayoutWidth :Int = mWidth - (paddingLeft + Util.getDp12() + dstSpaceHeightWidth + Util.getDp12() + paddingRight ).toInt()

        val srcText :String = "FBS (Fasting Blood Sugar ) Test"
        val lightText :String = "5 tests included"

        staticLayoutDark = StaticLayout(srcText, 0, srcText.length
            , textPaintDark
            , staticLayoutWidth
            , Layout.Alignment.ALIGN_NORMAL
            , 0.6f, 0f, false
        )

        staticLayoutLight = StaticLayout(lightText, 0, lightText.length
            , textPaintLight
            , staticLayoutWidth
            , Layout.Alignment.ALIGN_NORMAL
            , 0.6f, 0f, false
        )
        val staticLayoutHeight :Int= staticLayoutDark.height + staticLayoutLight.height

        linePointStart = Point()
        linePointEnd = Point()

        linePointStart.x = paddingLeft + Util.getDp12()*2
        linePointStart.y = Util.getDp12()*2 + Math.max(dstSpaceHeightWidth.toInt(), staticLayoutHeight) + Util.getDp12()

        linePointEnd.x = mWidth - paddingRight - Util.getDp12()*2
        linePointEnd.y = Util.getDp12()*2 + Math.max(dstSpaceHeightWidth.toInt(), staticLayoutHeight) + Util.getDp12()

        iconBitmap = BitmapFactory.decodeResource(resources, R.drawable.blood_test_icon)

        srcRect = Rect(0, 0, iconBitmap.width, iconBitmap.height)

        val dstL :Int = paddingLeft + Util.getDp12() + Util.getPixels(5, resources.displayMetrics.density).toInt()
        val dstR :Int = dstL + dstHeightWidth.toInt()
        val dstT :Int = Util.getDp12() + Util.getDp12() + staticLayoutHeight/2 - Util.getPixels(20, resources.displayMetrics.density).toInt()
        dstRect = Rect(dstL, dstT, dstR, dstT + Util.getPixels(40, resources.displayMetrics.density).toInt())

        staticLayoutDarkPoint = Point()
        staticLayoutDarkPoint.x = dstR + Util.getDp12()
        staticLayoutDarkPoint.y = Util.getDp12() + Util.getDp12()

        staticLayoutLightPoint = Point()
        staticLayoutLightPoint.x = staticLayoutDarkPoint.x
        staticLayoutLightPoint.y = staticLayoutDarkPoint.y + staticLayoutDark.height


        discountedPricePoint = Point()
        discountedPricePoint.x = Util.getDp12()*2
        discountedPricePoint.y = (linePointStart.y + Util.getDp12() + Util.getPixels(25, resources.displayMetrics.density)).toInt()

        mrpPoint = Point()
        mrpPoint.x = discountedPricePoint.x
        mrpPoint.y = (discountedPricePoint.y - mrpTextPaint.fontMetrics.ascent).toInt()

        val btnStrR :Float = (mWidth - Util.getDp12()*2).toFloat()
        val btnStrT :Float = linePointStart.y + Util.getDp12().toFloat() + Util.getPixels(5, resources.displayMetrics.density)
        val btnStrB :Float = btnStrT + Util.getPixels(40, resources.displayMetrics.density)
        val btnStrL :Float = btnStrR - Util.getPixels(100, resources.displayMetrics.density)

        btnStrokeRect = RectF(btnStrL, btnStrT, btnStrR, btnStrB)

        val btnR :Float = btnStrL - Util.getDp12()
        val btnL :Float = btnR - Util.getPixels(100, resources.displayMetrics.density)
        btnRect = RectF(btnL, btnStrT, btnR, btnStrB)

        btnPaint = Paint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.purple_200)
        }

        btnStrokePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = resources.getColor(R.color.purple_200)
            strokeWidth = 3f
        }

        mHeight = linePointStart.y + (Util.getDp12() + Util.getPixels(50, resources.displayMetrics.density) + Util.getDp12()*2).toInt()

        clipPath = getClipPath(mWidth.toFloat(), mHeight.toFloat(), Util.getDp12().toFloat())
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        event?.let {
            when(it.actionMasked){
                MotionEvent.ACTION_DOWN ->{
                    if(isInsideArea(it.x, it.y)){
                        startRipple(it.x, it.y)
                    }
                }

                MotionEvent.ACTION_UP ->{
//                    stopRipple()
                }
            }
        }
        return false
    }

    fun Int.adjustAlpha(factor : Float) :Int{
        return (this.ushr(24) * factor).roundToInt() shl(24) or (0x00FFFFFF and this)
    }

    private fun getAlpha(color :Int) :Int{
        return (color shr 24) and 0xff
    }

    private fun isInsideArea(x :Float, y :Float) :Boolean{
        if(x <= mWidth - touchAreaPadding && x >= touchAreaPadding &&
            y >= touchAreaPadding && y <= mHeight-touchAreaPadding){

            return true
        }
        return false
    }

    private fun startRipple(x :Float, y :Float){
        rippleX = x
        rippleY = y
        rippleRadius = maxRippleRadius * 0.15f
        ripplePaint.color = resources.getColor(R.color.white_30)

        animationExpand.run()
    }

    private fun stopRipple(){
        if(rippleRadius != null){
            animationFade.run()
        }
    }

    private fun getClipPath(mWidth :Float, mHeight :Float, padding :Float) : Path {

        val path : Path = Path()
        val radius :Float = Util.getPixels(5, resources.displayMetrics.density)
        var oval : RectF = RectF()

        path.moveTo(radius + padding, padding)
        path.lineTo(mWidth- padding - (radius*2), padding)

        oval.set(mWidth - padding - radius*2, padding, mWidth - padding, padding + radius*2)
        path.arcTo(oval, 270f, 90f)

        path.lineTo(mWidth - padding, mHeight - padding)

        oval.set(mWidth - padding - radius*2, mHeight - padding - radius*2, mWidth - padding, mHeight - padding)
        path.arcTo(oval, 0f, 90f)

        path.lineTo(padding + radius*2, mHeight - padding)

        oval.set(padding, mHeight - padding - radius*2, padding + radius*2, mHeight - padding)
        path.arcTo(oval, 90f, 90f)

        path.lineTo(padding, padding + radius*2)

        oval.set(padding, padding, padding + radius*2, padding + radius*2)
        path.arcTo(oval, 180f, 90f)

        return path
    }
}