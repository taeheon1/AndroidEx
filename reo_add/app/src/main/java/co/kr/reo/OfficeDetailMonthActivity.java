package co.kr.reo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfficeDetailMonthActivity extends AppCompatActivity implements OnMapReadyCallback, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;

    private AutoCompleteTextView autoCompleteTextView;
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
    private CalendarView calendarView;

    int this_year;
    int this_month;
    int this_day;

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

    TextView person;
    LinearLayout show_rent_per_person;
    TextView tv;
    EditText et;
    int reserv_check = 0;
    ArrayList<OfficeImgsDTO> imgs;
    Button b;
    int price_check = 1;
    ArrayList<String> reserList = new ArrayList<>();
    ArrayList<String> startdate = new ArrayList<>();
    ArrayList<String> enddate = new ArrayList<>();

    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private Date todayDate;
    private View todayView;
    ArrayList<Date> disabledatestartarray;
    ArrayList<Date> disabledatearray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_detail_month);
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
                    Geocoder geocoder = new Geocoder(OfficeDetailMonthActivity.this);
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

        b = findViewById(R.id.select_month);
        show_rent_per_person = findViewById(R.id.show_rent_per_person);
        tv = findViewById(R.id.show_rent);
        et = findViewById(R.id.memo);

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

        caldroidFragment = new CaldroidFragment();
//                                        if (savedInstanceState != null) {
//                                            caldroidFragment.restoreStatesFromKey(savedInstanceState,
//                                                    "CALDROID_SAVED_STATE");
//                                        } else {
        Bundle args = new Bundle();
        Calendar cal2 = Calendar.getInstance();
        caldroidFragment.setMinDate(cal2.getTime());
        this_year = cal2.get(Calendar.YEAR);
        this_month = cal2.get(Calendar.MONTH) + 1;
        Log.d("todayday", "1 - " + this_year + " : " + this_month);
        args.putInt(CaldroidFragment.MONTH, this_month);
        args.putInt(CaldroidFragment.YEAR, this_year);
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        args.putBoolean(CaldroidFragment.ENABLE_CLICK_ON_DISABLED_DATES, false);
        caldroidFragment.setArguments(args);
