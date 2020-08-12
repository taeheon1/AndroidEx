package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Intro extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(getApplicationContext(),
                    IndexActivity.class);
            startActivity(intent);
            overridePendingTransition(R.animator.fade_in, R.anim.hold);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

    }

    @Override
    protected void onResume() {

        super.onResume();
        handler.postDelayed(runnable, 800);
    }

    @Override
    protected void onPause() {

        super.onPause();
        handler.removeCallbacks(runnable);
    }
}
