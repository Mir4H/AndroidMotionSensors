package com.example.piirto

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var mSensor: Sensor? = null
    private var placeX = 100f
    private var placeY = 100f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        findViewById<MyView>(R.id.myView).setXY(placeX, placeY)
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        findViewById<TextView>(R.id.sensorTextX).text = getString(R.string.xaxis, String.format("%.2f", event!!.values[0]))
        findViewById<TextView>(R.id.sensorTextY).text = getString(R.string.yaxis, String.format("%.2f", event.values[1]))

        val mView = findViewById<MyView>(R.id.myView)
        val measureW = mView.width
        val measureH = mView.height

        placeX -= event.values[0]*2f //doubled the speed
        placeY += event.values[1]*2f

        if (placeX >= measureW-100f) placeX = measureW-100f
        if (placeX <= 0f) placeX = 0f
        if (placeY >= measureH-100f) placeY = measureH-100f
        if (placeY <= 0f) placeY = 0f

        mView.setXY(placeX, placeY)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()

        mSensor?.also { accelerometer ->
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

}