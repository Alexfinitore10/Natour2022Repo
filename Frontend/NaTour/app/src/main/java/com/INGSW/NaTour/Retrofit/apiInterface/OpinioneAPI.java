package com.INGSW.NaTour.Retrofit.apiInterface;

import com.INGSW.NaTour.Model.DTO.OpinioneDTO;
import com.INGSW.NaTour.Model.Sentiero;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OpinioneAPI {

    @POST("newOpinioni")
    Single<Sentiero> insertOpinione(@Body OpinioneDTO opinioneDTO);

}
