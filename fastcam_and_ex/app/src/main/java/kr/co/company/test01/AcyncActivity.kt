package kr.co.company.test01

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_acync.*
import java.lang.Exception
import java.lang.Thread

class AcyncActivity : AppCompatActivity() {
    var task: BackgroundAsyncTask? = null
    var task1: BackgroundAsyncTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acync)


        start.setOnClickListener {
            task = BackgroundAsyncTask(progressbar, ment)
            task1 = BackgroundAsyncTask(progressbar, ment)
            task?.execute()
            task1?.execute()
        }
        stop.setOnClickListener {
//            task?.cancel(true)  // 취소 클릭시 종료
            startActivity(Intent(this, Intent2::class.java))
        }
    }

    override fun onPause() {
        task?.cancel(true) // 정지시키는 코드를 onPause() life_cycle 에 넣어주는 방법도 있다.
        super.onPause()
    }

}

class BackgroundAsyncTask(
    val progressbar: ProgressBar,
    val progressText: TextView
) : AsyncTask<Int, Int, Int>() { // 첫번째 제네릭 타입은 doInBackground 타입이다.
    // params -> doinBackgoround 에서 사용할 타입 -> 1 번째 제네릭
    // progress -> onProgressUpdate 에서 사용할 타입 -> 2 번째 제네릭
    // result -> onPostExecute 에서 사용할 타입 -> 3 번째 제네릭
    var percent: Int = 0 // 처음에 percent 를 0% 초기화 시키고

    override fun onPreExecute() {
        percent = 0 //실행 할 때마다 0으로 시작
        progressbar.setProgress(percent)
    }

    override fun doInBackground(vararg params: Int?): Int { //vararg int가 여러개 올 수 있다.
        while (isCancelled() == false) { // 내 작업이 취소 됬어 ? 라는 키워드(isCancelled) 리턴 타입은 boolean
            percent++
            if (percent > 100) {
                break
            } else {
                publishProgress(percent)
            }
            try {
                Thread.sleep(100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return percent
    }

    override fun onProgressUpdate(vararg values: Int?) {
        progressbar.setProgress(values[0] ?: 0) //엘비스 연산자 0인경우는 0이다.
        progressText.setText("퍼센트 : " + values[0])
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
    }

    override fun onCancelled() { // 임의적으로 사용자가 취소버튼을 누른 경우
        progressbar.setProgress(0)
        progressText.setText("작업이 취소 되었습니다.")
    }
}
