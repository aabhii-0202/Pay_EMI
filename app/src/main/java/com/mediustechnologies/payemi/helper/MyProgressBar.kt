package com.mediustechnologies.payemi.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar
import com.mediustechnologies.payemi.R

class MyProgressBar(context: Context?, attrs: AttributeSet?) : ProgressBar(context, attrs) {
    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        val linePaint = Paint()
        linePaint.color = resources.getColor(R.color.grey)
        val lineWidth = 5f
        linePaint.strokeWidth = lineWidth
        canvas.drawLine(
            width * progress / 100f, -5f,
            width * progress / 100f, (height + 2).toFloat(),
            linePaint
        )
    }
}