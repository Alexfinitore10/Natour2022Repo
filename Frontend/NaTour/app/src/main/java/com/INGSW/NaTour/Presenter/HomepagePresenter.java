package com.INGSW.NaTour.Presenter;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.util.Log;

import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroCallback;
import com.INGSW.NaTour.Retrofit.Request.SentieroRequest;
import com.INGSW.NaTour.View.Fragment.HomepageFragment;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
        Log.d(TAG, "Search data: " + hour + minute + diff + dis + loc);
        Integer durata = null;
        Boolean disabilità = null;
        if(hour!= null && minute!=null)
            durata=((hour*60)+minute);
        Integer difficolta = convertDiff(diff);
        if(dis != null)
            disabilità = convertDis(dis);
        sentieroRequest = new SentieroRequest();
        sentieroRequest.getSentieriByQuery(loc, durata, difficolta, disabilità, new SentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {}

            @Override
            public void onSuccessList(List<Sentiero> sentieri) {
                Log.d(TAG+" sentieroRequestQuery Risposta: ", sentieri.toString());
                if(sentieri.isEmpty()){
                    Log.d(TAG, "Li lista è vuota");
                    homepageFragment.showError("Non esistono sentieri corrispondenti ai filtri inseriti");
                }else {
                    Log.d(TAG, "La lista è piena e aggiorniamo");
                    homepageFragment.updateRecycler(sentieri);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, throwable.toString());
                homepageFragment.showError("Errore nella ricerca");
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

    private boolean convertDis(String dis){
        Log.d(TAG, dis);
        if(dis.equals("Si")){
            return true;
        }
        return false;
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
