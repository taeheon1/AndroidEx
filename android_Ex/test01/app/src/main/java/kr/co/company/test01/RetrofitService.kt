package kr.co.company.test01

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    //baseurl 뒷부분이 들어간다.
    @GET("json/students/")
    fun getStudentList(): Call<ArrayList<PersonFromServer>>

    @POST("json/students/")
    fun createStudent(
        @Body params: HashMap<String, Any>
    ): Call<PersonFromServer> // 리턴타입에 맞는 데이터 형식을 넣어주면 된다 . < >  안에

    @POST("json/students/")
    fun createStudentEasy (
        @Body person : PersonFromServer
    ): Call<PersonFromServer>
}