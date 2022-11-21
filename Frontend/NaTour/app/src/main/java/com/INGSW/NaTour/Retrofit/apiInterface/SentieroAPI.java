package com.INGSW.NaTour.Retrofit.apiInterface;


import com.INGSW.NaTour.Model.DTO.SentieriDTO;
import com.INGSW.NaTour.Model.MapPoint;
import com.INGSW.NaTour.Model.Sentiero;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SentieroAPI {

    @GET("sentieriorder")
    Single<List<Sentiero>> getSentieriOrder();

    @GET("filtersentieri")
    Single<List<Sentiero>> getSentieriByQuery(@Query("località") String località,
                                              @Query("durata") Integer durata,
                                              @Query("difficolta") Integer difficolta,
                                              @Query("disabile") Boolean disabile);

    @GET("sentieri/{id}/tracciato")
    Single<List<MapPoint>> getTracciatoBySentiero(@Path("id") Long id);

    @POST("sentieri/{id}/newtracciato")
    Single<Response<Void>> insertTracciatoSentiero(@Path("id") Long id, @Body List<MapPoint> mapPoints);


    @POST("newsentiero")
    Single<Response<Void>> insertSentiero(@Body SentieriDTO sentiero);

    @DELETE("/sentieri/delete/{id}")
    Single<Response<Void>> deleteSentiero(@Path("id") Long id);

    @PUT("/sentieri/update/{id}")
    Single<Sentiero> updateSentiero(@Path("id") Long id, @Body SentieriDTO sentieriDTO);

}
