package co.kr.reo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReservListActivity extends AppCompatActivity {
    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;
    private int login_check = 0;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private AutoCompleteTextView autoCompleteTextView;
    RequestManager glide;
    ////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reserv_list);
        //////////////처음부분//////////////////
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        init();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (login_check == 0) {
                    Toast.makeText(context, "로그인 하세요.", Toast.LENGTH_SHORT).show();

                } else if (login_check == 1) {

                    menuItem.setChecked(true);
                    int id = menuItem.getItemId();
                    String title = menuItem.getTitle().toString();
                    if (id == R.id.account) {
                        Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.book_List) {
                        Intent i = new Intent(activity, MyReservListActivity.class);
                        startActivity(i);
                    } else if (id == R.id.QnA) {
                        Toast.makeText(context, title + ": QnA 정보를 확입합니다.", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.logout) {
                        Toast.makeText(context, "로그아웃 중~", Toast.LENGTH_SHORT).show();

                        new AlertDialog.Builder(context)
                                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        editor.clear();
                                        editor.commit();
                                        activity.finish();
                                        Intent i = new Intent(activity, IndexActivity.class);
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
                drawer.closeDrawers();
                return true;
            }

        });

        View header = navigationView.getHeaderView(0);
        TextView user_login = (TextView) header.findViewById(R.id.user_login);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);

        if (!sp.getString("email", "null").equals("null")) {
            user_login.setText(sp.getString("email", "null"));
            user_name.setText(sp.getString("name", ""));
            login_check = 1;
        }

        user_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if (sp.getString("email", "null").equals("null")) {
                            drawer.closeDrawers();
                            intent = new Intent(context, LoginActivity.class);
                        } else {
                            intent = new Intent(context, MyPageActivity.class);
                        }
                        startActivity(intent);
                    }
                }
        );

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.logout);
        if (login_check == 1) {
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setHorizontallyScrolling(true);

        final ImageView search_map_button = findViewById(R.id.search_map_button);
        search_map_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String addr_name = autoCompleteTextView.getText().toString();
                        Log.d("search_map_name", addr_name);
                        try {
//                            addrList = geocoder.getFromLocationName(addr_name, 3);
//                            if (addrList != null && !addrList.equals(" ")) {
//                                search(addrList);
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        ////////////////////////////////////////

        Call<ArrayList<ReservationDTO>> call = RetrofitSingleton.getInstance().getRetrofitService().getReservListNow(sp.getString("email","null").trim());
        call.enqueue(new Callback<ArrayList<ReservationDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<ReservationDTO>> call, Response<ArrayList<ReservationDTO>> response) {
                Log.d("MainActivity","response_success: " + response.isSuccessful());
                if(response.isSuccessful()){
                    ArrayList<ReservationDTO> myReservList = response.body();
                    Log.d("MainActivity","response_success: " + myReservList.get(0).getOff_name());

                    ReservationAdapter officAdapter = new ReservationAdapter(myReservList, LayoutInflater.from(context),glide,activity);
                    RecyclerView officeRecyclerView = findViewById(R.id.officeboardlist_recycler);
                    officeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    officeRecyclerView.setAdapter(officAdapter);
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ReservationDTO>> call, Throwable t) {
                Log.d("MainActivity","error : " + t.getMessage());
                t.printStackTrace();
            }
        });




    }

    //////////////처음부분//////////////////
    public void init(){
        sp = activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE);
        editor = sp.edit();
        glide = Glide.with(activity);
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
    ////////////////////////////////////////
}
