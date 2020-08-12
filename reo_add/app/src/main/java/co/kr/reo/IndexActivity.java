package co.kr.reo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.RequestManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndexActivity extends AppCompatActivity {
    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ////////////////////////////////////////

    ViewFlipper v_fllipper;
    Intent intent;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////처음부분//////////////////
        Init first = new Init(activity,context);
        setSupportActionBar(first.getToolbar());
        mAppBarConfiguration = first.getAppBarConfiguration();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, first.getNavController(), mAppBarConfiguration);
        NavigationUI.setupWithNavController(first.getNavigationView(), first.getNavController());
        first.firstinit();
        ///////////////////////////////////////


        /////////////////////index부분/////////////////
        int images[] = {
                R.drawable.office1,
                R.drawable.office2,
                R.drawable.office3,
                R.drawable.office4
        };

        v_fllipper = findViewById(R.id.index_img);
        for (int img : images) {
            fllipperImages(img);
        }

        Button off_btn1 = (Button) findViewById(R.id.off_btn1); //오피스
        Button off_btn2 = (Button) findViewById(R.id.off_btn2); //스터디
        Button off_btn3 = (Button) findViewById(R.id.off_btn3); //회의실
        Button off_btn4 = (Button) findViewById(R.id.off_btn4); //다목적홀
        TextView off_btn5 = (TextView) findViewById(R.id.off_btn5); //지도검색

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.off_btn1:
                        intent = new Intent(activity, BoardListActivity.class);
                        intent.putExtra("name", "오피스");
                        startActivity(intent);
                        break;
                    case R.id.off_btn2:
                        intent = new Intent(activity, BoardListActivity.class);
                        intent.putExtra("name", "스터디");
                        startActivity(intent);
                        break;
                    case R.id.off_btn3:
                        intent = new Intent(activity, BoardListActivity.class);
                        intent.putExtra("name", "회의실");
                        startActivity(intent);
                        break;
                    case R.id.off_btn4:
                        intent = new Intent(activity, BoardListActivity.class);
                        intent.putExtra("name", "다목적홀");
                        startActivity(intent);
                        break;
                    case R.id.off_btn5:
                        intent = new Intent(activity, MapPageActivity.class);
                        intent.putExtra("name", "지도검색");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        };

        off_btn1.setOnClickListener(onClickListener);
        off_btn2.setOnClickListener(onClickListener);
        off_btn3.setOnClickListener(onClickListener);
        off_btn4.setOnClickListener(onClickListener);
        off_btn5.setOnClickListener(onClickListener);

        items.add("오피스");
        items.add("스터디");
        items.add("회의실");
        items.add("다목적홀");
        //////////////////////////////////////////////////
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // 이미지 슬라이더 구현 메서드//////
    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(image);

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(4000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(this, android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    /////////////////////////////////////


}
