package com.INGSW.NaTour.Retrofit.Request;

import com.INGSW.NaTour.Model.DTO.OpinioneDTO;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.OpinioneCallback;
import com.INGSW.NaTour.Retrofit.RetrofitInstance.RetrofitClient;
import com.INGSW.NaTour.Retrofit.apiInterface.OpinioneAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OpinioneRequest {

    private static OpinioneAPI opinioneAPI;
    private static final String TAG = "OpinioneRequest";

    public OpinioneRequest() {
        opinioneAPI = RetrofitClient.getInstance().create(OpinioneAPI.class);
    }

    public void insertOpinione(OpinioneDTO opinioneDTO, OpinioneCallback opinioneCallback){
        opinioneAPI.insertOpinione(opinioneDTO).
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Sentiero>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Sentiero sentiero) {
                        opinioneCallback.onSuccess(sentiero);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        opinioneCallback.onFailure(e);
                    }
                });

    }

}
