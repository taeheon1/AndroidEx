package kr.co.company.test01

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignUpActivity : AppCompatActivity() {
    lateinit var usernameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText
    lateinit var registerBtn: TextView
    lateinit var loginBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //로그인이 되어있는 경우 바로 리스트 페이지로 보낸다
        if((application as MasterApplication).checkIsLogin()){
            finish()
            startActivity(Intent(this, OutStagramPostListActivity::class.java))
        }else{
        setContentView(R.layout.activity_email_sign_up)
        initView(this@EmailSignUpActivity) //함수 순서가 뒤바뀌면 안된다.
        setupListerner(this)
        }
    }

    fun setupListerner(activity: Activity) {
        registerBtn.setOnClickListener {
            register(this@EmailSignUpActivity)
        }
        loginBtn.setOnClickListener {
            startActivity(
                Intent(this@EmailSignUpActivity, LoginActivity::class.java)
            )
        }
    }

    fun register(activity: Activity) { //가입절차 진행
        val username = usernameView.text.toString()
        val passward1 = userPassword1View.text.toString()
        val passward2 = userPassword2View.text.toString()
        (application as MasterApplication).service.register(username, passward1, passward2)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) { //통신실패
                    Toast.makeText(activity, "가입에 실패 하였습니다.", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) { //통신성공
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "가입에 성공 하였습니다.", Toast.LENGTH_LONG).show()
                        val user = response.body()
                        val token = user!!.token!!
                        savaUserToken(token, activity)
                        (application as MasterApplication).createRetrofit()
                        activity.startActivity(
                            Intent(
                                activity,
                                OutStagramPostListActivity::class.java
                            )
                        )
                    }
                }
            })
    }

    fun savaUserToken(token: String, activity: Activity) { //받아온 토큰을 SharedPreference에 저장을 해준다.
        // 리턴타입은 필요 없다 저장만 해주면 된다. 토큰이 있어야 로그인이 정상적으로 된것이다.
        val sp =
            activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE) // SharedPreferences
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.commit()
    }

    //아이디와 패스워드를 가져올 수 있는 함수
    fun initView(activity: Activity) {
        usernameView = activity.findViewById(R.id.username_inputbox)
        userPassword1View = activity.findViewById(R.id.password1_inputbox)
        userPassword2View = activity.findViewById(R.id.password2_inputbox)
        registerBtn = activity.findViewById(R.id.register)
        loginBtn = activity.findViewById(R.id.login)
    }

    fun getUserName(): String {
        return usernameView.text.toString()
    }

    fun getUserPassword1(): String {
        return userPassword1View.text.toString()
    }

    fun getUserPassword2(): String {
        return userPassword2View.text.toString()
    }
}
