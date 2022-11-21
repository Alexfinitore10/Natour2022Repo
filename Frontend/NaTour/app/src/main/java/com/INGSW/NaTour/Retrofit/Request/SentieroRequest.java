package com.INGSW.NaTour.Retrofit.Request;


import android.util.Log;

import com.INGSW.NaTour.Model.DTO.SentieriDTO;
import com.INGSW.NaTour.Model.MapPoint;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroCallback;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroSingleCallback;
import com.INGSW.NaTour.Retrofit.CallbackInterface.TracciatoSentieroCallback;
import com.INGSW.NaTour.Retrofit.RetrofitInstance.RetrofitClient;
import com.INGSW.NaTour.Retrofit.apiInterface.SentieroAPI;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class SentieroRequest {

    private static SentieroAPI sentieroAPI;
    private static final String TAG = "SentieroRequest";

    public SentieroRequest() {
        sentieroAPI = RetrofitClient.getInstance().create(SentieroAPI.class);
    }

    public void deleteSentiero(Long id, SentieroCallback sentieroCallback){
        sentieroAPI.deleteSentiero(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        Log.d(TAG,"Risposta avvenuto con successo, " + voidResponse.toString());
                        if(voidResponse.code() == 200){
                            sentieroCallback.onSuccessResponse(true);
                        } else{
                            sentieroCallback.onSuccessResponse(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sentieroCallback.onFailure(e);
                    }
                });
    }

    public void insertSentiero(SentieriDTO sentiero, SentieroCallback sentieroCallback){
        sentieroAPI.insertSentiero(sentiero)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        Log.d(TAG,"Risposta avvenuto con successo, " + voidResponse.toString());
                        if(voidResponse.code() == 200){
                            sentieroCallback.onSuccessResponse(true);
                        } else{
                            sentieroCallback.onSuccessResponse(false);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sentieroCallback.onFailure(e);
                    }
                });
    }

    public void getTracciatoBySentiero(Long id, TracciatoSentieroCallback tracciatoCallback){
        sentieroAPI.getTracciatoBySentiero(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MapPoint>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<MapPoint> mapPoints) {
                        tracciatoCallback.onSuccessList(mapPoints);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        tracciatoCallback.onFailure(e);
                    }
                });
    }

    public void getSentieriOrder(SentieroCallback Sentierocallback){
        sentieroAPI.getSentieriOrder()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Sentiero>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Sentiero> sentieros) {
                        Sentierocallback.onSuccessList(sentieros);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Sentierocallback.onFailure(e);
                    }
                });
    }

    public void getSentieriByQuery(String località, Integer durata, Integer difficolta, Boolean disabile, SentieroCallback sentieroCallback){
        sentieroAPI.getSentieriByQuery(località,durata,difficolta,disabile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Sentiero>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Sentiero> sentieros) {
                        sentieroCallback.onSuccessList(sentieros);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sentieroCallback.onFailure(e);
                    }
                });
    }

    public void updateSentiero(Long id, SentieriDTO sentieroDTO, SentieroSingleCallback sentieroCallback){
        sentieroAPI.updateSentiero(id, sentieroDTO)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Sentiero>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Sentiero sentiero) {
                        sentieroCallback.onSuccess(sentiero);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        sentieroCallback.onFailure(e);
                    }
                });
    }

    public void insertTracciatoSentiero(Long id, List<MapPoint> mapPoints, SentieroCallback sentieroCallback){
        sentieroAPI.insertTracciatoSentiero(id, mapPoints)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Response<Void>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            //TODO: Fare un logo
                        }

                        @Override
                        public void onSuccess(@NonNull Response<Void> voidResponse) {
                            Log.d(TAG,"Risposta avvenuto con successo, " + voidResponse.toString());
                            if(voidResponse.code() == 200){
                                sentieroCallback.onSuccessResponse(true);
                            } else{
                                sentieroCallback.onSuccessResponse(false);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            sentieroCallback.onFailure(e);
                        }
                    });
    }

}
