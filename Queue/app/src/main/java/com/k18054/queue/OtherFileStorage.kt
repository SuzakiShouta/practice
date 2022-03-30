package com.k18054.queue

import android.content.Context
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter

class OtherFileStorage(context: Context, name: String, queue: Queue) {

    val fileAppend : Boolean = true //true=追記, false=上書き
    val context: Context = context
    val fileNameBace : String = "pressure"
    var fileName: String = fileNameBace.plus(name)
    val extension : String = ".csv"
    val filePath: String = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString().plus("/").plus(fileName).plus(extension) //内部ストレージのDocumentのURL
    val queue = queue
    // Handler のオブジェクトを生成
    val handler = Handler(Looper.getMainLooper())
    val runnable = object: Runnable {
        override fun run() {
            Log.d("Queue_queuesize",queue.queue.size.toString())
            val copy = queue.queue.toArray()
            Log.d("Queue_copysize1",copy.size.toString())
            queue.queue.clear()
            Log.d("Queue_copysize2",copy.size.toString())
            saveArrayDeque(copy)
            handler.postDelayed(this, 2000)
        }
    }

    fun writeText(text:String){
        val fil = FileWriter(filePath,fileAppend)
        val pw = PrintWriter(BufferedWriter(fil))
        pw.println((getUnixTime().toString().plus(",").plus(text)))
        pw.close()
    }

    fun getUnixTime() : Long {
        return System.currentTimeMillis()
    }

    // 別スレッドを Runnable で作成
    fun saveAtBatch() {
        // 別スレッドを実行
        handler.post(runnable)
    }

    fun stop() {
        // 別スレッドを停止
        handler.removeCallbacks(runnable)
    }


    fun saveArrayDeque(copy: Array<Any?>) {
        val fil = FileWriter(filePath,fileAppend)
        val pw = PrintWriter(BufferedWriter(fil))
        for (item in copy) {
            pw.println(item.toString())
        }
        pw.close()
    }

}