package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private static Context context ;
    private Activity activity;
    int check = 0;
    String email;
    String name;

    private Retrofit retrofit;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    TextView loginbtn;

    /////네이버/////
    private static String OAUTH_CLIENT_ID = "_UZIGG0EbNZSl1gKOjs8";
    private static String OAUTH_CLIENT_SECRET = "OWpTmRTC1Q";
    private static String OAUTH_CLIENT_NAME = "NAVER";
    public static OAuthLoginButton mOAuthLoginButton;
    public static OAuthLogin mOAuthLoginInstance;
    //////////

    //카카오//
    private Button btn_kakao_custom;
    private LoginButton btn_kakao_login;
    private Button btn_custom_login_out;
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    ////////

    //구글///
    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton googleButton;
    ////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;
        context = this;

        init(); //retrofit 초기화

        ///////navigation 셋팅///////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.login_drawer);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();
                if (id == R.id.account) {
                    Toast.makeText(context, "로그인 하세요.", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.book_List) {
                    Toast.makeText(context, "로그인 하세요.", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.QnA) {
                    Toast.makeText(context, "로그인 하세요.", Toast.LENGTH_SHORT).show();
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        /////////////////////////////////////////

        //////////////spring 연동 로그인///////////////
        final RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        loginbtn = (TextView) findViewById(R.id.login);
        loginbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String mem_email = ((EditText)findViewById(R.id.username_inpubox)).getText().toString();
                        String mem_pw = ((EditText)findViewById(R.id.password_inpubox)).getText().toString();

                        Call<ArrayList<MemberDTO>> call = retrofitService.login(mem_email,mem_pw);

                        call.enqueue(new Callback<ArrayList<MemberDTO>>() {
                            @Override
                            public void onResponse(Call<ArrayList<MemberDTO>> call, Response<ArrayList<MemberDTO>> response) {
                                if(response.isSuccessful()){
                                    ArrayList<MemberDTO> user = response.body();
                                    Log.d("MainActivity","success: " + user.get(0).getMem_email());
                                    email = user.get(0).getMem_email();
                                    name = user.get(0).getMem_name();

                                    if(mem_email.equals(user.get(0).getMem_email())){

                                        View header = navigationView.getHeaderView(0);
                                        TextView user_login = (TextView) header.findViewById(R.id.user_login);
                                        TextView user_name = (TextView) header.findViewById(R.id.user_name);

                                        user_login.setText(user.get(0).getMem_email());
                                        user_name.setText(user.get(0).getMem_name());
                                        user_name.setVisibility(View.VISIBLE);

                                        editor.putString("email", email);
                                        editor.putString("name", name);
                                        editor.commit();
                                        Intent intent = new Intent(activity, BoardListActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                }
                            }
                            @Override
                            public void onFailure(Call<ArrayList<MemberDTO>> call, Throwable t) {
                                Log.d("MainActivity","error" + t.getMessage());
                            }
                        });

                    }
                }
        );
        /////////////////////////

        ////////////spring 연동 로그아웃///////////
        TextView logout = findViewById(R.id.button_naverlogout);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forceLogout();
                    }
                }
        );
        ////////////////////////

        ///////////////네이버 로그인 셋팅//////////
        //1. 초기화
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(context,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);

        //2.로그인 버튼 셋팅
        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.button_naverlogin);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        /////////////////

        ////////카카오 로그인/////////////
        kakaodata();
        ///////////////////////////////

        ///////////구글 로그인////////////
        googleButton = findViewById(R.id.googleButton);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        /////////////////////////////

    }

    //////카카오 로그인 정보 가져오기////////
    private void kakaodata(){

        btn_kakao_custom = (Button) findViewById(R.id.btn_kakao_custom);
        btn_kakao_custom.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_kakao_login.performClick();
                    }
                });
        btn_kakao_login = (LoginButton) findViewById(R.id.btn_kakao_login);

        btn_custom_login_out = (Button) findViewById(R.id.btn_custom_login_out);
        btn_custom_login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        if(session.isOpenable()){
            session.checkAndImplicitOpen();
        }
    }
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e("SessionCallback", "카카오 로그인 성공 ");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.e("SessionCallback", "exception : " + exception);
            }
        }
    }
    /** 사용자에 대한 정보를 가져온다 **/
    private void requestMe() {
        List<String> keys = new ArrayList<>();

        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e("SessionCallback", "requestMe onFailure message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailureForUiThread(ErrorResult errorResult) {
                super.onFailureForUiThread(errorResult);
                Log.e("SessionCallback" , "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("SessionCallback", "requestMe onSessionClosed message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.e("SessionCallback", "requestMe onSuccess message : " + result.getKakaoAccount().getEmail() + " " + result.getId() + " " + result.getNickname());
                email = result.getKakaoAccount().getEmail();
                name = result.getNickname();
                editor.putString("email",email);
                editor.putString("name",name);
                editor.commit();
                Intent intent = new Intent(activity, BoardListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }
    /////////////////////////////////////


    /////////////Retrofit 초기화/////////////
    public void init(){
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.107:9090/reo/android/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sp = activity.getSharedPreferences("login_sp",Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    /////////////////////


    ////////네이버 로그인 진행//////////////
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            //로그인 인증 성공
            if(success){
                // 사용자 정보 가져오기
                String accessToken = mOAuthLoginInstance.getAccessToken(context);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(context);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(context);
                String tokenType = mOAuthLoginInstance.getTokenType(context);

                new RequestApiTask(accessToken).execute();
            } else {
                // 로그인 인증 실패
                String errorCode = mOAuthLoginInstance.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(context);
                Toast.makeText(context,"errorCode:" + errorCode + ", errorDesc:" + errorDesc,Toast.LENGTH_SHORT).show();
            }
        }
    };

    private class RequestApiTask extends AsyncTask {
        private String token;

        RequestApiTask(String token) {
            this.token = token;
        }

        @Override
        protected void onPostExecute(Object object) {
            StringBuffer result = (StringBuffer) object;
            super.onPostExecute(result);
            // 로그인 처리가 완료되면 수행할 로직 작성
            processAuthResult(result);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String header = "Bearer " + token;
            try {
                final String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode == 200 ? con.getInputStream() : con.getErrorStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                return response;

            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    private void processAuthResult(StringBuffer response) {
        try {
            // response는 json encoded된 상태이기 때문에 json 형식으로 decode 해줘야 한다.
            JSONObject object = new JSONObject(response.toString());
            JSONObject innerJson = new JSONObject(object.get("response").toString());

            // 만약 이메일이 필요한데 사용자가 이메일 제공을 거부하면
            // JSON 데이터에는 email이라는 키가 없고, 이걸로 제공 여부를 판단한다.
            if(!innerJson.has("email")) {
                Log.d("naver_email", "email 제공 거부");
            } else {
                String email = innerJson.getString("email");
                String name = innerJson.getString("name");

                editor.putString("email", email);
                editor.putString("name", name);
                editor.commit();
                Intent intent = new Intent(activity, BoardListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////

    /////네이버 로그아웃/////////////
    public void forceLogout() {
        // 스레드로 돌려야 한다. 안 그러면 로그아웃 처리가 안되고 false를 반환한다.
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(Login.this);
                if(!isSuccessDeleteToken){
                    Log.d("naverlogout", "errorCode:" + mOAuthLoginInstance.getLastErrorCode(context));
                    Log.d("naverlogout", "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(context));
                } else{
                    Log.d("naverlogout", "로그아웃 성공");
                }
            }
        }).start();
    }
    ///////////////////////////////

    /////////navigation 부분/////////
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
    //////////////////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    /////////////////구글 로그인 처리 메소드//////////////////////
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            String email = user.getEmail();
            String name = user.getDisplayName();
            editor.putString("email", email);
            editor.putString("name", name);
            editor.commit();
            Intent intent = new Intent(activity, BoardListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    ///////////////////////////////////////////
}
