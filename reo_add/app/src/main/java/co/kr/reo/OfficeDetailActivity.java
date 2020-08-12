package co.kr.reo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfficeDetailActivity extends AppCompatActivity implements OnMapReadyCallback, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;
    ////////////////////////////////////////

    private TextView office_detail_name;
    private TextView office_detail_rent;
    private TextView office_detail_addr;
    private SliderLayout office_detail_img_slider;

    private GoogleMap mMap;
    private Marker currentMarker = null;

    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "googlemap_example";
    private LocationRequest locationRequest;
    private ArrayList<OfficeDTO> office = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<String>(Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"));
    private CalendarView calendarView;
    private RecyclerView time_item_list;

    int this_year;
    int this_month;
    int this_day;
    private TimeButtonAdapter timeButtonAdapter;

    private String rent_price;

    private int room_price;
    private int res_people = 1;
    private String off_name;
    private int off_no;
    private long res_startdatetime;
    private long res_enddatetime;
    private Timestamp res_datetime;

    private String res_memo;

    private int total_rent;
    private SimpleDateFormat afterFormat;
    private SimpleDateFormat listFormat;
    private Date tempDate = null;
    private ArrayList<TimeData> timeData;

    TextView person;
    LinearLayout under_calendarView1;
    LinearLayout show_rent_per_person;
    TextView tv;
    EditText et;
    int reserv_check = 0;
    ArrayList<OfficeImgsDTO> imgs;
    int price_check;
    ArrayList<String> reserList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_detail);
        init();
        //////////////처음부분//////////////////
        Init first = new Init(activity, context);
        setSupportActionBar(first.getToolbar());
        mAppBarConfiguration = first.getAppBarConfiguration();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, first.getNavController(), mAppBarConfiguration);
        NavigationUI.setupWithNavController(first.getNavigationView(), first.getNavController());
        first.firstinit();
        ////////////////////////////////////////

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.office_detail_map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        office_detail_name = findViewById(R.id.office_detail_name);
        office_detail_rent = findViewById(R.id.office_detail_rent);
        office_detail_addr = findViewById(R.id.office_detail_addr);
        office_detail_img_slider = findViewById(R.id.slider);

        off_no = getIntent().getIntExtra("off_no", 0);

        person = findViewById(R.id.person);

        Call<ArrayList<OfficeDTO>> call = RetrofitSingleton.getInstance().getRetrofitService().getOffice(off_no);
        call.enqueue(new Callback<ArrayList<OfficeDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<OfficeDTO>> call, Response<ArrayList<OfficeDTO>> response) {
                Log.d("MainActivity", "response_success: " + response.isSuccessful());
                if (response.isSuccessful()) {
                    office = response.body();
                    Log.d("MainActivity", "success: " + office.get(0).getOff_name());
                    off_name = office.get(0).getOff_name();

                    office_detail_name.setText(off_name);
                    office_detail_addr.setText(office.get(0).getOff_stdAddr());

                    String[] type = office.get(0).getOff_type().split(",");
                    for (int i = 0; i < type.length; i++) {
                        textview(type[i]);
                    }

                    switch (office.get(0).getOff_type().split(",")[0]) {
                        case "공유오피스":
                            rent_price = String.valueOf(office.get(0).getOff_rent());
                            break;
                        case "회의실":
                            rent_price = String.valueOf(office.get(0).getOff_rentConfer());
                            break;
                        case "세미나실":
                            rent_price = String.valueOf(office.get(0).getOff_rentSemi());
                            break;
                        case "다목적홀":
                            rent_price = String.valueOf(office.get(0).getOff_rentHall());
                            break;
                        default:
                            rent_price = String.valueOf(office.get(0).getOff_rentStudy());
                    }
                    office_detail_rent.setText(rent_price + "원/ " + office.get(0).getOff_unit());
                    Geocoder geocoder = new Geocoder(OfficeDetailActivity.this);
                    double x = 37.56;
                    double y = 126.97;
                    try {
                        List<Address> addrList = geocoder.getFromLocationName(office.get(0).getOff_stdAddr(), 1);
                        x = addrList.get(0).getLatitude();
                        y = addrList.get(0).getLongitude();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LatLng DEFAULT_LOCATION = new LatLng(x, y);
                    final String markerTitle = office.get(0).getOff_name();
                    if (currentMarker != null) currentMarker.remove();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(DEFAULT_LOCATION);
                    markerOptions.title(markerTitle);
                    markerOptions.draggable(true);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    currentMarker = mMap.addMarker(markerOptions);

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
                    mMap.moveCamera(cameraUpdate);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<OfficeDTO>> call, Throwable t) {
                Log.d("MainActivity", "error" + t.getMessage());
            }
        });


        Call<ArrayList<OfficeImgsDTO>> callimgs = RetrofitSingleton.getInstance().getRetrofitService().getOfficeImgs(off_no);
        callimgs.enqueue(new Callback<ArrayList<OfficeImgsDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<OfficeImgsDTO>> call, Response<ArrayList<OfficeImgsDTO>> response) {
                if (response.isSuccessful()) {
                    imgs = response.body();
                    for (int i = 0; i < imgs.size(); i++) {
                        final TextSliderView sliderView = new TextSliderView(context);
                        final StorageReference ref = FirebaseStorage.getInstance().getReference("images/" + imgs.get(i).getOffimg_name());
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
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OfficeImgsDTO>> call, Throwable t) {

            }
        });

        under_calendarView1 = findViewById(R.id.under_calendarView1);
        show_rent_per_person = findViewById(R.id.show_rent_per_person);
        tv = findViewById(R.id.show_rent);
        et = findViewById(R.id.memo);

        calendarView = findViewById(R.id.calendarView1);
        calendarView.setMinDate(System.currentTimeMillis());

        calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        price_check = 1;
                        this_year = year;
                        this_month = month;
                        this_day = day;
                        String date = "" + this_year + "-" + (this_month + 1) + "-" + this_day;
                        final ArrayList<Integer> startdate = new ArrayList<>();
                        final ArrayList<Integer> enddate = new ArrayList<>();
                        try {
                            tempDate = listFormat.parse(date);
                            long timestamp = tempDate.getTime();
                            res_datetime = new Timestamp(timestamp);
                            final Call<ArrayList<String>> resList = RetrofitSingleton.getInstance().getRetrofitService().resList(off_no, res_datetime);
                            resList.enqueue(new Callback<ArrayList<String>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                                                    if (response.isSuccessful()) {
                                                        reserList = response.body();
                                                        for (int i = 0; i < reserList.size(); i++) {
                                                            if (i % 2 == 0) {
                                                                startdate.add(Integer.valueOf(reserList.get(i).split(" ")[1].split(":")[0]));
                                                            } else {
                                                                enddate.add(Integer.valueOf(reserList.get(i).split(" ")[1].split(":")[0])-1);
                                                            }
                                                            Log.d("reserList", "success11: " +(startdate.get(0)));
                                                        }
                                                        onstart(startdate, enddate);

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                                                    Log.d("reserList", "fail: " + t.getCause() + " " + t.getLocalizedMessage() + " " + t.toString());
                                                }
                                            }
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv.setText("총 금액: " + rent_price + "원");
                        res_people = 1;
                        person.setText("" + res_people + "/" + office.get(0).getOff_maxNum() + "명");

                    }
                }
        );


        ImageButton.OnClickListener onClickListener = new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.plus:
                        if (res_people < office.get(0).getOff_maxNum()) {
                            res_people += 1;
                        }
                        break;

                    case R.id.minus:
                        if (res_people > 1) {
                            res_people -= 1;
                        }
                        break;
                }
                person.setText("" + res_people + "/" + office.get(0).getOff_maxNum() + "명");
            }
        };

        ImageButton img1 = findViewById(R.id.plus);
        ImageButton img2 = findViewById(R.id.minus);
        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);

    }


    public void onstart(ArrayList<Integer> startdate, ArrayList<Integer> enddate) {

        timeButtonAdapter = new TimeButtonAdapter(timeData, LayoutInflater.from(context), activity, this_month, this_day, startdate, enddate);
        timeButtonAdapter.setOnItemClickListener(
                new TimeButtonAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int start, int end, int checking, String startDate, String endDate, int sub) {
                        reserv_check = sub;
                        if (sub == 1) {
                            Toast.makeText(context, "다시 선택하세요", Toast.LENGTH_LONG).show();
                        } else {
                            if (checking == 2) {
                                price_check = end - start + 1;
                                total_rent = price_check * Integer.valueOf(rent_price);
                                endDate = String.valueOf(Integer.valueOf(endDate.split(":")[0]) + 1) + ":00:00";
                                Log.d("checkdate", endDate);
                            } else {
                                price_check = 1;
                                total_rent = Integer.valueOf(rent_price);
                                endDate = String.valueOf(Integer.valueOf(startDate.split(":")[0]) + 1) + ":00:00";
                            }
                            tv.setText("총 금액: " + total_rent + "원");

                            String monthstr = "";
                            String daystr = "";

                            if (this_month + 1 < 10) {
                                monthstr = "0" + (this_month + 1);
                            } else {
                                monthstr = "" + (this_month + 1);
                            }
                            if (this_day < 10) {
                                daystr = "0" + this_day;
                            } else {
                                daystr = "" + this_day;
                            }

                            String date1 = "" + this_year + "-" + monthstr + "-" + daystr + " " + startDate + ":00";
                            String date2 = "" + this_year + "-" + monthstr + "-" + daystr + " " + endDate;

                            try {
                                tempDate = afterFormat.parse(date1);
                                res_startdatetime = tempDate.getTime();
//                                        long timestamp = tempDate.getTime();
//                                        res_startdatetime = new Timestamp(timestamp);

                                tempDate = afterFormat.parse(date2);
                                res_enddatetime = tempDate.getTime();
//                                        timestamp = tempDate.getTime();
//                                        res_enddatetime = new Timestamp(timestamp);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }
        );
        time_item_list = findViewById(R.id.rcyTime);
        time_item_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        time_item_list.setAdapter(timeButtonAdapter);
        under_calendarView1.setVisibility(View.VISIBLE);
        show_rent_per_person.setVisibility(View.VISIBLE);

        Button res_btn = findViewById(R.id.reservation);
        res_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (reserv_check != 0) {
                            Toast.makeText(context, "예약할 수 없습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            room_price = total_rent;
                            res_memo = et.getText().toString();

                            Intent intent = new Intent(activity, PayActivity.class);
                            intent.putExtra("room_price", room_price);
                            intent.putExtra("res_people", res_people);
                            intent.putExtra("off_name", off_name);
                            intent.putExtra("off_no", off_no);
                            intent.putExtra("res_startdatetime", res_startdatetime);
                            intent.putExtra("res_enddatetime", res_enddatetime);
                            intent.putExtra("res_memo", res_memo);
                            intent.putExtra("mem_agentName", office.get(0).getMem_agentName());
                            intent.putExtra("mem_agentTel", office.get(0).getMem_agentTel());
                            ArrayList<String> imglist = new ArrayList<>();
                            for (int i = 0; i < imgs.size(); i++) {
                                imglist.add(imgs.get(i).getOffimg_name());
                            }
                            intent.putExtra("off_imgs", imglist);
                            startActivity(intent);

                        }
                    }
                }
        );
    }

    public void textview(String type) {
        TextView view = new TextView(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(10 * dm.density);
        FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(size, size);
        FlowLayout container = findViewById(R.id.office_detail_types);
        view.setLayoutParams(lp);
        view.setText("#" + type);
        view.setBackground(ContextCompat.getDrawable(this, R.drawable.border));
        size = Math.round(5 * dm.density);
        view.setPadding(size, 0, size, 0);
        container.addView(view);
    }

    //////////////처음부분//////////////////
    public void init() {


        afterFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        listFormat = new SimpleDateFormat("yyyy-MM-dd");
        timeData = new ArrayList<>();
        timeData.add(new TimeData(timeList.get(0), false, Code.ViewType.tim1));
        timeData.add(new TimeData(timeList.get(1), false, Code.ViewType.tim2));
        timeData.add(new TimeData(timeList.get(2), false, Code.ViewType.tim3));
        timeData.add(new TimeData(timeList.get(3), false, Code.ViewType.tim4));
        timeData.add(new TimeData(timeList.get(4), false, Code.ViewType.tim5));
        timeData.add(new TimeData(timeList.get(5), false, Code.ViewType.tim6));
        timeData.add(new TimeData(timeList.get(6), false, Code.ViewType.tim7));
        timeData.add(new TimeData(timeList.get(7), false, Code.ViewType.tim8));
        timeData.add(new TimeData(timeList.get(8), false, Code.ViewType.tim9));
        timeData.add(new TimeData(timeList.get(9), false, Code.ViewType.tim10));
        timeData.add(new TimeData(timeList.get(10), false, Code.ViewType.tim11));
        timeData.add(new TimeData(timeList.get(11), false, Code.ViewType.tim12));
        timeData.add(new TimeData(timeList.get(12), false, Code.ViewType.tim13));
        timeData.add(new TimeData(timeList.get(13), false, Code.ViewType.tim14));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    ////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TAG, "onMapClick :");
            }
        });

    }

    @Override
    protected void onStop() {
        office_detail_img_slider.startAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().getString("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
