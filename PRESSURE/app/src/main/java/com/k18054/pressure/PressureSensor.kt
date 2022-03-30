package com.k18054.pressure

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class PressureSensor(context: Context, otherFileStorage: OtherFileStorage): SensorEventListener {

    val context = context
    val otherFileStorage = otherFileStorage
    private lateinit var sensorManager: SensorManager
    private var AccSensor: Sensor? = null

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        AccSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
    }

    fun start() {
        sensorManager.registerListener(this, AccSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        var pressure = event.values[0]
        otherFileStorage.writeText(pressure.toString())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}