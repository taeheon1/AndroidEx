package co.kr.test200722;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {

    @GET("officeList.reo")
    Call<List<OfficeDTO>> getOff_List();
}
