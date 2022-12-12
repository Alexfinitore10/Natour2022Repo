/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.INGSW.NaTour.Presenter.EmailPresenter;
import com.INGSW.NaTour.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmailFragment extends Fragment {

    private static EmailFragment emailFragment;
    private EmailPresenter emailPresenter;

    private static final String TAG = "EmailFragment";

    private ExtendedFloatingActionButton buttonEmail;
    private TextInputEditText editTextObject;
    private TextInputEditText editTextBody;
    private SweetAlertDialog progressDialog;

    public EmailFragment() {/* Required empty public constructor*/}

    public static EmailFragment getInstance(){
        if(emailFragment == null){
            Log.d(TAG, "Invio emailFragment");
            emailFragment = new EmailFragment();
        }

        Log.d(TAG, "Invio emailFragment");
        return emailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emailPresenter = new EmailPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonEmail = view.findViewById(R.id.buttonEmail);
        editTextBody = view.findViewById(R.id.editTextEmailBody);
        editTextObject = view.findViewById(R.id.editTextEmailObject);

        buttonEmail.setOnClickListener(view1 -> {
            String emailObject = editTextObject.getText().toString();
            String emailBody = editTextBody.getText().toString();
            Log.d(TAG, "Dati email: " + emailObject + " " + emailBody);
            if(emailPresenter.check(emailObject, emailBody)){
                emailPresenter.sendEmail(emailObject, emailBody);
            }else {
                showError("Inserisci dei valori corretti");
            }
        });
    }

    public void showError(String error) {
        Log.e(TAG, "Errore popup");
        progressDialog.dismissWithAnimation();
        runOnUiThread(() ->
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(error)
                        .show());
    }

    public void showSuccess(String success) {
        Log.d(TAG, "Success popup");
        if(progressDialog != null)
            progressDialog.dismissWithAnimation();
        runOnUiThread(() ->
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success")
                        .setContentText(success)
                        .show());
        emptyField();
    }

    public void showLoading(){
        runOnUiThread(() -> {
            progressDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            progressDialog.setTitleText("Loading ...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        });
    }



    private void emptyField(){
        editTextBody.setText(null);
        editTextObject.setText(null);
    }
}