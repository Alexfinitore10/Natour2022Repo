package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.INGSW.NaTour.Presenter.LoginPresenter;
import com.INGSW.NaTour.R;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";

    private LoginPresenter loginPresenter;

    Button buttonSignIn;
    Button buttonSignUp;
    Button buttonGoogle;
    Button buttonGuest;
    Button buttonFacebook;
    TextInputEditText editTextUsername;
    TextInputEditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginPresenter = new LoginPresenter(this);

        buttonGuest = findViewById(R.id.buttonGuest);
        buttonSignIn = findViewById(R.id.buttonCreate);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonGoogle = findViewById(R.id.buttonGoogle);
        buttonFacebook = findViewById(R.id.buttonFacebook);
        editTextUsername = findViewById(R.id.editUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        //Bottone per la SignUpActivity
        buttonSignUp.setOnClickListener(v -> {
            Log.d(TAG,"Bottone SignUp premuto");
            loginPresenter.SignUpRecall();
        });

        //Bottone SignIn
        buttonSignIn.setOnClickListener(v -> {
            Log.d(TAG,"Bottone SignIn premuto");
            loginPresenter.AmplifySignin(editTextUsername.getText().toString(), editTextPassword.getText().toString());
        });

        buttonGoogle.setOnClickListener(v -> {
            Log.d(TAG,"Bottone SignGoogle premuto");
            loginPresenter.AmplifyGoogleLogin();
        });

        buttonFacebook.setOnClickListener(v -> {
            Log.d(TAG,"Bottone SignFacebook premuto");
            loginPresenter.AmplifyFacebookLogin();
        });

        buttonGuest.setOnClickListener(view -> {
            Log.d(TAG,"Bottone Guest Login premuto");
            loginPresenter.GuestLogin();
            //startActivity(new Intent(this, Homepage1Activity.class));
        });

    }

    public void showError(String error) {
        runOnUiThread(() ->
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Errore")
                    .setContentText(error)
                    .show());
    }

    public SweetAlertDialog showLoading(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("");
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;
    }
}