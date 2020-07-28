package kr.co.company.test01

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_intent.*
import kotlinx.android.synthetic.main.activity_intent2.*

class Intent1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        change_activity.setOnClickListener {

//            val intent = Intent(this@Intent1, Intent2::class.java) //인텐트1, 이동할 위치 인텐트 2

            // Kye, value 방식 -> key와 value를 쌍으로 만들어 저장한다. -> Dictionary
//            intent.putExtra("number1", 1) //정보를 보낸다. 여러개를 보낼수 있다. 여러개를 보낼때 이름을 지어줌
//            intent.putExtra("number2", 2)
//            startActivity(intent)

//            val intent2 = Intent(this@Intent1, Intent2::class.java)
//            //Apply ->  코드를 읽기 편하게 Apply에 putExtra 를 넣어준다.
//            intent2.apply {
//                this.putExtra("number1", 1)
//                this.putExtra("number2", 1)
//            }
//            startActivityForResult(intent2, 200)

            //암시적 인텐트
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com")) //  Action_view 뒤에있는걸 보여주는것
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 20){
            Log.d("number", "111")
            Log.d("number", "" + requestCode)
            var result = data?.getIntExtra("result",0)
        }

        Log.d("number", "" + resultCode)
    }
}
