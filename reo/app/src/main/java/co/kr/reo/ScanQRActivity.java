package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScanQRActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Activity activity = this;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_q_r);
        sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE);
        String email = sp.getString("email", null);
        Log.d("email", "" + email);
        qrScan = new IntentIntegrator(this);
        qrScan.setCaptureActivity(CaptureForm.class);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "QR인증실패", Toast.LENGTH_LONG).show();
                data = new Intent(ScanQRActivity.this, QRFailActivity.class);
                startActivity(data);
                // todo
            } else {
                Toast.makeText(this, "QR인증완료", Toast.LENGTH_LONG).show();
                String email = sp.getString("email", null);
                String mem_email = "?mem_email=" + email;
                String uri = result.getContents() + mem_email;
                data = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivityForResult(data, 123);
                Log.d("bbb", "" + resultCode);
                data = new Intent(ScanQRActivity.this, QRSucActivity.class);
                startActivity(data);
                // todo
            }
        }

    }
}

