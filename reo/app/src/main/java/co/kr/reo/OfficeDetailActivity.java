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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfficeDetailActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context = this;
    private Activity activity = this;
    int check = 0;

    private Retrofit retrofit;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_detail);

        init();

        EditText search_text_all = findViewById(R.id.search_text_all);
        search_text_all.setHorizontallyScrolling(true);

        ImageView search_button_all = findViewById(R.id.search_button_all);
//        search_button_all.setOnClickListener(
//
//        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.menu);  //버튼 이미지 지정

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
                    } else if (id == R.id.book_List) {
                        Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.QnA) {
                        Toast.makeText(context, title + ": QnA 정보를 확입합니다.", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.logout){
                        Toast.makeText(context, "로그아웃 중~", Toast.LENGTH_SHORT).show();

                        new AlertDialog.Builder(context)
                                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        editor.clear();
                                        editor.commit();
                                        activity.finish();
                                        Intent i = new Intent(context , OfficeDetailActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

        if(!sp.getString("email","null").equals("null")){
            user_login.setText(sp.getString("email","null"));
            user_name.setText(sp.getString("name",""));
            check = 1;
        }

        user_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(sp.getString("email","null").equals("null")){
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
        if(check == 1){
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }

    }

    public void init(){
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.137:9090/reo/android/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sp = activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE);
        editor = sp.edit();

        glide = Glide.with(activity);
    }

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
