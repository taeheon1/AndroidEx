package kr.co.company.test01

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_out_stargram_user_info.*

class OutStargramUserInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_user_info)

        all_list.setOnClickListener{ startActivity(Intent(this, OutStagramPostListActivity::class.java)) }
        my_list.setOnClickListener{ startActivity(Intent(this, OutStargramMyPostListActivity::class.java)) }
        upload.setOnClickListener{ startActivity(Intent(this, OutStargramUploadActivity::class.java)) }

        logout.setOnClickListener {
            val sp = getSharedPreferences("login_sp" , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("login_sp", "null")
            editor.commit()
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
