package com.INGSW.NaTour.Retrofit.apiInterface;

import com.INGSW.NaTour.Model.DTO.FotoPercorsoDTO;
import com.INGSW.NaTour.Model.FotoPercorso;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FotoPercorsoAPI {

    @GET("sentieri/{id}/photos")
    Single<List<FotoPercorso>> getPhotoBySentiero(@Path("id") Long id);

    @POST("/newfoto")
    Single<Response<Void>> insertPhoto(@Body FotoPercorsoDTO fotoPercorsoDTO);

}
