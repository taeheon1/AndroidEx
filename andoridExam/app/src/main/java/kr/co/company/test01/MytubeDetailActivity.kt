package kr.co.company.test01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_mytube_detail.*

class MytubeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mytube_detail)


        val url = intent.getStringExtra("video_url")
        val mediaController = MediaController(this@MytubeDetailActivity) //비디오 영상을 재생하기 위해 필요한 컨트롤러
        video_view.setVideoPath(url)
        video_view.requestFocus()
        video_view.start() //영상재생
        mediaController.show()
        // 버튼을 만들어서 정지, 세로, 가로 등 추가시켜 줄 수 있다.
        // Exoplayer -> 안드로이드에서 영상을 훨 씬더 많은 기능들로 만들어 줄 수 있다.
        // drm - > digital right management
    }
}
