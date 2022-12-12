/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Retrofit.apiInterface;

import com.INGSW.NaTour.Model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("utenti")
    Single<List<User>> getUsers();

    @GET("utente/{username}")
    Single<User> getUserByUsername(@Path("username") String username);

    @POST("newutente")
    Single<Response<Void>> insertUser(@Body User user);



}
