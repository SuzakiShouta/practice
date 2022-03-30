package com.k18054.pressure

import android.content.Context
import android.os.Environment
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.PrintWriter

class OtherFileStorage(context: Context, name: String) {

    val fileAppend : Boolean = true //true=追記, false=上書き
    val context: Context = context
    val fileNameBace : String = "pressure"
    var fileName: String = fileNameBace.plus(name)
    val extension : String = ".csv"
    val filePath: String = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString().plus("/").plus(fileName).plus(extension) //内部ストレージのDocumentのURL

    fun writeText(text:String){
        val fil = FileWriter(filePath,fileAppend)
        val pw = PrintWriter(BufferedWriter(fil))
        pw.println((getUnixTime().toString().plus(",").plus(text)))
        pw.close()
    }

    fun getUnixTime() : Long {
        return System.currentTimeMillis()
    }

}