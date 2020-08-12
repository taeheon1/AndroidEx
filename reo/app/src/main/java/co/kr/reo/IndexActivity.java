package co.kr.reo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndexActivity extends AppCompatActivity implements TextWatcher {
    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private Activity activity = this;
    int check = 0;

    private Retrofit retrofit;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RequestManager glide;
    ViewFlipper v_fllipper;
    Intent intent;
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        init();
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
        ViewGroup off_btn5 = (ViewGroup) findViewById(R.id.off_btn5); //지도검색
        ViewGroup off_btn6 =  (ViewGroup) findViewById(R.id.off_btn6); //QR인증
        final RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        off_btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(IndexActivity.this, ScanQRActivity.class);
                String email = sp.getString("email",null);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.off_btn1:
                        intent = new Intent(IndexActivity.this, BoardListActivity.class);
                        intent.putExtra("name", "오피스");
                        startActivity(intent);
                        break;
                    case R.id.off_btn2:
                        intent = new Intent(IndexActivity.this, BoardListActivity.class);
                        intent.putExtra("name", "스터디");
                        startActivity(intent);
                        break;
                    case R.id.off_btn3:
                        intent = new Intent(IndexActivity.this, BoardListActivity.class);
                        intent.putExtra("name", "회의실");
                        startActivity(intent);
                        break;
                    case R.id.off_btn4:
                        intent = new Intent(IndexActivity.this, BoardListActivity.class);
                        intent.putExtra("name", "다목적홀");
                        startActivity(intent);
                        break;
                }
            }
        };
        off_btn1.setOnClickListener(onClickListener);
        off_btn2.setOnClickListener(onClickListener);
        off_btn3.setOnClickListener(onClickListener);
        off_btn4.setOnClickListener(onClickListener);

        items.add("오피스");
        items.add("스터디");
        items.add("회의실");
        items.add("다목적홀");

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        EditText search_text_all = findViewById(R.id.search_text_all);
        search_text_all.setHorizontallyScrolling(true);

        ImageView search_button_all = findViewById(R.id.search_button_all);
        //        search_button_all.setOnClickListener(
//        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.menu);  //버튼 이미지 지정

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (check == 0) {
                    Toast.makeText(context, "로그인 하세요.", Toast.LENGTH_SHORT).show();

                } else if (check == 1) {

                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    String title = menuItem.getTitle().toString();
                    if (id == R.id.account) {
                        Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(IndexActivity.this, MyPageActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.book_List) {
                        Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.QnA) {
                        Toast.makeText(context, title + ": QnA 정보를 확입합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(IndexActivity.this, QnAActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.logout) {
                        Toast.makeText(context, "로그아웃 중~", Toast.LENGTH_SHORT).show();

                        new AlertDialog.Builder(context)
                                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        editor.clear();
                                        editor.commit();
                                        activity.finish();
                                        Intent i = new Intent(IndexActivity.this, BoardListActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(i);
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                }).show();
                    }
                }
                mDrawerLayout.closeDrawers();
                return true;
            }

        });

        View header = navigationView.getHeaderView(0);
        TextView user_login = (TextView) header.findViewById(R.id.user_login);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);

        if (!sp.getString("email", "null").equals("null")) {
            user_login.setText(sp.getString("email", "null"));
            user_name.setText(sp.getString("name", ""));
            check = 1;
        }

        user_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if (sp.getString("email", "null").equals("null")) {
                            mDrawerLayout.closeDrawers();
                            intent = new Intent(context, Login.class);

                        } else {
                            intent = new Intent(context, MyPageActivity.class);
                        }
                        startActivity(intent);
                    }
                }
        );

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.logout);
        if (check == 1) {
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 이미지 슬라이더 구현 메서드
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

    public void init() {

        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.100:9090/reo/android/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE);
        editor = sp.edit();

        glide = Glide.with(activity);

    }

    //필수
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}