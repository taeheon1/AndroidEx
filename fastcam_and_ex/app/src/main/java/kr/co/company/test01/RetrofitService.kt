package kr.co.company.test01


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

// 사용설명서를 만들어놈 무조건 이렇게 인터페이스를 만들어서 사용해야 한다.
interface RetrofitService {
    //baseurl 뒷부분이 들어간다.
    @GET("json/students/")  //어디로 보낼건지 baseUrl 뒷부분을 적어준다.
    fun getStudentList(): Call<ArrayList<PersonFromServer>> //약속이다. Call 안에 넣어주어야 한다.

    @POST("json/students/")
    fun createStudent(
        @Body params: HashMap<String, Any>
    ): Call<PersonFromServer> // 리턴타입에 맞는 데이터 형식을 넣어주면 된다 . < >  안에

    @POST("json/students/")
    fun createStudentEasy(
        @Body person: PersonFromServer
    ): Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("instagram/post/list/all/")
    fun getAllPosts(): Call<ArrayList<Post>>

    @Multipart
    @POST("instagram/post/")
    fun uploadPost(
        @Part image: MultipartBody.Part,
        @Part("content") requestBody: RequestBody
    ): Call<Post>

    @GET("instagram/post/list/")
    fun getUserPostList(): Call<ArrayList<Post>>

    @GET("youtube/list/")
    fun getYoutubeList(): Call<ArrayList<Youtube>>

    @GET("melon/list")
    fun getSongList(): Call<ArrayList<Song>>
}