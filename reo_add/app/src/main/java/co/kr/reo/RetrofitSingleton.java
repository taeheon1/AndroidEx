package co.kr.reo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static RetrofitSingleton retrofitSingleton = new RetrofitSingleton();
    private static RetrofitService retrofitService;

    private RetrofitSingleton(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Timestamp.class, new TimestampAdapter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.219.130:8090/reo/android/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitService =retrofit.create(RetrofitService.class);
    }

    public static RetrofitSingleton getInstance(){
        return retrofitSingleton;
    }

    public RetrofitService getRetrofitService(){
        return retrofitService;
    }


}
