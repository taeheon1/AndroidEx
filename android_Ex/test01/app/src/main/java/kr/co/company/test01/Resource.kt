package kr.co.company.test01

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_resource.*

class Resource : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        val ment = resources.getString(R.string.hello)
        Log.d("ment", "ment: " + ment)

        //1
        val ment2 = getString(R.string.hello)
        Log.d("ment", "ment: " + ment2)

        val color = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setBackgroundColor(getColor(R.color.textview_color))
        } else {
            button.setBackgroundColor(resources.getColor(R.color.textview_color))
        }
    }
}
