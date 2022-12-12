/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Retrofit.Request;

import android.util.Log;

import com.INGSW.NaTour.Model.User;
import com.INGSW.NaTour.Retrofit.CallbackInterface.UserCallback;
import com.INGSW.NaTour.Retrofit.RetrofitInstance.RetrofitClient;
import com.INGSW.NaTour.Retrofit.apiInterface.UserAPI;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UserRequest {

    private static UserAPI userAPI;
    private static final String TAG = "UserRequest";

    public UserRequest() {
        userAPI = RetrofitClient.getInstance().create(UserAPI.class);
    }

    public void getUsers(UserCallback userCallback){
        userAPI.getUsers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<User> users) {
                        userCallback.onSuccessList(users);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        userCallback.onFailure(e);
                    }
                });
    }

    public void getUserByUsername(String username, UserCallback userCallback){
        userAPI.getUserByUsername(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull User user) {
                        userCallback.onSuccess(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        userCallback.onFailure(e);
                    }
                });
    }


    public void insertUser(User user, UserCallback userCallback){
        userAPI.insertUser(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        Log.d(TAG,"Risposta avvenuto con successo, " + voidResponse.toString());
                        if(voidResponse.code() == 200 || voidResponse.code() == 409){
                            userCallback.onSuccessResponse(true);
                        } else{
                            userCallback.onSuccessResponse(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        userCallback.onFailure(e);
                    }
                });
    }




}
