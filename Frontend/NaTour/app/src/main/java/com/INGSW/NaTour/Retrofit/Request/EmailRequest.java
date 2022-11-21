package com.INGSW.NaTour.Retrofit.Request;

import android.util.Log;

import com.INGSW.NaTour.Model.DTO.EmailDTO;
import com.INGSW.NaTour.Retrofit.CallbackInterface.EmailCallback;
import com.INGSW.NaTour.Retrofit.RetrofitInstance.RetrofitClient;
import com.INGSW.NaTour.Retrofit.apiInterface.EmailAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class EmailRequest {

    private static final String TAG = "EmailRequest";
    private static EmailAPI emailAPI;

    public EmailRequest(){
        emailAPI = RetrofitClient.getInstance().create(EmailAPI.class);
    }

    public void sendAllMail(EmailDTO emailDTO, EmailCallback emailCallback){
        emailAPI.sendAllMail(emailDTO)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        Log.d(TAG, "La risposta ha ricevuto: " + String.valueOf(voidResponse.code()));
                        if(voidResponse.code()==200){
                            emailCallback.onSuccessResponse(true);
                        }else {
                            emailCallback.onSuccessResponse(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        emailCallback.onFailure(e);
                    }
                });
    }
}
