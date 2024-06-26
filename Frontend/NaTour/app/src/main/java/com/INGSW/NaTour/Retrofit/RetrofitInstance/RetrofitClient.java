/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Retrofit.RetrofitInstance;

import android.util.Log;

import com.INGSW.NaTour.Extra.Constants;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";
    private static Retrofit RetrofitClient;

    private static OkHttpClient okClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.MINUTES)
                .writeTimeout(60, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.MINUTES)
                .build();
    }

    public static Retrofit getInstance(){
        Log.d(TAG, "Creazione dell'instanza di Retrofit");
        if(RetrofitClient == null){
            Log.d(TAG,"RetrofitClient non presente, creazione in corso");
            RetrofitClient = new Retrofit.Builder()
                    .baseUrl("http://" + Constants.IP_ADDRESS + ":" + Constants.PORT+ "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return RetrofitClient;
    }

    private RetrofitClient(){}


}
