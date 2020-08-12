package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QRSucActivity extends AppCompatActivity {
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm");
    String formatDate = sdfNow.format(date);
    TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_suc);
        time = (TextView) findViewById(R.id.time);
        time.setText(formatDate+" 입실완료");

        Button qr_suc = (Button) findViewById(R.id.qr_suc);
        qr_suc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRSucActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });
    }

}
