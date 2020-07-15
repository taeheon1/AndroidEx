package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service =
            retrofit.create(RetrofitService::class.java) // service 를 통하여 데이터 통신을 진행한다. Retrofit을 사용할 준비가 됨.

        //GET 요청방식
        service.getStudentList().enqueue(object :
            Callback<ArrayList<PersonFromServer>> {  //enqueue ->  대기줄에 올려놓는다 라는뜻 통신을 올려놈.
            override fun onFailure(
                call: Call<ArrayList<PersonFromServer>>,
                t: Throwable
            ) { // -> 통신실패
                Log.d("retrofitt", "ERROR") // 간단하게 에러메세지 보는 용도로만 사용된다.
            }

            override fun onResponse( // -> 통신성공
                call: Call<ArrayList<PersonFromServer>>,
                response: Response<ArrayList<PersonFromServer>>
            ) {
                if (response.isSuccessful) { //200번때 통신이 잘된경우 boolean 타입
                    val personList = response.body()
                    Log.d("retrofitt", "res: " + personList?.get(0)?.age)
                    val code = response.code()
                    Log.d("retrofitt", "code: " + code)
                    val error = response.errorBody()
                    Log.d("retrofitt", " code: " + error)
                    val header = response.headers()
                    Log.d("retrofitt", " code: " + header)

                }
            }
        })

        //POST요청방식 (1)
//        val params = HashMap<String, Any>()
//        params.put("name", "김개똥")
//        params.put("age", 20)
//        params.put("intro", "안녕하세여?")
//        service.createStudent(params).enqueue(object : Callback<PersonFromServer> {
//            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
//            }
//
//            override fun onResponse(
//                call: Call<PersonFromServer>,
//                response: Response<PersonFromServer>
//            ) {
//                if (response.isSuccessful) {
//                    val person = response.body()
//                    Log.d("retrofitt", "name :" + person?.name)
//                }
//            }
//        })

        //POST요청방식(2)
        val person = PersonFromServer(name = "김철수", age = 12, intro = "안녕하세요 철수입니다.")
        service.createStudentEasy(person).enqueue(object : Callback<PersonFromServer> {
            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PersonFromServer>,
                response: Response<PersonFromServer>
            ) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log.d("retrofitt", "name :" + person?.name)
                }
            }
        })
    }
}
