package hieudxph21411.fpoly.test;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APISinhVien {

    APISinhVien apiSV = new Retrofit.Builder()
            .baseUrl("http://192.168.0.102:9999/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APISinhVien.class);

    @GET("sv")
    Call<ArrayList<SinhVien>> getAllSv();

    @GET("sv/{id}")
    Call<SinhVien> getOneSv(@Path("id") String id);

    @POST("sv")
    Call<SinhVien> addSv(@Body SinhVien sv);

    @DELETE("sv/{id}")
    Call<SinhVien> deleteSv(@Path("id") String id);

    @PUT("sv/{id}")
    Call<SinhVien> editSv(@Path("id") String id, @Body SinhVien sv);

}
