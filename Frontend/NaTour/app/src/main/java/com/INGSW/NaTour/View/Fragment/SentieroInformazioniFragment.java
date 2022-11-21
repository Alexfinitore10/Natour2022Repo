package com.INGSW.NaTour.View.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.INGSW.NaTour.Model.Sentiero;
import com.bumptech.glide.Glide;
import com.INGSW.NaTour.Presenter.SentieroPresenter;
import com.INGSW.NaTour.R;
import com.INGSW.NaTour.View.Activity.SentieroActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.concurrent.TimeUnit;

public class SentieroInformazioniFragment extends Fragment {

    private static final String TAG = "SentieroInformazioniFragment";
    private SentieroPresenter sentieroPresenter;
    private TextView txtProfileName;
    private TextView txtDifficulty;
    private TextView txtTime;
    private TextView txtDisable;
    private TextView txtLocalità;
    private TextView txtNome;
    private TextView txtDescription;
    private TextView txtAlert;
    private ImageView imagePropic;
    private ExtendedFloatingActionButton fabOpinione;
    private MaterialAlertDialogBuilder opinioneDialog;
    Integer hour, minute;
    String diff, dis, loc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"onCreateView " + getActivity().toString());
        return inflater.inflate(R.layout.fragment_percorso_informazioni, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");

        sentieroPresenter = ((SentieroActivity)getActivity()).informationFragmentToPresenter(this);
        Log.d(TAG,"Creato SentieroPresenter" + sentieroPresenter);

        txtProfileName = view.findViewById(R.id.profileNameFragment);
        txtLocalità = view.findViewById(R.id.LocalitaFragment);
        txtDifficulty = view.findViewById(R.id.difficultyFragment);
        txtTime = view.findViewById(R.id.timeFragment);
        txtDisable = view.findViewById(R.id.disableFragment);
        txtDescription = view.findViewById(R.id.descriptionFragment);
        imagePropic = view.findViewById(R.id.propicFragment);
        txtNome = view.findViewById(R.id.nomePercorsoFragment);
        txtLocalità = view.findViewById(R.id.LocalitaFragment);
        txtAlert = view.findViewById(R.id.txtAlert);
        fabOpinione = view.findViewById(R.id.buttonOpinione);

        //Log.d(TAG,sentieroPresenter.getSentiero().toString());

        if(sentieroPresenter.getSentiero().isDisabile() == false){
            txtDisable.setVisibility(View.INVISIBLE);
        }else {
            txtDisable.setVisibility(View.VISIBLE);
        }
        if(sentieroPresenter.getSentiero().getDifficolta()==1){
            txtDifficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facile, 0, 0, 0);
        }else if(sentieroPresenter.getSentiero().getDifficolta()==2){
            txtDifficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_medio, 0, 0, 0);
        }
        txtNome.setText(sentieroPresenter.getSentiero().getNome());
        txtProfileName.setText(sentieroPresenter.getSentiero().getUtenteProprietario().getUsername());
        txtDifficulty.setText(convertDifficulty(sentieroPresenter.getSentiero().getDifficolta()));
        txtTime.setText(converTime(sentieroPresenter.getSentiero().getDurata()));
        txtLocalità.setText(sentieroPresenter.getSentiero().getLocalità());
        txtDescription.setText(sentieroPresenter.getSentiero().getDescrizione());
        if(sentieroPresenter.getSentiero().getLastModified() != null)
            txtAlert.setText("Le informazioni sono state modificate da un admin in data: " + sentieroPresenter.getSentiero().getLastModified());
        Glide.with(getContext()).load(sentieroPresenter.getSentiero().getUtenteProprietario().getPropic()).
                circleCrop()
                .into(imagePropic);

        fabOpinione.setOnClickListener(view1 -> {
            opinioneDialog();
        });
    }

    private void opinioneDialog() {
        opinioneDialog = new MaterialAlertDialogBuilder(getActivity());

        final View layout = View.inflate(getContext(), R.layout.dialog_opinione, null);

        TextInputEditText timePicker = layout.findViewById(R.id.editTextTimeDialogOpinione);

        timePicker.setOnClickListener(view -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Seleziona durata")
                    .build();
            materialTimePicker.show(getActivity().getSupportFragmentManager(),"timepicker");

            materialTimePicker.addOnPositiveButtonClickListener(view1 -> {
                hour = materialTimePicker.getHour();
                minute = materialTimePicker.getMinute();
                timePicker.setText(hour + ":" + minute);
            });
        });

        ChipGroup chipGroupDif = layout.findViewById(R.id.chipGroupDifDialogOpinione);


        opinioneDialog.setView(layout);

        opinioneDialog.setTitle("Espermi un opinione: ");
        opinioneDialog.setMessage("La tua opinione verranno presa in considerazione e ricalcolata con la media finale.\n");
        opinioneDialog.setPositiveButton("Invia", (dialogInterface, i) -> {
            Chip chipDif = layout.findViewById(chipGroupDif.getCheckedChipId());
            if(chipDif!=null)
                diff = String.valueOf(chipDif.getText());
            sentieroPresenter.createOpinion(hour,minute,diff);
            resetValue();
        });
        opinioneDialog.setNegativeButton("Annulla", (dialogInterface, i) -> {});

        opinioneDialog.show();
    }

    private String convertDifficulty(int diff){
        switch (diff){
            case 1:
                return "Facile";
            case 2:
                return "Medio";
            case 3:
                return "Difficile";
        }
        return null;
    }

    private String converTime(int time){
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(time));
        long remainMinutes = time - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, remainMinutes);
    }

    private void resetValue(){
        hour=null;
        minute=null;
        diff=null;
        dis=null;
        loc=null;
    }

    public void updateInformation(){
        txtDifficulty.setText(convertDifficulty(sentieroPresenter.getSentiero().getDifficolta()));
        txtTime.setText(converTime(sentieroPresenter.getSentiero().getDurata()));
        if(sentieroPresenter.getSentiero().getDifficolta()==1){
            txtDifficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facile, 0, 0, 0);
        }else if(sentieroPresenter.getSentiero().getDifficolta()==2){
            txtDifficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_medio, 0, 0, 0);
        }
    }


    public void updateSentiero(Sentiero sentiero) {
        txtNome.setText(sentiero.getNome());
        txtLocalità.setText(sentiero.getLocalità());
        txtDescription.setText(sentiero.getDescrizione());
        txtDifficulty.setText(convertDifficulty(sentiero.getDifficolta()));
        txtTime.setText(converTime(sentiero.getDurata()));
        if(sentiero.getLastModified() != null)
            txtAlert.setText("Le informazioni sono state modificate da un admin in data: " + sentiero.getLastModified());
        if(sentiero.isDisabile() == false){
            txtDisable.setVisibility(View.INVISIBLE);
        }else {
            txtDisable.setVisibility(View.VISIBLE);
        }
        if(sentiero.getDifficolta()==1){
            txtDifficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facile, 0, 0, 0);
        }else if(sentiero.getDifficolta()==2){
            txtDifficulty.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_medio, 0, 0, 0);
        }
    }
}