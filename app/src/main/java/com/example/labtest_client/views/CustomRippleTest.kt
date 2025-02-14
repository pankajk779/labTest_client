package com.example.labtest_client.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.example.labtest_client.R
import com.example.labtest_client.Util
import kotlin.math.roundToInt

class CustomRippleTest : View, OnTouchListener{

    private val TAG :String = this.javaClass.name
    private var mWidth :Int = Util.INVALID_INT
    private var mHeight :Int = Util.INVALID_INT
    private lateinit var clipPath :Path
    private var touchAreaPadding :Float
    private val animationExpand :Runnable
    private val animationFade :Runnable
    private var rippleRadius :Float? = null
    private var rippleX :Float? = null
    private var rippleY :Float? = null
    private var maxRippleRadius :Float = Util.INVALID_INT.toFloat()
    private lateinit var ripplePaint :Paint

    constructor(context : Context) :super(context)
    constructor(context :Context, attrs :AttributeSet) :super(context, attrs)
    constructor(context :Context, attrs :AttributeSet, defUtils :Int) :super(context, attrs, defUtils)

    init{
        touchAreaPadding = Util.getDp12().toFloat()
        ripplePaint = Paint(0).apply {
            style = Paint.Style.FILL
        }

        animationExpand = object :Runnable{
            override fun run() {
                rippleRadius?.let{radius ->
                    if(radius < maxRippleRadius){
                        rippleRadius = radius + maxRippleRadius * 0.1f
                        invalidate()
                        postDelayed(this, 10)

                    }else{
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

        val x :Float = rippleX ?: return
        val y :Float = rippleY ?: return
        val r :Float = rippleRadius ?: return
        canvas?.drawCircle(x, y, r, ripplePaint)

        canvas?.restore()
    }

    private fun prepareView(proposedWidth :Int, proposedHeight :Int){
        mWidth = proposedWidth
        mHeight = mWidth

        maxRippleRadius = Math.hypot(mWidth.toDouble(), mHeight.toDouble()).toFloat()

        clipPath = getClipPath(mWidth.toFloat(), mHeight.toFloat(), Util.getDp12().toFloat())
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        event?.let {
            when(it.actionMasked){
                MotionEvent.ACTION_DOWN ->{
                    if(isInsideArea(it.x, it.y)){
                        startRipple(it.x, it.y)
                    }
                    parent.requestDisallowInterceptTouchEvent(true)
                }

                MotionEvent.ACTION_UP ->{
//                    stopRipple()
                }
            }
        }
        return true
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
        val radius :Float = Util.getPixels(12, resources.displayMetrics.density)
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