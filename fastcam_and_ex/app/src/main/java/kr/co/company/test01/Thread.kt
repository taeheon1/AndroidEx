package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_thread.*
import java.lang.Thread
import kotlin.concurrent.thread

class Thread : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        val runnable: Runnable = object :Runnable{
            override fun run(){
                Log.d("thread-1", "Thread2 is made")
            }
        }
        val thread: Thread = Thread(runnable)
        button.setOnClickListener {
            thread.start()
        }
        Thread(object :Runnable{
            override fun run(){
                Log.d("thread-1", "Thread3 is made")
                runOnUiThread{
                    button.setBackgroundColor(getColor(R.color.textview_color))
                }
            }
        }).start()
    }
}
