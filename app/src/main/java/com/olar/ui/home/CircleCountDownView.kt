package com.olar.ui.home


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View

class CircleCountdownView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    interface CountdownListener {
        fun onCountdownFinished()
    }

    private var listener: CountdownListener? = null

    fun setCountdownListener(listener: CountdownListener) {
        this.listener = listener
    }

    private val circlePaint = Paint()
    private val textPaint = Paint()
    private var progress = 0
    private var totalTime = 10000 // 10 seconds
    private var elapsedTime = 0
    private val handler = Handler()
    private val updateInterval = 50L

    init {
        circlePaint.isAntiAlias = true
        circlePaint.color = Color.parseColor("#08D4A1")
        circlePaint.strokeWidth = 20f
        circlePaint.style = Paint.Style.STROKE

        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE
        textPaint.textSize = 100f
        textPaint.textAlign = Paint.Align.CENTER
    }

    private val runnable = object : Runnable {
        override fun run() {
            elapsedTime += updateInterval.toInt()
            progress = (elapsedTime.toFloat() / totalTime * 360).toInt()
            invalidate()

            if (elapsedTime < totalTime) {
                handler.postDelayed(this, updateInterval)
            } else {
                listener?.onCountdownFinished()
            }
        }
    }

    fun startCountdown(totalTimeInMillis: Int) {
        totalTime = totalTimeInMillis
        elapsedTime = 0
        handler.post(runnable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = Math.min(width, height) / 2 - circlePaint.strokeWidth / 2
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint)

        // Draw the arc
        canvas.drawArc(
            circlePaint.strokeWidth / 2, circlePaint.strokeWidth / 2,
            width - circlePaint.strokeWidth / 2, height - circlePaint.strokeWidth / 2,
            -90f, progress.toFloat(), false, circlePaint
        )

        // Draw the remaining seconds in the center
        val remainingTime = ((totalTime - elapsedTime) / 1000).coerceAtLeast(0)
        canvas.drawText(
            remainingTime.toString(),
            width / 2,
            height / 2 + textPaint.textSize / 3,
            textPaint
        )
    }
}