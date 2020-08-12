package co.kr.reo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Init {
    private Activity activity;
    private AppBarConfiguration mAppBarConfiguration;
    private Context context;
    static int login_check = 0;
    static String login_type;

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    private AutoCompleteTextView autoCompleteTextView;
    private DrawerLayout drawer;
    NavigationView navigationView;

    public Init(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        drawer = activity.findViewById(R.id.drawer_layout);
        navigationView = activity.findViewById(R.id.nav_view);
    }

    public Toolbar getToolbar() {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        return toolbar;
    }

    public AppBarConfiguration getAppBarConfiguration() {
        return mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
    }

    public NavigationView getNavigationView() {

        return navigationView;
    }

    public NavController getNavController() {
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        return navController;
    }

    public void firstinit() {

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
                        String email = sp.getString("email", null);
                        Intent intent = new Intent(activity, MyPageActivity.class);
                        intent.putExtra("email", email);
                        activity.startActivity(intent);
                    } else if (id == R.id.book_List) {
                        Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity, MyReservListActivity.class);
                        activity.startActivity(i);
                    } else if (id == R.id.QnA) {
                        Toast.makeText(context, title + ": QnA 정보를 확입합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, QnaActivity.class);
                        activity.startActivity(intent);
                    } else if (id == R.id.logout) {
                        new AlertDialog.Builder(context)
                                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        switch (sp.getString("type", "spring")) {
                                            case "spring":
                                                break;
                                            case "kakao":
                                                UserManagement.getInstance()
                                                        .requestLogout(new LogoutResponseCallback() {
                                                            @Override
                                                            public void onCompleteLogout() {
                                                                Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                break;
                                            case "naver":
                                                forceLogout();
                                                break;
                                            case "google":
                                                LoginActivity.mAuth.signOut();
                                                break;
                                            default:
                                                break;
                                        }

                                        login_check = 0;
                                        editor.clear();
                                        editor.commit();
                                        activity.finish();
                                        Intent i = new Intent(activity, IndexActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        activity.startActivity(i);
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
                        activity.startActivity(intent);
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

        autoCompleteTextView = activity.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setHorizontallyScrolling(true);

        final ImageView search_map_button = activity.findViewById(R.id.search_map_button);
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

    }

    public void init() {
        sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /////네이버 로그아웃/////////////
    public void forceLogout() {
        // 스레드로 돌려야 한다. 안 그러면 로그아웃 처리가 안되고 false를 반환한다.
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccessDeleteToken = Naver.mOAuthLoginInstance.logoutAndDeleteToken(context);
                if (!isSuccessDeleteToken) {
                    Log.d("naverlogout", "errorCode:" + Naver.mOAuthLoginInstance.getLastErrorCode(context));
                    Log.d("naverlogout", "errorDesc:" + Naver.mOAuthLoginInstance.getLastErrorDesc(context));
                } else {
                    Log.d("naverlogout", "로그아웃 성공");
                }
            }
        }).start();
    }
    ////////////////////////////////

    public void getBoardList(){
        Call<ArrayList<OfficeDTO>> call = RetrofitSingleton.getInstance().getRetrofitService().getBoardList();
        call.enqueue(new Callback<ArrayList<OfficeDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<OfficeDTO>> call, Response<ArrayList<OfficeDTO>> response) {
                if(response.isSuccessful()){
                    ArrayList<OfficeDTO> office = response.body();
                    OfficeDTO dto = new OfficeDTO();
                    Log.d("list",""+ dto.getOff_name());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<OfficeDTO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }




}
