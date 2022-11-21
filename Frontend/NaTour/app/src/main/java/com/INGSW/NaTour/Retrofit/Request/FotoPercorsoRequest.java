package com.INGSW.NaTour.Retrofit.Request;

import android.util.Log;

import com.INGSW.NaTour.Model.DTO.FotoPercorsoDTO;
import com.INGSW.NaTour.Model.FotoPercorso;
import com.INGSW.NaTour.Retrofit.CallbackInterface.FotoPercorsoCallback;
import com.INGSW.NaTour.Retrofit.RetrofitInstance.RetrofitClient;
import com.INGSW.NaTour.Retrofit.apiInterface.FotoPercorsoAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class FotoPercorsoRequest {

    private static final String TAG = "FotoPercorsoRequest";
    private static FotoPercorsoAPI fotoPercorsoAPI;

    public FotoPercorsoRequest() {
        fotoPercorsoAPI = RetrofitClient.getInstance().create(FotoPercorsoAPI.class);
    }

    public void insertPhoto(FotoPercorsoDTO fotoPercorsoDTO, FotoPercorsoCallback fotoPercorsoCallback){
        fotoPercorsoAPI.insertPhoto(fotoPercorsoDTO)
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
                            fotoPercorsoCallback.onSuccessResponse(true);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fotoPercorsoCallback.onFailure(e);
                    }
                });
    }

    public void getPhotoBySentiero(Long id, FotoPercorsoCallback fotoPercorsoCallback){
        fotoPercorsoAPI.getPhotoBySentiero(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FotoPercorso>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<FotoPercorso> fotosPercorso) {
                        fotoPercorsoCallback.onSuccessList(fotosPercorso);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        fotoPercorsoCallback.onFailure(e);
                    }
                });
    }

}
