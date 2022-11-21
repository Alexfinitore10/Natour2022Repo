package com.INGSW.NaTour.Presenter;

import android.util.Log;

import com.INGSW.NaTour.Model.DTO.SentieriDTO;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroCallback;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroSingleCallback;
import com.INGSW.NaTour.Retrofit.Request.SentieroRequest;
import com.INGSW.NaTour.View.Activity.EditActivity;

import java.util.List;

public class EditPresenter {

    private static final String TAG = "EditPresenter";

    private EditActivity editActivity;

    public EditPresenter(EditActivity editActivity) {
        this.editActivity = editActivity;
    }

    public boolean isEqual(Sentiero sentiero, SentieriDTO sentieroDTO){

        boolean equal = true;

        if(!sentiero.getNome().equals(sentieroDTO.getNome()))
            return equal = false;

        if(!sentiero.getDescrizione().equals(sentieroDTO.getDescrizione()))
            return equal = false;

        if(!sentiero.getLocalità().equals(sentieroDTO.getLocalità()))
            return equal = false;

        if(! (sentiero.getDifficolta() == sentieroDTO.getDifficolta()))
            return equal = false;

        if(! (sentiero.getDurata() == sentieroDTO.getDurata()))
            return equal = false;

        if(! (sentiero.isDisabile() == sentieroDTO.isDisabile()))
            return equal = false;

        return equal;
    }


    public void updateSentiero(Long id, SentieriDTO sentieroDTO) {
        SentieroRequest sentieroRequest = new SentieroRequest();
        sentieroRequest.updateSentiero(id, sentieroDTO, new SentieroSingleCallback() {
            @Override
            public void onSuccessResponse(boolean response) {}

            @Override
            public void onSuccess(Sentiero sentiero) {
                Log.d(TAG, "Aggiornato con successo" + sentiero.toString());
                editActivity.success(sentiero);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, throwable.toString());
                editActivity.showError("Errore nell'aggiornare il sentiero");
            }
        });
    }
}
