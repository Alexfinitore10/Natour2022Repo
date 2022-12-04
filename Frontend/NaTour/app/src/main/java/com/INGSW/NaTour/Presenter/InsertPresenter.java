package com.INGSW.NaTour.Presenter;

import android.util.Log;

import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Extra.GpxManager;
import com.INGSW.NaTour.Model.DTO.SentieriDTO;
import com.INGSW.NaTour.Model.MapPoint;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroCallback;
import com.INGSW.NaTour.Retrofit.Request.SentieroRequest;
import com.INGSW.NaTour.View.Activity.InsertActivity;

import org.osmdroid.util.GeoPoint;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InsertPresenter {

    private static final String TAG = "InsertPresenter";

    private SentieroRequest sentieroRequest;
    private InsertActivity insertActivity;
    //private OSMapActivity mapActivity;
    private List<MapPoint> points;
    private SentieriDTO sentiero;

    public InsertPresenter(InsertActivity insertActivity) {
        this.insertActivity = insertActivity;
    }

    public boolean isCorrect(String nome, String descrizione, String dif, String dis, String località, Long hour, Long minute) {
        if(nome.length()==0)
            return false;
        if(descrizione.length() == 0)
            return false;
        if(hour != null && hour==0 && minute != null && minute==0)
            return false;
        if(hour == null)
            return false;
        if(minute == null)
            return false;
        if(dif == null)
            return false;
        if(dis == null)
            return false;
        if(località == null)
            return false;
        return true;
    }

    public void gpxMap(InputStream gpxSteam){
        points = GpxManager.getTrack(gpxSteam);
        Log.d(TAG, "Gpx Track :" + points.toString());
        sentiero.setTracciato(points);
        Log.d(TAG, sentiero.toString());
        insertSentiero();
    }

    public void trackFromMap(List<GeoPoint> actualPoints){
        points = convertGeoPointToMapPoint(actualPoints);
        Log.d(TAG, "Map Track :" + points.toString());
        sentiero.setTracciato(points);
        insertSentiero();
    }

    private List<MapPoint> convertGeoPointToMapPoint(List<GeoPoint> geoPoints){
        List<MapPoint> point = new ArrayList<MapPoint>();
        for(GeoPoint g: geoPoints){
            point.add(new MapPoint(g.getLongitude(),g.getLatitude()));
        }
        return point;
    }

    public void createSentiero(String nome, String descrizione, String dif, String dis, String località, Long hour, Long minute) {

        int durata = convertDurata(hour,minute);
        int difficolta = convertDiff(dif);
        boolean disabile = convertDis(dis);

        if(Constants.utente==null){
            Log.e(TAG,"Utente non presente");
            insertActivity.errorDialog();
            return;
        }

        sentiero = new SentieriDTO(nome,descrizione,durata,difficolta,disabile,località, Constants.utente.getId());
        Log.d(TAG, "Sentiero creato: " + sentiero.toString());
    }

    private int convertDiff(String dif){
        switch(dif){
            case "Facile":
                return 1;
            case "Medio":
                return 2;
            case "Difficile":
                return 3;
        }
        return 0;
    }

    private boolean convertDis(String dis){
        Log.d(TAG, dis);
        if(dis.equals("Si")){
            return true;
        }
        return false;
    }

    private int convertDurata(Long hour, Long minute){
        return (int) ((hour*60)+minute);
    }

    public void insertSentiero(){
        insertActivity.progressDialog();
        sentieroRequest = new SentieroRequest();
        sentieroRequest.insertSentiero(sentiero, new SentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {
                if(response){
                    Log.d(TAG, "Percorso inserito con successo tornando alla homepage");
                    insertActivity.successDialog();
                }else {
                    Log.e(TAG, "Percorso non inserito");
                    insertActivity.errorDialog();
                }

            }

            @Override
            public void onSuccessList(List<Sentiero> sentieri) {}

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG,throwable.toString());
                insertActivity.errorDialog();
            }
        });
    }



}
