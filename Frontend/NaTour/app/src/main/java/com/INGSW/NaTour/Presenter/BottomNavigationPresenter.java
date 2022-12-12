/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Presenter;

import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.User;
import com.INGSW.NaTour.Retrofit.CallbackInterface.UserCallback;
import com.INGSW.NaTour.Retrofit.Request.UserRequest;
import com.INGSW.NaTour.View.Activity.BottomNavigationActivity;

import java.util.List;

public class BottomNavigationPresenter {

    private static final String TAG = "BottomNavigationPresenter";
    private BottomNavigationActivity bottomNavigationActivity;
    private UserRequest userRequest;

    public BottomNavigationPresenter(BottomNavigationActivity bottomNavigationActivity) {
        this.bottomNavigationActivity = bottomNavigationActivity;
    }

    public void getUserByUsername(){
        userRequest = new UserRequest();
        userRequest.getUserByUsername(Amplify.Auth.getCurrentUser().getUsername(), new UserCallback() {
            @Override
            public void onSuccess(User user) {
                Constants.utente = user;
                if(user.getEmail().contains("@natouradmin.com"))
                    Constants.LOGIN=3;
                Log.d(TAG,"Dati utente loggato: " + user);
            }

            @Override
            public void onSuccessResponse(boolean response) {

            }

            @Override
            public void onSuccessList(List<User> users) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "Non sono riusciuto a prendere l'utente dal db" + throwable.toString());
            }
        });
    }



}
