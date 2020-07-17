package kr.co.company.test01

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class NullSafety : AppCompatActivity() {

    lateinit var lateCar: Car

    class Car(var number: Int){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null_safety)

        val number : Int = 10
        val number1 : Int? = null

//      val number3 = number? + number1
        val number3 = number1?.plus(number)
        Log.d("number", "number3 :" +number3 )

        // 삼항연산자 - > 엘비스 연산자 (?:)
        // Null safety 를 위한 도구
        val number4 = number1 ?: 10

        lateCar = Car(10)
        Log.d("number", "number4 :" +number4)

            fun plus(a: Int, b: Int?) : Int {
            if (b != null) return a+b
            else return a
        }

        fun plus2(a: Int, b: Int?) : Int? {
            return b?.plus(a)
        }
    }

}
