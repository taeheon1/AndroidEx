package co.kr.reo;

import android.text.Html;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
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
    Call<ArrayList<OfficeDTO>> getBoardList();

    @FormUrlEncoded
    @POST("reservCheck.reo")
    Call<ArrayList<ReservationDTO>> getReservListPast(
            @Field("mem_email") String mem_email
    );

    @FormUrlEncoded
    @POST("reservCheck2.reo")
    Call<ArrayList<ReservationDTO>> getReservListNow(
            @Field("mem_email") String mem_email
    );

    @GET("getOfficeListByType.reo")
    Call<ArrayList<OfficeDTO>> getOffListType(
            @Query("off_type") String off_type
    );

    @GET("getOffice.reo")
    Call<ArrayList<OfficeDTO>> getOffice(
            @Query("off_no") int off_no
    );

    @GET("getOfficeImgs.reo")
    Call<ArrayList<OfficeImgsDTO>> getOfficeImgs(
            @Query("off_no") int off_no
    );
    @FormUrlEncoded
    @POST("makeReserv.reo")
    Call<Integer> makeReservation(
            @Field("room_price") int room_price,
            @Field("res_people") int res_people,
            @Field("off_name") String off_name,
            @Field("off_no") int off_no,
            @Field("res_startdatetime") Timestamp res_startdatetime,
            @Field("res_enddatetime") Timestamp res_enddatetime,
            @Field("res_memo") String res_memo,
            @Field("mem_email") String mem_email,
            @Field("mem_agentName") String mem_agentName,
            @Field("mem_agentTel") String mem_agentTel

    );

    @FormUrlEncoded
    @POST("reservAddList.reo")
    Call<ArrayList<String>> resList(
            @Field("off_no") int off_no,
            @Field("res_datetime") Timestamp res_datetime
    );

    @GET("getOfficeList.jsp")
    Call<Html> abc();

}
