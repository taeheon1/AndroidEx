package co.kr.reo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Spring {
    private String type = "spring";
    private Activity activity;
    private Context context;
    private Init first;

    public Spring(Activity activity,Init first,Context context){
        this.activity=activity;
        this.first = first;
        this.context = context;
    }

    public void login(){
        TextView loginbtn = (TextView) activity.findViewById(R.id.login);
        loginbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String mem_email = ((EditText) activity.findViewById(R.id.username_inpubox)).getText().toString().trim();
                        String mem_pw = ((EditText) activity.findViewById(R.id.password_inpubox)).getText().toString();
                        RetrofitSingleton retrofitSingleton = RetrofitSingleton.getInstance();
                        Call<ArrayList<MemberDTO>> call = retrofitSingleton.getRetrofitService().login(mem_email, mem_pw);

                        call.enqueue(new Callback<ArrayList<MemberDTO>>() {
                            @Override
                            public void onResponse(Call<ArrayList<MemberDTO>> call, Response<ArrayList<MemberDTO>> response) {
                                if (response.isSuccessful()) {
                                    ArrayList<MemberDTO> user = response.body();
                                    if (!user.isEmpty()) {
                                        Log.d("MainActivity", "success: " + user.get(0).getMem_email());
                                        String email = user.get(0).getMem_email();
                                        String name = user.get(0).getMem_name();

                                        Log.d("MainActivity", ""+ (mem_email.equals(user.get(0).getMem_email())));
                                        if (mem_email.equals(user.get(0).getMem_email())) {
                                            View header = first.getNavigationView().getHeaderView(0);
                                            TextView user_login = (TextView) header.findViewById(R.id.user_login);
                                            TextView user_name = (TextView) header.findViewById(R.id.user_name);

                                            user_login.setText(user.get(0).getMem_email());
                                            user_name.setText(user.get(0).getMem_name());
                                            user_name.setVisibility(View.VISIBLE);
                                            Log.d("MainActivity", "1111");

                                            Init.editor.putString("email", email);
                                            Init.editor.putString("name", name);
                                            Init.editor.putString("type", type);
                                            Init.editor.commit();
                                            Intent intent = new Intent(activity, IndexActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            Log.d("MainActivity", "2222");
                                            activity.startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(context, "아이디 혹은 비밀번호 입력을 잘못하셨습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<MemberDTO>> call, Throwable t) {
                                Log.d("MainActivity", "error" + t.getMessage());
                            }
                        });

                    }
                }
        );
    }

}
