package com.INGSW.NaTour.Presenter;

import android.content.Intent;
import android.util.Log;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.rx.RxAmplify;
import com.INGSW.NaTour.Model.User;
import com.INGSW.NaTour.Retrofit.CallbackInterface.UserCallback;
import com.INGSW.NaTour.Retrofit.Request.UserRequest;
import com.INGSW.NaTour.View.Activity.LogInActivity;
import com.INGSW.NaTour.View.Activity.SignUpActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpPresenter {

    private SignUpActivity signUpActivity;
    private static final String TAG = "SignUpPresenter";
    private UserRequest userRequest;

    public SignUpPresenter(SignUpActivity signUpActivity) {
        this.signUpActivity = signUpActivity;
    }

    public void signUp(String username, String password, String email) {
        RxAmplify.Auth.signUp(username, password, AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), email).build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            Log.i("AuthQuickStart", "Result: " + result.toString());
                            insertUser(username,email);
                            signUpActivity.ConfirmCodeDialog(username);
                        },
                        error -> {
                            if (error.getCause().toString().contains("UsernameExistsException")) {
                                signUpActivity.runOnUiThread(() -> new SweetAlertDialog(signUpActivity, SweetAlertDialog.ERROR_TYPE).setTitleText("Errore").setContentText("Username già esiste!").show());
                            } else {
                                Log.e(TAG, error.toString());
                            }
                        }
                );
    }

    public void AmplifyConfirmCode(String username, String confirmationCode) {
        RxAmplify.Auth.confirmSignUp(username, confirmationCode)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                            signUpActivity.startActivity(new Intent(signUpActivity, LogInActivity.class));
                        },
                        error -> Log.e("AuthQuickstart", error.toString())
                );
    }

    public void insertUser(String username, String email) {
        Log.i(TAG, "Inserimento utente in corso");
        User u = new User(username, email);
        userRequest = new UserRequest();
        userRequest.insertUser(u, new UserCallback() {
            @Override
            public void onSuccess(User user) {
            }

            @Override
            public void onSuccessResponse(boolean response) {
                if (!response) {
                    Log.e(TAG, "Errore: non sono riusciuto ad inserire l'utente");
                } else {
                    Log.d(TAG, "Inserito con successo!");
                }
            }

            @Override
            public void onSuccessList(List<User> users) {
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "Errore: non sono riusciuto ad inserire l'utente " + throwable.toString());
            }
        });

    }


}
