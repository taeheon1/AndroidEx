package kr.co.company.test01

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {
    lateinit var service: RetrofitService
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this) //stetho 를 init 을 해주어야 실행이된다.
        createRetrofit()
        //chrome://inspect/#devices
    }

    // header를 만든다.
    fun createRetrofit() {
        val header = Interceptor { //나가는 통신을 가로챈다
            val original = it.request()
            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token " +token)
                        .build()
                    it.proceed(request)//proceed 진행한다.
                }
            } else {
                it.proceed(original)
            }
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create((RetrofitService::class.java))
    }

    //login check 함수
    fun checkIsLogin(): Boolean { //로그인을 했는지 않했는지 알아보는 함수 가입을 한경우에 토큰을 받는다. getSharedPreferences 에 토큰값이 있으면 로그인이 된거다.
        var sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if (token != "null") return true
        else return false
    }

    fun getUserToken(): String? {
        var sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if(token =="null") return null
        else return token
    }
}