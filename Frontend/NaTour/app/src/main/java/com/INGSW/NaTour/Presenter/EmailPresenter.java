/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Presenter;

import android.util.Log;

import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.DTO.EmailDTO;
import com.INGSW.NaTour.Model.Email;
import com.INGSW.NaTour.Retrofit.CallbackInterface.EmailCallback;
import com.INGSW.NaTour.Retrofit.Request.EmailRequest;
import com.INGSW.NaTour.View.Fragment.EmailFragment;

import java.util.List;

public class EmailPresenter {

    private static final String TAG = "EmailPresenter";
    private EmailFragment emailFragment;

    public EmailPresenter(EmailFragment emailFragment) {
        this.emailFragment = emailFragment;
    }

    public boolean check(String mailObject, String mailBody){
        if(mailObject != null && mailBody!=null){
            if(mailObject.length() != 0 && mailBody.length() != 0){
                return true;
            }
        }
        return false;
    }

    public void sendEmail(String mailObject, String mailBody){
        EmailDTO emailDTO = new EmailDTO( mailObject, mailBody, Constants.utente.getId());
        Log.d(TAG, "EmailDTO da inviare: " + emailDTO.toString());
        emailFragment.showLoading();
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.sendAllMail(emailDTO, new EmailCallback() {
            @Override
            public void onSuccessResponse(boolean response) {
                if(response){
                    emailFragment.showSuccess("Email inviata con successo");

                }
            }

            @Override
            public void onSuccessList(List<Email> emailList) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "Errore nell'invio dell'email" + throwable.toString());
            }
        });

    }

}
