package com.k18054.pressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    var pressureSensor: PressureSensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.Start)
        val stopButton: Button = findViewById(R.id.Stop)
        val fileNameText: EditText = findViewById(R.id.FileName)

        stopButton.isEnabled = false

        startButton.setOnClickListener {
            val otherFileStorage = OtherFileStorage(this,fileNameText.text.toString())
            pressureSensor = PressureSensor(this, otherFileStorage)
            pressureSensor?.start()
            stopButton.isEnabled = true
            startButton.isEnabled = false
        }

        stopButton.setOnClickListener {
            startButton.isEnabled = true
            stopButton.isEnabled = false
            pressureSensor?.stop()
        }

    }
}