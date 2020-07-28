package co.kr.test200722;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private TextView textView;
    RecyclerView off_list;
    private final String BASE_URL = "http://192.168.219.139:9090/reo/android/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        init(); // URL 초기화

        // 인터페이스 생성
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        // 인터페이스에 구현한 메소드인 contributors에 param 값을 넘기는 요청 만듬
        Call<List<OfficeDTO>> call = retrofitService.getOff_List();
        call.enqueue(new Callback<List<OfficeDTO>>() {
            @Override
            public void onResponse(Call<List<OfficeDTO>> call, Response<List<OfficeDTO>> response) {
                Log.d("office", ""+response.isSuccessful());
                //성공시 실행
                if (response.isSuccessful()) {
                    List<OfficeDTO> offList = response.body();
                    for(OfficeDTO officeDTO : offList){
                        textView.append(officeDTO.getOff_name());
                    }

                }
        }

            @Override
            public void onFailure(Call<List<OfficeDTO>> call, Throwable t) {
                t.printStackTrace();
                Log.d("err", "err : " + t);
            }
        });
    }

    public void init() {
        textView = (TextView) findViewById(R.id.textView);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
