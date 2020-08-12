package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;

import com.bumptech.glide.RequestManager;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class QnaActivity extends AppCompatActivity {
    WebSettings webSettings;
    WebView web_qna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        web_qna = (WebView) findViewById(R.id.web_qna);
        web_qna.setWebViewClient(new WebViewClient());
        webSettings = web_qna.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        web_qna.loadUrl("http://192.168.219.143:8090/reo/androidQnaList.reo");
    }

    @Override
    public void onBackPressed() {
        if (web_qna.getOriginalUrl().equalsIgnoreCase(URL)) {
            super.onBackPressed();
        } else if (web_qna.canGoBack()) {
            web_qna.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
