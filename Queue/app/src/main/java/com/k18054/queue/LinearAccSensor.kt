package com.k18054.queue

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class LinearAccSensor(context: Context, queue: Queue): SensorEventListener {

    val context = context
    val queue = queue
    private lateinit var sensorManager: SensorManager
    private var AccSensor: Sensor? = null

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        AccSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    fun start() {
        sensorManager.registerListener(this, AccSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        var pressure = event.values[0]
        queue.linerAccQueue.add(TimeManager().getUnixTime().toString().plus(",").plus(pressure))
        Log.d("LinerAcc",queue.linerAccQueue.size.toString())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}