package com.INGSW.NaTour.Extra;

import android.util.Log;

import com.INGSW.NaTour.Model.MapPoint;

import java.util.List;

public class TransferDataIntent {

    private static final String TAG = "TransferDataIntent";
    private static TransferDataIntent transferDataIntent;
    private List<MapPoint> tracciato;

    private TransferDataIntent(){}

    public static TransferDataIntent getInstance(){
        Log.d(TAG, "getInstance");
        if(transferDataIntent == null)
            transferDataIntent = new TransferDataIntent();
        return transferDataIntent;
    }

    public static TransferDataIntent getTransferDataIntent() {
        return transferDataIntent;
    }

    public static void setTransferDataIntent(TransferDataIntent transferDataIntent) {
        Log.d(TAG, "salvataggio data");
        TransferDataIntent.transferDataIntent = transferDataIntent;
    }

    public List<MapPoint> getTracciato() {
        Log.d(TAG, "Trasferimento data");
        return tracciato;
    }

    public void setTracciato(List<MapPoint> tracciato) {
        this.tracciato = tracciato;
    }
}
