package com.INGSW.NaTour.Retrofit.apiInterface;

import com.INGSW.NaTour.Model.DTO.EmailDTO;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EmailAPI {

    @POST("/sendallmail")
    Single<Response<Void>> sendAllMail(@Body EmailDTO emailDTO);

}
