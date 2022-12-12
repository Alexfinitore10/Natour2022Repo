/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

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
import java.util.regex.Pattern;

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
                            Log.i(TAG, "Result: " + result.toString());
                            insertUser(username,email);
                            signUpActivity.dismissLoading();
                            signUpActivity.ConfirmCodeDialog(username);
                        },
                        error -> {
                            if (error.getCause().toString().contains("UsernameExistsException")) {
                                signUpActivity.showError("Username già esistente");
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
                            Log.i(TAG, result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                            signUpActivity.showSuccess("Account registrato con successo");
                            signUpActivity.startActivity(new Intent(signUpActivity, LogInActivity.class));
                        },
                        error -> {
                            Log.e(TAG, error.toString());
                            if (error.getCause().toString().contains("AliasExistsException")) {
                                signUpActivity.showError("Email già usata");
                            }else if(error.getCause().toString().contains("CodeExpiredException")){
                                signUpActivity.showError("Il codice inserito non è valido");
                            } else {
                                signUpActivity.showError("Hai sbagliato ad inserire i campi");
                            }
                        }
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

    public boolean isEmailCorrectAndPasswordCorrentandEqual(String email, String pass1, String pass2){
        String regexEmailValid = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String regexStrongPasswordValid = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*\\=\\-\\_\\:\\.\\,\\\"\\€\\/\\\\\\^]).{8,}$";

        Pattern patternEmail = Pattern.compile(regexEmailValid);
        Pattern patternPassword = Pattern.compile(regexStrongPasswordValid);

        if (email == null || pass1==null || pass2==null){
            signUpActivity.showError("Riempi tutti i campi");
            return false;
        }

        if(email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
            signUpActivity.showError("Riempi tutti i campi");
            return false;
        }

        if(!patternEmail.matcher(email).matches()){
            signUpActivity.showError("L'email non è stata inserita correttamente");
            return false;
        }

        if(!patternPassword.matcher(pass1).matches() || !patternPassword.matcher(pass2).matches()){
            signUpActivity.showError("La password deve avere almeno 8 caratteri, 1 maiuscola, 1 carattere speciale e 1 numero");
            return false;
        }

        if(!pass1.equals(pass2)){
            signUpActivity.showError("Le password non coincidono");
            return false;
        }

        return true;
    }




}
