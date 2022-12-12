/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

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

        if(!sentiero.getNome().equals(sentieroDTO.getNome()))
            return false;

        if(!sentiero.getDescrizione().equals(sentieroDTO.getDescrizione()))
            return false;

        if(!sentiero.getLocalità().equals(sentieroDTO.getLocalità()))
            return false;

        if(! (sentiero.getDifficolta() == sentieroDTO.getDifficolta()))
            return false;

        if(! (sentiero.getDurata() == sentieroDTO.getDurata()))
            return false;

        if(! (sentiero.isDisabile() == sentieroDTO.isDisabile()))
            return false;

        return true;
    }


    public void updateSentiero(Long id, SentieriDTO sentieroDTO) {
        editActivity.showLoading();
        SentieroRequest sentieroRequest = new SentieroRequest();
        sentieroRequest.updateSentiero(id, sentieroDTO, new SentieroSingleCallback() {
            @Override
            public void onSuccessResponse(boolean response) {}

            @Override
            public void onSuccess(Sentiero sentiero) {
                Log.d(TAG, "Aggiornato con successo" + sentiero.toString());
                editActivity.showSuccess("Modifica fatto con successo");
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
