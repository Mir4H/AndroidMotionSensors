package com.example.piirto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint()
    var placeX = 0f
    var placeY = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.MAGENTA
        canvas?.drawOval(placeX, placeY, placeX + 100f, placeY + 100f, paint)
    }

    fun setXY(x: Float, y: Float) {
        placeX = x
        placeY = y
        invalidate()
    }
}