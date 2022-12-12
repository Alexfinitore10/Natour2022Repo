/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Presenter;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.rx.RxAmplify;
import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.User;
import com.INGSW.NaTour.Retrofit.CallbackInterface.UserCallback;
import com.INGSW.NaTour.Retrofit.Request.UserRequest;
import com.INGSW.NaTour.View.Activity.BottomNavigationActivity;
import com.INGSW.NaTour.View.Activity.LogInActivity;
import com.INGSW.NaTour.View.Activity.SignUpActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter {

    private LogInActivity logInActivity;
    private UserRequest userRequest;
    private static final String TAG = "LoginPresenter";

    public LoginPresenter(LogInActivity logInActivity) {
        this.logInActivity = logInActivity;
    }

    public void SignUpRecall() {
        Log.d(TAG,"Passaggio alla SignUpActivity");
        logInActivity.startActivity(new Intent(logInActivity, SignUpActivity.class));
    }

    public void HomepageCall() {
        Log.d(TAG,"Passaggio alla schermata principale");
        logInActivity.startActivity(new Intent(logInActivity, BottomNavigationActivity.class));
    }

    public void AmplifySignin(String Username, String Password){
        SweetAlertDialog pdialog = logInActivity.showLoading();
        RxAmplify.Auth.signIn(Username,Password)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                result -> {
                    Log.d(TAG,"Login effettuato con successo, valori: " + result);
                    Constants.LOGIN = 0; //Login Normale
                    checkAdmin();
                    new Handler().postDelayed((Runnable) () -> {
                        HomepageCall();
                    },1000);
                },
                error ->{
                    Log.e(TAG, "Errore login: " + error.toString());
                    pdialog.dismissWithAnimation();
                    if(error.getCause().toString().contains("UserNotConfirmedException")){
                        logInActivity.showError("Devi confermare l'account");
                    }else logInActivity.showError("Credenziali sbagliate");
                }
            );
    }

    private void checkAdmin(){
        RxAmplify.Auth.fetchUserAttributes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eachAttribute -> {
                            Log.d(TAG, "Attributi: " + eachAttribute.toString());
                            String email = eachAttribute.get(2).getValue();;
                            Log.d(TAG, "Email dell'admin? + " + email);
                            if(email.contains("@natouradmin.com")){
                                Log.d(TAG,"E' un admin");
                                Constants.LOGIN=3;
                            }else{
                                Log.d(TAG, "Non è un admin");
                            }
                        },
                        error -> Log.e(TAG, "Failed to fetch attributes.", error)
                );
    }

    public void AmplifyGoogleLogin(){
        RxAmplify.Auth.signInWithSocialWebUI(AuthProvider.google(), logInActivity)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                result -> {
                    fetchUserAttributes();
                    Log.i(TAG, result.toString());
                    Constants.LOGIN = 1; //Login con Provider
                    HomepageCall();
                },
                error -> Log.e(TAG, error.toString())
            );
    }

    public void AmplifyFacebookLogin(){
        RxAmplify.Auth.signInWithSocialWebUI(AuthProvider.facebook(), logInActivity)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                result -> {
                    fetchUserAttributes();
                    Log.i(TAG, result.toString());
                    Constants.LOGIN = 1; //Login con Provider
                    HomepageCall();
                },
                error -> Log.e(TAG, error.toString())
            );
    }

    public void insertUser(String username, String email) {
        Log.i(TAG,"Inserimento utente in corso");
        User u = new User(username,email);
        userRequest = new UserRequest();
        userRequest.insertUser(u, new UserCallback() {
            @Override
            public void onSuccess(User user) {
                Log.i(TAG,"Inserito con successo!");
            }

            @Override
            public void onSuccessResponse(boolean response) {
                if(!response){
                    Log.e(TAG,"Errore: non sono riusciuto ad inserire l'utente");
                }else{
                    Log.d(TAG,"Inserito con successo!");
                }
            }

            @Override
            public void onSuccessList(List<User> users) {
                Log.i(TAG,"Inserito con successo!");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG,"Errore: non sono riusciuto ad inserire l'utente " + throwable.toString());
            }
        });

    }

    public void fetchUserAttributes(){
        RxAmplify.Auth.fetchUserAttributes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        eachAttribute -> {
                            Log.d(TAG, "Attributi: " + eachAttribute.toString());
                            String username = RxAmplify.Auth.getCurrentUser().getUsername();
                            String email;
                            if(Constants.LOGIN == 0){
                                email = eachAttribute.get(2).getValue();
                            }else{
                                email = eachAttribute.get(3).getValue();
                            }
                            Log.i(TAG+"AuthDemo email", email);
                            Log.i(TAG+"AuthDemo username", username);
                            insertUser(username,email);
                        },
                        error -> Log.e(TAG, "Failed to fetch attributes.", error)
                );
    }

    public void GuestLogin() {
        Constants.LOGIN=2;
        Log.d(TAG,"Login come Guest in corso");
        HomepageCall();
    }
}
