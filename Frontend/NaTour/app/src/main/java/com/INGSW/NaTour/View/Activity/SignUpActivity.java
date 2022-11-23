package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

    SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpPresenter = new SignUpPresenter(this);

        buttonSignUp = findViewById(R.id.buttonSignUp2);
        editTextEmail = findViewById(R.id.editRegisterEmail);
        editTextUsername = findViewById(R.id.editRegisterUsername);
        editTextPassword = findViewById(R.id.editTextRegisterPassword2);
        editTextPassword2 = findViewById(R.id.editTextRegisterPassword);

        buttonSignUp.setOnClickListener(v -> {
            Log.d(TAG, "BottoneSignUp premuto");
            signUp();

            /*
            signUpPresenter.signUp(editTextUsername.getText().toString(),
                    editTextPassword.getText().toString(),
                    editTextEmail.getText().toString());*/
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
                    Log.e(TAG, "Il codice di verifica è: " + verificationCode);
                    signUpPresenter.AmplifyConfirmCode(username, verificationCode);
                })
                .show();
        });
    }

    public void showError(String error) {
        runOnUiThread(() ->
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(error)
                        .show());
    }

    public void showSuccess(String success) {
        runOnUiThread(() ->
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Successo")
                        .setContentText(success)
                        .show());
    }


}
