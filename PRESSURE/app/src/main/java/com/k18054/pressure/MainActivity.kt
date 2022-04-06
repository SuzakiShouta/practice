package com.k18054.pressure

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    var pressureSensor: PressureSensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fileNameText: EditText = findViewById(R.id.FileName)
        val startButton: Button = findViewById(R.id.Start)
        val stopButton: Button = findViewById(R.id.Stop)
        val ampNumber: EditText = findViewById(R.id.AmpNumber)
        val vibStartButton: Button = findViewById(R.id.VibStart)
        val vibStopButton: Button = findViewById(R.id.VibStop)



        val vibratorManager = if (android.os.Build.VERSION.SDK_INT >= 31) {
            this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        } else {
            TODO("VERSION.SDK_INT < 31")
        }
        val vibrator = vibratorManager.defaultVibrator

        stopButton.isEnabled = false
        vibStopButton.isEnabled = false

        startButton.setOnClickListener {

            stopButton.isEnabled = true
            startButton.isEnabled = false

            val otherFileStorage = OtherFileStorage(this,fileNameText.text.toString())
            pressureSensor = PressureSensor(this, otherFileStorage)
            pressureSensor?.start()
        }

        stopButton.setOnClickListener {

            startButton.isEnabled = true
            stopButton.isEnabled = false

            pressureSensor?.stop()
        }

        vibStartButton.setOnClickListener {

            vibStopButton.isEnabled = true
            vibStartButton.isEnabled = false

//            val timing = longArrayOf(5000,5000)
//            val amplitudes = intArrayOf(0,255)
//            val vibrationEffect = VibrationEffect.createWaveform(timing, amplitudes,-1)
//            vibrator.vibrate(vibrationEffect)

            val vibrationEffect = VibrationEffect.createOneShot(10000000, ampNumber.text.toString().toInt())
            vibrator.vibrate(vibrationEffect)
        }

        vibStopButton.setOnClickListener {

            vibStopButton.isEnabled = false
            vibStartButton.isEnabled = true

            vibrator.cancel()
        }

    }
}