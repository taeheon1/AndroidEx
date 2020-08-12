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

public class BoardListActivity extends AppCompatActivity {
    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;
    ////////////////////////////////////////
    RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);
        init();
        ////////////////처음부분//////////////////
        Init first = new Init(activity,context);
        setSupportActionBar(first.getToolbar());
        mAppBarConfiguration = first.getAppBarConfiguration();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, first.getNavController(), mAppBarConfiguration);
        NavigationUI.setupWithNavController(first.getNavigationView(), first.getNavController());
        first.firstinit();
        ////////////////////////////////////////

        String type = getIntent().getStringExtra("name");
        Call<ArrayList<OfficeDTO>> call = RetrofitSingleton.getInstance().getRetrofitService().getOffListType(type);
        call.enqueue(new Callback<ArrayList<OfficeDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<OfficeDTO>> call, Response<ArrayList<OfficeDTO>> response) {
                Log.d("MainActivity","response_success: " + response.isSuccessful());
                if(response.isSuccessful()){
                    ArrayList<OfficeDTO> office = response.body();
                    Log.d("MainActivity","response_success: " + office);
                    OfficeAdapter officAdapter = new OfficeAdapter(office, LayoutInflater.from(context),glide,activity);
                    RecyclerView officeRecyclerView = findViewById(R.id.officeboardlist_recycler);
                    officeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    officeRecyclerView.setAdapter(officAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<OfficeDTO>> call, Throwable t) {
                Log.d("MainActivity","error" + t.getMessage());
            }
        });
    }
    //////////////처음부분//////////////////
    public void init(){
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
