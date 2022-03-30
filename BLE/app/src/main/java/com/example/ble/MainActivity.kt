package com.example.ble

import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.altbeacon.beacon.*


class MainActivity : AppCompatActivity(),BeaconConsumer {

    private lateinit var beaconManager: BeaconManager

    //iBeacon認識のためのフォーマット設定
    private val IBEACON_FORMAT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(IBEACON_FORMAT))
        val mRegion = Region("iBeacon", null, null, null)

        button.setOnClickListener{
            try {
                Log.d("iBeacon", "try!")
                //Beacon情報の監視を開始
//            beaconManager.foregroundScanPeriod = 500L
                beaconManager.startMonitoringBeaconsInRegion(mRegion)
                beaconManager.startRangingBeaconsInRegion(mRegion);
            } catch (e: RemoteException) {
                Log.d("iBeacon", e.message)
                e.printStackTrace()
            }
        }

    }
    override fun onResume() {
        super.onResume()
        beaconManager.bind(this)
        //サービス開始
    }

    override fun onPause() {
        super.onPause()
        beaconManager.unbind(this)
        //サービス終了
    }

    override fun onBeaconServiceConnect() {
        Log.d("iBeacon", "Connect!")
        beaconManager.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region) {
                // 領域侵入
                Log.d("MYE", "Enter")
            }

            override fun didExitRegion(region: Region) {
                // 領域退出
                Log.d("MYE", "EXit")
            }

            override fun didDetermineStateForRegion(i: Int, region: Region) {
                // 領域に対する状態が変化
                Log.d("MYE", "didDetermineStateForRegion")
            }
        })

        beaconManager.addRangeNotifier { beacons, region -> //検出したBeaconの情報を全てlog出力
            Log.d("iBeacon", "Enter Region")
            for (beacon in beacons) {
                Log.d(
                        "MyActivity", "UUID:" + beacon.id1 + ", major:"
                        + beacon.id2 + ", minor:" + beacon.id3 + ", RSSI:"
                        + beacon.rssi + ", TxPower:" + beacon.txPower
                        + ", Distance:" + beacon.distance
                )
            }
        }
        beaconManager?.addRangeNotifier(mRangeNotifier)
    }
    private val mRangeNotifier = RangeNotifier { beacons, region ->
        // 検出したビーコンの情報を全部Logに書き出す

        for (beacon in beacons) {
            Log.d("tag", "UUID:" + beacon.id1 + ", major:" + beacon.id2 +
                    ", minor:" + beacon.id3 + ", Distance:" + beacon.distance +
                    ",RSSI" + beacon.rssi + ", TxPower" + beacon.txPower)
        }
    }
}