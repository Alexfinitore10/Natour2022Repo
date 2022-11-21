package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.INGSW.NaTour.Presenter.SignUpPresenter;
import com.INGSW.NaTour.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    Button buttonSignUp;
    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPassword;

    SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpPresenter = new SignUpPresenter(this);

        buttonSignUp = findViewById(R.id.buttonSignUp2);
        editTextEmail = findViewById(R.id.editTextEmail2);
        editTextUsername = findViewById(R.id.editTextUsername2);
        editTextPassword = findViewById(R.id.editTextPassword2);

        buttonSignUp.setOnClickListener(v -> {
            Log.d(TAG, "BottoneSignUp premuto");
            signUpPresenter.signUp(editTextUsername.getText().toString(),
                    editTextPassword.getText().toString(),
                    editTextEmail.getText().toString());
        });
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
}
