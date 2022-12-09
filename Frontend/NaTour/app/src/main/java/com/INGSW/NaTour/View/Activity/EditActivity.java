package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.DTO.SentieriDTO;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Presenter.EditPresenter;
import com.INGSW.NaTour.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";

    private EditPresenter editPresenter;

    private MaterialToolbar toolbar;
    private ChipGroup chipGroupDif, chipGroupDis;
    private Chip chipFacile, chipMedio, chipDifficile, chipDisabile, chipNonDisabile;
    private ExtendedFloatingActionButton fabNext, fabCancel;
    private SearchableSpinner searchableSpinner;
    private TextInputEditText editTimePicker, editName, editDescription;
    private Long hour, minute;
    private Sentiero sentiero;
    String località = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        editPresenter = new EditPresenter(this);

        toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("Modifica Percorso");

        fabNext = findViewById(R.id.buttonNext);
        editName = findViewById(R.id.editTextPercorso);
        editDescription = findViewById(R.id.editTextDescizione);
        chipGroupDif = findViewById(R.id.chipGroupDif);
        chipGroupDis = findViewById(R.id.chipGroupDisDialog);
        fabCancel = findViewById(R.id.buttonCancel);
        editTimePicker = findViewById(R.id.editTextTime);
        searchableSpinner = findViewById(R.id.searchSpinner1);
        chipFacile = findViewById(R.id.chip9);
        chipMedio = findViewById(R.id.chip7);
        chipDifficile = findViewById(R.id.chip8);
        chipDisabile = findViewById(R.id.chip4);
        chipNonDisabile = findViewById(R.id.chip5);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            sentiero = (Sentiero) bundle.get("Sentiero");
            Log.d("Sentiero ricevuto: ", sentiero.toString());
        }

        Resources res = getResources();
        String[] comuni = res.getStringArray(R.array.comuni);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,comuni);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Log.d(TAG, String.valueOf(arrayAdapter.getPosition(sentiero.getLocalità())));


        searchableSpinner.setAdapter(arrayAdapter);

        searchableSpinner.setTitle("Località");
        searchableSpinner.setPositiveButton("Ok");

        setupField();

        editTimePicker.setOnClickListener(view -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Seleziona durata")
                    .setHour(Math.toIntExact(hour))
                    .setMinute(Math.toIntExact(minute))
                    .build();

            materialTimePicker.show(getSupportFragmentManager(),"timepicker");
            materialTimePicker.addOnPositiveButtonClickListener(view1 -> {
                hour = Long.valueOf(materialTimePicker.getHour());
                minute = Long.valueOf(materialTimePicker.getMinute());
                editTimePicker.setText(hour + ":" + minute);
            });
        });

        fabNext.setText("Modifica");
        fabNext.setOnClickListener(v -> {
            getData();
        });

        fabCancel.setOnClickListener(view -> {
            cancelDialog();
        });

    }

    private void setupField() {
        editName.setText(sentiero.getNome());
        editDescription.setText(sentiero.getDescrizione());
        checkDifficulty(sentiero.getDifficolta());
        checkDisable(sentiero.isDisabile());
        slipTime(sentiero.getDurata());
    }

    private void slipTime(int durata){
        hour = Long.valueOf(durata/60);
        minute = durata - (hour*60);
        editTimePicker.setText(hour + ":" + minute);
    }

    private void checkDisable(boolean disabile) {
        if(disabile)
            chipGroupDis.check(R.id.chip4);
        else
            chipGroupDis.check(R.id.chip5);
    }

    private void checkDifficulty(int difficolta) {
        switch(difficolta){
            case 1:
                chipGroupDif.check(R.id.chip9);
            break;

            case 2:
                chipGroupDif.check(R.id.chip7);
            break;

            case 3:
                chipGroupDif.check(R.id.chip8);
            break;
        }
    }



    private void getData(){
        String nome = String.valueOf(editName.getText());
        String descrizione = String.valueOf(editDescription.getText());

        //Get Difficoltà
        String dif = null;
        Chip chipDif = findViewById(chipGroupDif.getCheckedChipId());
        if(chipDif!=null)
            dif = String.valueOf(chipDif.getText());

        //Get Disabile
        String dis = null;
        Chip chipDis = findViewById(chipGroupDis.getCheckedChipId());
        if(chipDis!=null)
            dis = String.valueOf(chipDis.getText());

        //Get Località
        if(searchableSpinner.getSelectedItem() == null){
            località = sentiero.getLocalità();
        }else{
            località = (String) searchableSpinner.getSelectedItem();
        }

        int durata = (int) ((hour*60)+minute);

        if(Constants.utente==null){
            Log.e(TAG,"Utente non presente");
            showError("Errore, impossibile continuare l'operazione, riprova più tardi");
            return;
        }

        Log.d(TAG, "Dati da aggiornare: " + nome + " " + descrizione + " " + dif + " " + dis + " "+ località + " " + hour + " " + minute);
        SentieriDTO sentieroDTO = new SentieriDTO(nome,descrizione,durata,convertDiff(dif),convertDis(dis),località, Constants.utente.getId());
        Log.d(TAG, sentieroDTO.toString());
        if(!editPresenter.isEqual(sentiero, sentieroDTO)){
            editPresenter.updateSentiero(sentiero.getId(), sentieroDTO);
        }else {
            showError("Devi modificare almeno un elemento");
        }

    }

    private boolean convertDis(String dis){
        Log.d(TAG, dis);
        if(dis.equals("Si")){
            return true;
        }
        return false;
    }

    private void cancelDialog(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("Sei sicuro?")
                .setMessage("Sei sicuro, perderai tutti i dati inseriti")
                .setPositiveButton("Sì", (dialogInterface, i) -> {
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void showError(String error) {
        runOnUiThread(() ->
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(error)
                        .show());
    }

    private int convertDiff(String dif){
        switch(dif){
            case "Facile":
                return 1;
            case "Medio":
                return 2;
            case "Difficile":
                return 3;
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        cancelDialog();
    }

    public void success(Sentiero sentiero) {
        Intent i = new Intent();
        i.putExtra("result", sentiero);
        setResult(80, i);
        Log.d(TAG, "Sto per chiudere l'activity");
        finish();
    }
}