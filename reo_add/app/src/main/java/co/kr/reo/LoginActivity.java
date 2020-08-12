package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kakao.util.maps.helper.Utility.getPackageInfo;

public class LoginActivity extends AppCompatActivity {
    //////////////처음부분//////////////////
    private AppBarConfiguration mAppBarConfiguration;
    private Context context = this;
    private Activity activity = this;
    ////////////////////////////////////////

    private String email;
    private String name;

    TextView loginbtn;

    //카카오//
    private Button btn_kakao_custom;
    private LoginButton btn_kakao_login;
    private Button btn_custom_login_out;
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;
    ////////

    //구글///
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton googleButton;
    ////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //////////////처음부분//////////////////
        final Init first = new Init(activity, context);
        setSupportActionBar(first.getToolbar());
        mAppBarConfiguration = first.getAppBarConfiguration();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, first.getNavController(), mAppBarConfiguration);
        NavigationUI.setupWithNavController(first.getNavigationView(), first.getNavController());
        first.firstinit();
        ////////////////////////////////////////

        //////////////spring 연동 로그인///////////////
        Spring spring = new Spring(activity, first, context);
        spring.login();
        /////////////////////////

        ///////////////네이버 로그인 셋팅//////////
        Naver naver = new Naver(OAuthLogin.getInstance(), context, activity);
        naver.login();
        ///////////////////////////////////

        ////////카카오 로그인/////////////
        kakaodata();
        ///////////////////////////////

        ///////////구글 로그인////////////
        googleButton = findViewById(R.id.googleButton);
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

        //////카카오 키해시 값 구하기//////
        PackageInfo packageInfo1 = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        for (Signature signature : packageInfo1.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("d", "to get MessageDigest. signature= " + Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            } catch (NoSuchAlgorithmException e) {
                Log.e("d", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        ////////////////////////////////////////////

    }

    //////////////처음부분//////////////////
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    //////카카오 로그인 정보 가져오기////////
    private void kakaodata() {

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

        if (session.isOpenable()) {
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
            if (exception != null) {
                Log.e("SessionCallback", "exception : " + exception);
            }
        }
    }

    /**
     * 사용자에 대한 정보를 가져온다
     **/
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
                Log.e("SessionCallback", "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
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
                Init.editor.putString("email", email);
                Init.editor.putString("name", name);
                Init.editor.putString("type", "kakao");
                Init.editor.commit();
                Intent intent = new Intent(activity, IndexActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });
    }

    /////////////////////////////////////
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
            Init.editor.putString("email", email);
            Init.editor.putString("name", name);
            Init.editor.putString("type", "google");
            Init.editor.commit();
            Intent intent = new Intent(activity, IndexActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    ///////////////////////////////////////////

}
