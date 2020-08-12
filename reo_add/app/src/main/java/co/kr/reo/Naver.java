package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Naver {
    /////네이버/////
    private static String OAUTH_CLIENT_ID = "_UZIGG0EbNZSl1gKOjs8";
    private static String OAUTH_CLIENT_SECRET = "OWpTmRTC1Q";
    private static String OAUTH_CLIENT_NAME = "NAVER";
    public static OAuthLoginButton mOAuthLoginButton;
    public static OAuthLogin mOAuthLoginInstance;
    private Context context;
    private Activity activity;
    //////////

    public Naver(OAuthLogin oAuthLogin, Context context, Activity activity){
        mOAuthLoginInstance = oAuthLogin;
        this.context = context;
        this.activity = activity;
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(context, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
    }

    public void login(){
        mOAuthLoginButton = (OAuthLoginButton) activity.findViewById(R.id.button_naverlogin);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            //로그인 인증 성공
            if (success) {
                // 사용자 정보 가져오기
                String accessToken = mOAuthLoginInstance.getAccessToken(context);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(context);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(context);
                String tokenType = mOAuthLoginInstance.getTokenType(context);

                new Naver.RequestApiTask(accessToken).execute();
            } else {
                // 로그인 인증 실패
                String errorCode = mOAuthLoginInstance.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(context);
//                Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
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
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode == 200 ? con.getInputStream() : con.getErrorStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                return response;

            } catch (Exception e) {
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
            if (!innerJson.has("email")) {
                Log.d("naver_email", "email 제공 거부");
            } else {
                String email = innerJson.getString("email");
                String name = innerJson.getString("name");

                Init.editor.putString("email", email);
                Init.editor.putString("name", name);
                Init.editor.putString("type", "naver");
                Init.editor.commit();
                Intent intent = new Intent(activity, IndexActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
