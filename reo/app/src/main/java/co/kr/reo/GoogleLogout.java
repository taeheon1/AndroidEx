package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class GoogleLogout extends AppCompatActivity implements View.OnClickListener {
    Button btnLogout;
    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_logout);

        btnLogout = (Button)findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(this);

    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                signOut();
                finishAffinity();
                break;
        }
    }
}
