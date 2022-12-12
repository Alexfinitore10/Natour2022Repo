/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.INGSW.NaTour.Presenter.SignUpPresenter;
import com.INGSW.NaTour.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    Button buttonSignUp;
    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPassword2;
    TextView txtCode;
    SweetAlertDialog pDialog;

    SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpPresenter = new SignUpPresenter(this);

        txtCode = findViewById(R.id.buttonCode);
        buttonSignUp = findViewById(R.id.buttonSignUp2);
        editTextEmail = findViewById(R.id.editRegisterEmail);
        editTextUsername = findViewById(R.id.editRegisterUsername);
        editTextPassword = findViewById(R.id.editTextRegisterPassword2);
        editTextPassword2 = findViewById(R.id.editTextRegisterPassword);

        buttonSignUp.setOnClickListener(v -> {
            Log.d(TAG, "BottoneSignUp premuto");
            showLoading();
            signUp();
        });

        txtCode.setOnClickListener(view -> {
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);

            final EditText input = new EditText(this);
            dialogBuilder.setView(input);

            dialogBuilder.setCancelable(false)
                    .setTitle("Inserisci l'username")
                    .setMessage("Inserisci l'username dell'account a quale vuoi confermare l'iscrizione")
                    .setPositiveButton("INVIA", (dialogInterface, i) -> {
                        String username = String.valueOf(input.getText());
                        Log.d(TAG, "L'username per la conferma è: " + username);
                        ConfirmCodeDialog(username);
                    })
                    .show();
        });

    }

    private void signUp(){
        String email = editTextEmail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password1 = editTextPassword.getText().toString();
        String password2 = editTextPassword2.getText().toString();

        if(signUpPresenter.isEmailCorrectAndPasswordCorrentandEqual(email, password1, password2)){
            signUpPresenter.signUp(username,password1,email);
        }
    }

    public void ConfirmCodeDialog(String username){
        runOnUiThread(() -> {
            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(this);

            final EditText input = new EditText(this);
            dialogBuilder.setView(input);

            dialogBuilder.setCancelable(false)
                .setTitle("Codice di conferma")
                .setMessage("Inserisci il codice di conferma che hai ricevuto via email")
                .setPositiveButton("INVIA", (dialogInterface, i) -> {
                    String verificationCode = String.valueOf(input.getText());
                    Log.d(TAG, "Il codice di verifica è: " + verificationCode);
                    showLoading();
                    signUpPresenter.AmplifyConfirmCode(username, verificationCode);
                })
                .show();
        });
    }

    public void showError(String error) {
        runOnUiThread(() -> {
            dismissLoading();
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Errore")
                    .setContentText(error)
                    .show();
        });
    }

    public void showSuccess(String success) {
        runOnUiThread(() ->{
            dismissLoading();
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successo")
                    .setContentText(success)
                    .show();
        });

    }

    public void showLoading(){
        runOnUiThread(() -> {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("");
            pDialog.setCancelable(false);
            pDialog.show();
        });
    }

    public void dismissLoading(){
        if(pDialog!=null){
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
