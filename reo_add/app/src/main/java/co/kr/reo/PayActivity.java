package co.kr.reo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Timestamp;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;
    ////////////////////////////////////////

    private SliderLayout office_detail_img_slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //////////////처음부분//////////////////
        Init first = new Init(activity, context);
        setSupportActionBar(first.getToolbar());
        mAppBarConfiguration = first.getAppBarConfiguration();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, first.getNavController(), mAppBarConfiguration);
        NavigationUI.setupWithNavController(first.getNavigationView(), first.getNavController());
        first.firstinit();
        ////////////////////////////////////////


        final int room_price = getIntent().getIntExtra("room_price", 0);
        final int off_no = getIntent().getIntExtra("off_no", 0);
        final int res_people = getIntent().getIntExtra("res_people", 0);
        final String off_name = getIntent().getStringExtra("off_name");
        final String res_memo = getIntent().getStringExtra("res_memo");
        final String mem_agentName = getIntent().getStringExtra("mem_agentName");
        final String mem_agentTel = getIntent().getStringExtra("mem_agentTel");
        long startdatetime = getIntent().getLongExtra("res_startdatetime", 0);
        long enddatetime = getIntent().getLongExtra("res_enddatetime", 0);
        ArrayList<String> imgs = getIntent().getStringArrayListExtra("off_imgs");

        final Timestamp res_startdatetime = new Timestamp(startdatetime);
        final Timestamp res_enddatetime = new Timestamp(enddatetime);

        String start = "";
        String end = "";

        try {
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = formatter.format(res_startdatetime);
            end = formatter.format(res_enddatetime);

        } catch (Exception ex) {
            start = "";
            end = "";
        }

        office_detail_img_slider = findViewById(R.id.slider);

        for (int i = 0; i < imgs.size(); i++) {
            final TextSliderView sliderView = new TextSliderView(context);
            final StorageReference ref = FirebaseStorage.getInstance().getReference("images/" + imgs.get(i));
            ref.getDownloadUrl().addOnCompleteListener(
                    new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                sliderView.image(String.valueOf(task.getResult())).setProgressBarVisible(true);
                                office_detail_img_slider.addSlider(sliderView);
                            } else {
                                // URL을 가져오지 못하면 토스트 메세지
                                Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );

        }

        office_detail_img_slider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        office_detail_img_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        office_detail_img_slider.setDuration(4000);
        office_detail_img_slider.stopCyclingWhenTouch(false);

        Button btn_reserv = findViewById(R.id.btn_reserv);
        Button btn_cancel = findViewById(R.id.btn_cancel);

        TextView office_name = findViewById(R.id.office_name);
        TextView office_price = findViewById(R.id.office_price);
        TextView office_start_time = findViewById(R.id.office_start_time);
        TextView office_end_time = findViewById(R.id.office_end_time);
        TextView office_memo = findViewById(R.id.office_memo);
        TextView office_people = findViewById(R.id.office_people);

        office_name.setText(off_name);
        office_price.setText(String.valueOf(room_price) + "원");
        office_start_time.setText(start);
        office_end_time.setText(end);
        office_memo.setText(res_memo);
        office_people.setText(String.valueOf(res_people) + "명");

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_reserv:
                        if (Init.login_check == 0) {
                            Toast.makeText(context, "로그인 하세요", Toast.LENGTH_LONG).show();
                        } else {

                            Call<Integer> insesrtRes = RetrofitSingleton.getInstance().getRetrofitService().makeReservation(room_price, res_people, off_name, off_no, res_startdatetime, res_enddatetime, res_memo, Init.sp.getString("email", "null"), mem_agentName,mem_agentTel);
                            insesrtRes.enqueue(
                                    new Callback<Integer>() {
                                        @Override
                                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                                            Log.d("MainActivity", ""+response.isSuccessful());
                                            if (response.isSuccessful()) {
                                                Log.d("MainActivity", ""+response.body());
                                                Intent intent = new Intent(activity, KakaoPayActivity.class);
                                                intent.putExtra("room_price", room_price);
                                                intent.putExtra("off_name", off_name);
                                                intent.putExtra("off_no", off_no);
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Integer> call, Throwable t) {

                                        }
                                    }
                            );
                        }
                        break;
                    case R.id.btn_cancel:
                        onBackPressed();
                        break;
                    default:
                        break;
                }
            }
        };

        btn_reserv.setOnClickListener(onClickListener);
        btn_cancel.setOnClickListener(onClickListener);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
