package co.kr.reo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MyPageActivity extends AppCompatActivity {
    WebSettings webSettings;
    WebView web_myPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        web_myPage = (WebView) findViewById(R.id.web_qna);
        web_myPage.setWebViewClient(new WebViewClient());

        webSettings = web_myPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_myPage.loadUrl("http://192.168.219.108:9090/reo/androidmypage.reo");
    }

    @Override
    public void onBackPressed() {
        if (web_myPage.getOriginalUrl().equalsIgnoreCase(URL)) {
            super.onBackPressed();
        } else if (web_myPage.canGoBack()) {
            web_myPage.goBack();
        } else {
            super.onBackPressed();
        }
}
}
