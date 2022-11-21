package com.INGSW.NaTour.Presenter;

import android.util.Log;

import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroCallback;
import com.INGSW.NaTour.Retrofit.Request.SentieroRequest;
import com.INGSW.NaTour.View.Fragment.HomepageFragment;

import java.util.List;

public class HomepagePresenter {

    private static final String TAG = "HomepagePresenter";
    private HomepageFragment homepageFragment;

    private SentieroRequest sentieroRequest;

    public HomepagePresenter(HomepageFragment homepageFragment) {
        this.homepageFragment = homepageFragment;
    }

    public void getItinerari(){
        sentieroRequest = new SentieroRequest();
        sentieroRequest.getSentieriOrder(new SentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {

            }

            @Override
            public void onSuccessList(List<Sentiero> sentieri) {
                Log.d(TAG+" sentieroRequest Risposta: ", sentieri.toString());
                homepageFragment.updateRecycler(sentieri);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

    }


    public void search(Integer hour, Integer minute, String diff, String dis, String loc) {
        Integer durata = null;
        if(hour!= null && minute!=null)
            durata=((hour*60)+minute);
        Integer difficolta = convertDiff(diff);
        Boolean disabilità = null;
        sentieroRequest = new SentieroRequest();
        sentieroRequest.getSentieriByQuery(loc, durata, difficolta, disabilità, new SentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {}

            @Override
            public void onSuccessList(List<Sentiero> sentieri) {
                Log.d(TAG+" sentieroRequestQuery Risposta: ", sentieri.toString());
                homepageFragment.updateRecycler(sentieri);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });

    }

    private Integer convertDiff(String dif){
        if(dif == null)
            return null;
        switch(dif){
            case "Facile":
                return 1;
            case "Medio":
                return 2;
            case "Difficile":
                return 3;
        }
        return null;
    }

    public void delete(Long id) {
        sentieroRequest.deleteSentiero(id, new SentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {
                if(response){
                    Log.d(TAG, "Sentiero eliminato con successo");
                }
            }

            @Override
            public void onSuccessList(List<Sentiero> sentieri) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
