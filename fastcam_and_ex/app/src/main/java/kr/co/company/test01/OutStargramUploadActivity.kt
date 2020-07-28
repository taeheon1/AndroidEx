package kr.co.company.test01

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_out_stargram_upload_activitu.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OutStargramUploadActivity : AppCompatActivity() {
    lateinit var filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_upload_activitu)

        view_pictures.setOnClickListener {
            getPicture()
        }
        upload_post.setOnClickListener {
            uploadPost()
        }
        all_list.setOnClickListener {
            startActivity(Intent(this, OutStagramPostListActivity::class.java))
        }
        my_list.setOnClickListener {
            startActivity(Intent(this, OutStargramMyPostListActivity::class.java))
        }
        user_info.setOnClickListener {
            startActivity(Intent(this, OutStargramUserInfo::class.java))
        }
    }

    fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val uri: Uri = data!!.data!! // 데이터의 타입은 URI로 넘어온다.
            filePath = getImageFilePath(uri)
            Log.d("path", "asd" + filePath)
        }
    }

    //절대경로를 알아내는 함수 -> 함수를 호출하기 전에 권한을 얻어야 한다.
    fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    // 이미지 보내는 함수
    fun uploadPost() {
        val file = File(filePath)
        val fileRequestBody =
            RequestBody.create(MediaType.parse("image/*"), file) //이미지를 RequestBody에 넣어줌.
        val part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            fileRequestBody
        ) //서버한테 보낼때 "image" 로 보내야함. MultipartBody -> Body가 여러개 이다.
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as MasterApplication).service.uploadPost(
            part, content
        ).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    finish()
                    startActivity(
                        Intent(
                            this@OutStargramUploadActivity,
                            OutStargramMyPostListActivity::class.java
                        )
                    )
                }
            }
        })
    }

    fun getContent(): String {
        return content_input.text.toString()
    }
}
