package com.k18054.practice

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var AccSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        AccSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }

    //センサーに何かしらのイベントが発生したときに呼ばれる
    override fun onSensorChanged(event: SensorEvent) {
        var sensorX: Float
        var sensorY: Float
        var sensorZ: Float
        // 全て
        if (event.sensor.type === Sensor.TYPE_LINEAR_ACCELERATION) {
            sensorX = event.values[0]
            sensorY = event.values[1]
            sensorZ = event.values[2]
            val strTmp = """加速度センサー
                         X: $sensorX
                         Y: $sensorY
                         Z: $sensorZ"""
            val textView: TextView = findViewById(R.id.textView)
            textView.setText(strTmp)
        }
    }

    //センサの精度が変更されたときに呼ばれる
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    //アクティビティが閉じられたときにリスナーを解除する
    override fun onPause() {
        super.onPause()
        //リスナーを解除しないとバックグラウンドにいるとき常にコールバックされ続ける
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        //リスナーとセンサーオブジェクトを渡す
        //第一引数はインターフェースを継承したクラス、今回はthis
        //第二引数は取得したセンサーオブジェクト
        //第三引数は更新頻度 UIはUI表示向き、FASTはできるだけ早く、GAMEはゲーム向き
        sensorManager.registerListener(this, AccSensor, SensorManager.SENSOR_DELAY_UI)
    }
}
