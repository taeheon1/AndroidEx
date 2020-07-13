package kr.co.company.test01

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_shared_preference.*

class SharedPreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        // SharedPreference - 안드로이드에서 사용하는 데이터베이스
        val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        // Mode
        // - MODE_PRIVATE : 생성한 application 에서만 사용가능
        // - MODE_WORLD_READABLE : 다른 application 사용가능 -> 읽을수만 있다.
        // - MODE_WORLD_WRITABLE : 다른 application 사용가능 -> 기록도 가능하다.
        // - MODE_MULTI_PROCESS : 이미 호출되어 사용 중 인지 체크
        // - MODE_APPEND : 기존 preference에 신규로 추가
//        val editor: SharedPreferences.Editor = sharedPreference.edit() 데이터 저장 코드
//        editor.putString("hello1", "안녕하세요") // 값을 넣어줄때
//        editor.commit()

        // 다른 것 이다.
        // sp1 -> Sharedpreference
        // (Key,Value) -> ("Hello", "안녕하세요")
        // sp2 -> Sharedpreference
        // (Key,Value) -> ("Hello", "안녕하세요11")
        save_btn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.putString("hello1", "안녕하세요") // 값을 넣어줄때
            editor.putString("goodbye", "안녕히가세요") // 값을 넣어줄때
            editor.commit()
        }

        load_button.setOnClickListener {
            // SharedPreference에 값을 불러오는 방법
            val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val value1 = sharedPreference.getString("hello1", "데이터 없음")
            val value2 = sharedPreference.getString("goodbye", "데이터 없음")
            Log.d("key-value", "Value : " + value1)
            Log.d("key-value", "Value:" + value2)
        }

        delete_button.setOnClickListener {
            val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.remove("hello")
            editor.commit()
        }

        delete_all_button.setOnClickListener {
            val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.clear()
            editor.commit()
        }
    }
}
