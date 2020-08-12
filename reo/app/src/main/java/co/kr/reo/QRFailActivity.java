package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QRFailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_fail);

       Button qr_fail = (Button)findViewById(R.id.qr_fail);
       qr_fail.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent = new Intent(QRFailActivity.this, IndexActivity.class);
                       startActivity(intent);
                   }
               }
       );
    }
}
