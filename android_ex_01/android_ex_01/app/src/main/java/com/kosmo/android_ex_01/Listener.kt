package com.kosmo.android_ex_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_listener.*

class Listener : AppCompatActivity() {
    var number= 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listener)

        // 뷰를 activity로 가져오는 방법
        // 1> 직접찾아서 가져온다.
//        val textView : TextView = findViewById(R.id.hello)

        // 2> xml을 import 해서 가져온다.
//        hello.

        // 사용자가 클릭했을때 호출이 된다. 안드로이드 OS 가 찾는다.
        // 람다식 방식
        hello.setOnClickListener {
            Log.d("click", "Click!")
        }

        //익명 함수 방식
        hello.setOnClickListener(object : View.OnClickListener{
            override  fun onClick(v: View?){
                Log.d("click","Click!!")
            }
        })

        // 이름이 필요한경우(click)
        val click = object : View.OnClickListener{
            override fun onClick(v: View?){
                hello.setText("안녕하세요")
                image.setImageResource(R.drawable.ic_launcher_background)
                number += 10
                Log.d("number",""+ number)
            }
        }
        hello.setOnClickListener(click)


        //뷰를 조작하는 함수들

    }
}