//                                        }

        String date = "" + this_year + "-" + this_month + "-" + this_day;

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
                                                startdate.add(reserList.get(i).split(" ")[0]);
                                            } else {
                                                enddate.add(reserList.get(i).split(" ")[0]);
                                            }
                                        }
                                        disabledatestartarray = new ArrayList<>();
                                        disabledatearray = new ArrayList<>();

                                        for (int i = 0; i < startdate.size(); i++) {
                                            try {
                                                Date sd = listFormat.parse(startdate.get(i));
                                                Date ed = listFormat.parse(enddate.get(i));
                                                disabledatestartarray.add(sd);
                                                disabledatearray.add(ed);

                                                Log.d("yyyy-MM-dd", "" + (sd));
                                                Log.d("yyyy-MM-dd", "" + (ed));

                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                                        t.replace(R.id.calendar1, caldroidFragment);
                                        t.commit();

                                        // Setup listener
                                        final CaldroidListener listener = new CaldroidListener() {
                                            @Override
                                            public void onSelectDate(Date date, View view) {

                                                if (todayDate == null) {
                                                    todayDate = date;
                                                    todayView = view;
                                                    this_month = date.getMonth() + 1;
                                                    this_day = date.getDate();
                                                    Log.d("todayday1", "" + this_month + " : " + this_day);
                                                    ColorDrawable reo = new ColorDrawable(Color.rgb(255, 127, 0));
                                                    view.setBackgroundColor(Color.rgb(255, 127, 0));
                                                    caldroidFragment.setBackgroundDrawableForDate(reo, date);
                                                    caldroidFragment.setTextColorForDate(R.color.caldroid_black, date);
                                                } else {
                                                    if (todayDate.equals(date)) {
                                                        view.setBackgroundColor(Color.WHITE);
                                                        caldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(Color.WHITE), date);
                                                        this_month = 0;
                                                        this_day = 0;
                                                        todayView = null;
                                                        todayDate = null;
                                                    } else {

                                                        this_month = date.getMonth() + 1;
                                                        this_day = date.getDate();
                                                        Log.d("todayday2", "" + this_month + " : " + this_day);
                                                        ColorDrawable reo = new ColorDrawable(Color.rgb(255, 127, 0));
                                                        view.setBackgroundColor(Color.rgb(255, 127, 0));
                                                        todayView.setBackgroundColor(Color.WHITE);
                                                        caldroidFragment.setBackgroundDrawableForDate(reo, date);
                                                        caldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(Color.WHITE), todayDate);
                                                        caldroidFragment.setTextColorForDate(R.color.caldroid_black, date);
                                                        todayDate = date;
                                                        todayView = view;
                                                    }
                                                }
                                                b.setText("1개월");
                                                onstart(startdate, enddate);
                                            }

                                            @Override
                                            public void onChangeMonth(int month, int year) {
                                                String text = "month: " + month + " year: " + year;
                                                Toast.makeText(getApplicationContext(), text,
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onLongClickDate(Date date, View view) {
                                                Toast.makeText(getApplicationContext(),
                                                        "Long click " + listFormat.format(date),
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCaldroidViewCreated() {
                                                if (caldroidFragment.getLeftArrowButton() != null) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Caldroid view is created", Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }

                                        };

                                        // Setup Caldroid
                                        caldroidFragment.setCaldroidListener(listener);

                                        ArrayList<String> disableddate = new ArrayList<>();
                                        for (int i = 0; i < startdate.size(); i++) {
                                            long calDate = disabledatearray.get(i).getTime() - disabledatestartarray.get(i).getTime();
                                            long calDateDays = calDate / (24 * 60 * 60 * 1000);
                                            int days = (int) Math.abs(calDateDays);
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(disabledatestartarray.get(i));
                                            disableddate.add(listFormat.format(disabledatestartarray.get(i).getTime()));
                                            for (int j = 0; j < days; j++) {
                                                cal.add(Calendar.DATE, 1);
                                                Log.d("yyyy-MM-dd", listFormat.format(cal.getTime()));
                                                disableddate.add(listFormat.format(cal.getTime()));
                                            }
                                            caldroidFragment.setDisableDatesFromString(disableddate);

                                        }
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
    }

    public void onPopupButton(final View button) {
        PopupMenu p = new PopupMenu(this, button);
        p.getMenuInflater().inflate(R.menu.select_month_menu, p.getMenu());
        p.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.one:
                                price_check = 1;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.two:
                                price_check = 2;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.three:
                                price_check = 3;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.four:
                                price_check = 4;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.five:
                                price_check = 5;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.six:
                                price_check = 6;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.seven:
                                price_check = 7;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.eight:
                                price_check = 8;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.nine:
                                price_check = 9;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.ten:
                                price_check = 10;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.eleven:
                                price_check = 11;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            case R.id.twelve:
                                price_check = 12;
                                b.setText(menuItem.getTitle().toString());
                                break;
                            default:
                                break;
                        }

                        tv.setText("총 금액: " + (total_rent = Integer.valueOf(rent_price) * price_check) + "원");
                        setDateTime(this_year, this_month, this_day);
                        return false;
                    }
                }
        );
        p.show();

    }

    public void setDateTime(int year, int month, int day) {
        String monthstr = "";
        String monthstr2 = "";
        String daystr = "";
        int year2 = year;
        int month2 = month + price_check;
        if (month < 10) {
            monthstr = "0" + (month);

        } else {
            monthstr = "" + (month);

        }
        if (month2 < 10) {
            monthstr2 = "0" + month2;
        } else if (month2 < 13) {
            monthstr2 = "" + month2;
        } else {
            year2 += 1;
            if (month2 - 12 < 10) {
                monthstr2 = "0" + (month2 - 12);
            } else {
                monthstr2 = "" + (month2 - 12);
            }
        }

        if (day < 10) {
            daystr = "0" + day;
        } else {
            daystr = "" + day;
        }

        String date1 = "" + year + "-" + monthstr + "-" + daystr + " " + "00:00:00";
        String date2 = "" + year2 + "-" + monthstr2 + "-" + daystr + " " + "00:00:00";

        try {
            tempDate = afterFormat.parse(date1);
            res_startdatetime = tempDate.getTime();

            tempDate = afterFormat.parse(date2);
            res_enddatetime = tempDate.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i < disabledatestartarray.size(); i++){
            Date check1 = disabledatestartarray.get(i);
            Date check2 = disabledatearray.get(i);
            Log.d("todayday",""+check1);
            if(res_enddatetime >= check1.getTime() && res_enddatetime <= check2.getTime()){
                reserv_check = 1;
                Toast.makeText(context, "다른 예약 날과 겹칩니다.", Toast.LENGTH_LONG).show();
//                break;
            } else if(res_startdatetime <= check1.getTime() && res_enddatetime >= check1.getTime()){
                reserv_check = 1;
                Toast.makeText(context, "다른 예약 날과 겹칩니다.", Toast.LENGTH_LONG).show();
//                break;
            } else {
                reserv_check = 0;
//                break;
            }

        }


    }

    public void onstart(ArrayList<String> startdate, ArrayList<String> enddate) {

        total_rent = Integer.valueOf(rent_price);
        tv.setText("총 금액: " + total_rent + "원");
        findViewById(R.id.select_by_month).setVisibility(View.VISIBLE);
        setDateTime(this_year, this_month, this_day);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

        if (dialogCaldroidFragment != null) {
            dialogCaldroidFragment.saveStatesToKey(outState,
                    "DIALOG_CALDROID_SAVED_STATE");
        }
    }

}
