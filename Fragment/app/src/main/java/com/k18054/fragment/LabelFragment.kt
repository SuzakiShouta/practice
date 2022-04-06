package com.k18054.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

//①Fragmentを継承したclassを作る　さっきと一緒だね
class LabelFragment: Fragment(){

    //③argumentに入れておいた値を取り出す
    private var counter = 0
    private lateinit var counterLabel : TextView

    //onCreateはフラグメント生成時に呼ばれる
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        counter = savedInstanceState?.getInt("counter")
            ?:arguments?.getInt("counter")
                    ?: 0

    }

    //取り出した値をViewに表示する
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_label,container,false)
        counterLabel = view.findViewById(R.id.textView)
        counterLabel.text = counter.toString()
        return view
    }
    //③

    //⑤onSaveInstanceState:フラグメントが停止する際に呼ばれる
    //ここでカウンターを保存する
    override fun onSaveInstanceState(outState:Bundle){
        outState.putInt("counter",counter)
    }
    //⑤

    //④カウンターをインクリメントするメソッド
    fun update(){
        counter++
        counterLabel.text = counter.toString()
    }
    //④
}
//①

//②このフラグメントのインスタンスを生成する関数
//フラグメント生成時にBundleにパラメータを詰めargumentプロパティに設定する
//ここにカウンターの値を保持する
// https://qiita.com/orimomo/items/0472a52f90d14bef0c89
fun newLabelFragment(value : Int):LabelFragment{
    val fragment = LabelFragment()

    val args = Bundle()
    args.putInt("counter",value)

    fragment.arguments = args
    return fragment
}
//②
