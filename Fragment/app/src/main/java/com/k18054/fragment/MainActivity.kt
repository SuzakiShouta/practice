package com.k18054.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(),ButtonFragment.OnButtonClickListener {

    //ボタンがクリックされたらラベルフラグメントを呼び出す
    override fun onButtonClicked() {
        val fragment = supportFragmentManager.findFragmentByTag("labelFragment") as LabelFragment
        fragment.update()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //フラグメントの有無を確認し動的にフラグメントを追加する
        if (supportFragmentManager.findFragmentByTag("labelFragment") == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.frameLayout, newLabelFragment(0),"labelFragment")
                .commit()
        }
    }
}