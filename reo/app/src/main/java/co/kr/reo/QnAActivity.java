package co.kr.reo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.google.android.material.navigation.NavigationView;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class QnAActivity extends AppCompatActivity {
    WebSettings webSettings;
    WebView web_qna;
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;
    private int login_check = 0;
    private AutoCompleteTextView autoCompleteTextView;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private DrawerLayout mDrawerLayout;
    int check = 0;
    Intent intent;
    RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a);

        web_qna = (WebView) findViewById(R.id.web_qna);
        web_qna.setWebViewClient(new WebViewClient());
        webSettings = web_qna.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        web_qna.loadUrl("http://192.168.219.108:9090/reo/androidQnaList.reo");
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
