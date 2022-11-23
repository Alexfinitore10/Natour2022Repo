package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.INGSW.NaTour.Presenter.LoginPresenter;
import com.INGSW.NaTour.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";

    private LoginPresenter loginPresenter;

    Button buttonSignIn;
    TextView buttonSignUp;
    ImageView buttonGoogle;
    ExtendedFloatingActionButton buttonGuest;
    ImageView buttonFacebook;
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

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Tasto Back premuto");
        new MaterialAlertDialogBuilder(this)
                .setTitle("Uscire dall'App?")
                .setMessage("Vuoi uscire dall'App?")
                .setPositiveButton("SÌ", (dialogInterface, i) -> {
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                })
                .setNegativeButton("NO", (dialogInterface, i) -> {})
                .show();
    }
}