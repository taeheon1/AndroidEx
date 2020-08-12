package co.kr.reo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("login.reo")
    Call<ArrayList<MemberDTO>> login(
            @Field("mem_email") String mem_email,
            @Field("mem_pw") String mem_pw
    );

    @POST("getOfficeList.reo")
    Call<ArrayList<OfficeDTO>> getBoardList( );

    @GET("getOffListSearch.reo")
    Call<ArrayList<OfficeDTO>> getOffListSearch(
            @Query("off_type") String seachKeyword
    );

    @GET("qrscan.reo")
    Call<ArrayList<MemberDTO>> qrScan(
            @Query("mem_email") String mem_email
    );
}
