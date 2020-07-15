package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_phone_book_detail.*

class PhoneBookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_book_detail)

        getPersonInfo()

        back.setOnClickListener {
            onBackPressed() //  사용자가 뒤로가기 버튼을 누른 동작.
        }
    }

    fun getPersonInfo(){
        val name = intent.getStringExtra("name")
        val number = intent.getStringExtra("number")

        person_detail_name.setText(name)
        person_detail_number.setText(number)
    }
}

