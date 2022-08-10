package com.k18054.queue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    val queue:Queue = Queue()
    var pressureSensor: PressureSensor? = null
    var otherFileStorage: OtherFileStorage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.Start)
        val stopButton: Button = findViewById(R.id.Stop)
        val fileNameText: EditText = findViewById(R.id.FileName)

        stopButton.isEnabled = false

        startButton.setOnClickListener {
            queue.pressureQueue.clear()
            pressureSensor = PressureSensor(this, queue)
            pressureSensor?.start()
            otherFileStorage = OtherFileStorage(this,fileNameText.text.toString(),queue)
            otherFileStorage?.saveAtBatch()
            stopButton.isEnabled = true
            startButton.isEnabled = false
        }

        stopButton.setOnClickListener {
            startButton.isEnabled = true
            stopButton.isEnabled = false
            otherFileStorage?.stop()
            pressureSensor?.stop()
            otherFileStorage = null
        }

    }
}