package co.kr.reo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URISyntaxException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
public class KakaoPayActivity extends AppCompatActivity {

    WebSettings webSettings;
    WebView web_qna;
    private final Handler handler = new Handler();
    final Context myApp = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_pay);

        web_qna = (WebView) findViewById(R.id.mainWebView);
        web_qna.setWebViewClient(new WebViewClient());

        final int room_price = getIntent().getIntExtra("room_price", 0);
        final int off_no = getIntent().getIntExtra("off_no", 0);
        final String off_name = getIntent().getStringExtra("off_name");

        webSettings = web_qna.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        web_qna.addJavascriptInterface(new AndroidHandler(),"android");
        web_qna.loadUrl("http://192.168.219.130:8090/reo/kPayReady/1/1/5000.reo");
        web_qna.setWebViewClient(
                new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        if (url != null && url.startsWith("intent://")) {
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                                if (existPackage != null) {
                                    startActivity(intent);
                                } else {
                                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                                    marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                                    startActivity(marketIntent);
                                }
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (url != null && url.startsWith("market://")) {
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                if (intent != null) {
                                    startActivity(intent);
                                }
                                return true;
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                        view.loadUrl(url);
                        return false;
                    }
                }
        );
    }

    public class AndroidHandler {
        @JavascriptInterface
        public void setMessage(final String argv) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("TAG", "setMessage("+argv+")");
                    if (argv.equals("Success")) {

                        Intent intent = new Intent(KakaoPayActivity.this, MyReservListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        finish();
                    }

                }
            });
        }
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
