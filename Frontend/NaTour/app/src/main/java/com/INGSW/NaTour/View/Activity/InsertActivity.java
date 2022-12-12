/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.INGSW.NaTour.Presenter.InsertPresenter;
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

import org.osmdroid.util.GeoPoint;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InsertActivity extends AppCompatActivity {

    private static final String TAG = "InsertActivity";

    private InsertPresenter insertPresenter;
    private ChipGroup chipGroupDif, chipGroupDis;
    private ExtendedFloatingActionButton fabNext, fabCancel;
    private SearchableSpinner searchableSpinner;
    private TextInputEditText editTimePicker, editName, editDescription;
    private Long hour, minute;
    private SweetAlertDialog pDialog;
    
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        insertPresenter = new InsertPresenter(this);

        fabNext = findViewById(R.id.buttonNext);
        editName = findViewById(R.id.editTextPercorso);
        editDescription = findViewById(R.id.editTextDescizione);
        chipGroupDif = findViewById(R.id.chipGroupDif);
        chipGroupDis = findViewById(R.id.chipGroupDisDialog);
        fabCancel = findViewById(R.id.buttonCancel);
        editTimePicker = findViewById(R.id.editTextTime);
        searchableSpinner = findViewById(R.id.searchSpinner1);

        Resources res = getResources();
        String[] comuni = res.getStringArray(R.array.comuni);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,comuni);
        searchableSpinner.setAdapter(arrayAdapter);

        searchableSpinner.setTitle("Località");
        searchableSpinner.setPositiveButton("Ok");

        editTimePicker.setOnClickListener(view -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Seleziona durata")
                    .build();
            materialTimePicker.show(getSupportFragmentManager(),"timepicker");

            materialTimePicker.addOnPositiveButtonClickListener(view1 -> {
                hour = Long.valueOf(materialTimePicker.getHour());
                minute = Long.valueOf(materialTimePicker.getMinute());
                editTimePicker.setText(hour + ":" + minute);
            });
        });

        fabNext.setOnClickListener(v -> {
            getData();
        });

        fabCancel.setOnClickListener(view -> {
            cancelDialog();
        });

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
        String località = (String) searchableSpinner.getSelectedItem();

        Log.d(TAG, "Dati: " + nome + " " + descrizione + " " + dif + " " + dis + " "+ località + " " + hour + " " + minute);

        if(insertPresenter.isCorrect(nome, descrizione, dif, dis, località, hour, minute)){
            insertPresenter.createSentiero(nome, descrizione, dif, dis, località, hour, minute);
        }else {
            showError("Compila tutti i campi in modo giusto");
        }
    }



    public void choiceDialog(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("Come vuoi inserire un tracciato?")
                .setPositiveButton("Mappa", (dialogInterface, i) -> {
                    Log.d(TAG, "Scelta la mappa");
                    goMap();
                })
                .setNegativeButton("Gpx", (dialogInterface, i) -> {
                    Log.d(TAG, "Scelto il gpx");
                    selectGPXFile();
                })
                .show();
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == 80){
            Log.d(TAG, "Ho ricevuto dei dati");
            Intent i = result.getData();
            List<GeoPoint> points = (List<GeoPoint>) i.getSerializableExtra("result");
            Log.d(TAG, "Point mappa: " + points.toString());
            insertPresenter.trackFromMap(points);
        }else{
            Log.d(TAG, "Non ho ricevuto niente");
        }
    });



    private void goMap() {
        Intent i = new Intent(this, OSMapActivity.class);
        activityResultLauncher.launch(i);
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

    public void showError(String text) {
        runOnUiThread(() ->
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(text)
                        .show());
    }

    private void selectGPXFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/octet-stream");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        someActivityResultLauncher.launch(intent);
    }

    public void progressDialog(){
        pDialog = new SweetAlertDialog(InsertActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();
    }

    public void successDialog(){
        pDialog.setTitleText("Loading Complete");
        pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        pDialog.cancel();
        finish();
    }


    public void errorDialog(){
        pDialog.setTitleText("Error");
        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        pDialog.cancel();
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri gpxSelected = data.getData();
                    InputStream gpxSteam = null;
                    try {
                        gpxSteam = getApplicationContext().getContentResolver().openInputStream(gpxSelected);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        showError("Impossibile aprire il file");
                    }
                    insertPresenter.gpxMap(gpxSteam);
                }else{showError("Errore");}
            });

    @Override
    public void onBackPressed() {
        cancelDialog();
    }
}