/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.INGSW.NaTour.Extra.Constants;
import com.bumptech.glide.Glide;
import com.INGSW.NaTour.Extra.TransferDataIntent;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Presenter.SentieroPresenter;
import com.INGSW.NaTour.R;
import com.INGSW.NaTour.View.Adapter.ViewPagerAdapter;
import com.INGSW.NaTour.View.Fragment.SentieroFotoFragment;
import com.INGSW.NaTour.View.Fragment.SentieroInformazioniFragment;
import com.google.android.material.tabs.TabLayout;

import org.osmdroid.util.GeoPoint;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SentieroActivity extends AppCompatActivity {

    private static final String TAG = "SentieroActivity";

    SentieroPresenter sentieroPresenter;
    
    ImageView imageView;
    TextView txtNome;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    Sentiero sentiero = null;
    ImageView btnModify;
    SweetAlertDialog dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percorso);

        tabLayout = findViewById(R.id.tablayout);
        txtNome = findViewById(R.id.txtNomePercorso);
        imageView = findViewById(R.id.imageMap);
        viewPager2 = findViewById(R.id.viewpager);
        btnModify = findViewById(R.id.btnModify);
        viewPagerAdapter = new ViewPagerAdapter(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            sentiero = (Sentiero) bundle.get("Itinerario");
            Log.d("Sentiero ricevuto: ", sentiero.toString());
        }

        if(Constants.LOGIN!=3)
            btnModify.setVisibility(View.INVISIBLE);

        sentieroPresenter = new SentieroPresenter(this, sentiero);

        Log.d(TAG,"Creato SentieroPresenter" + sentieroPresenter);

        Glide.with(getApplicationContext()).load(sentiero.getImmagine()).into(imageView);
        txtNome.setText(sentiero.getNome());

        viewPager2.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        btnModify.setOnClickListener(view -> {
            callEdit();
        });
        
        imageView.setOnClickListener(view -> {
            showLoading();
            sentieroPresenter.getTracciato();

            new Handler().postDelayed(() -> {
                goToMap();
            }, 2000);

        });

    }


    public void showLoading() {
        runOnUiThread(() -> {
            dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Caricamento in corso");
            dialog.setCancelable(false);
            dialog.show();
        });
    }

    public void showError(String error) {
        runOnUiThread(() ->
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(error)
                        .show());
    }

    private void goToMap() {
        if(sentiero.getTracciato() != null){
            Log.d(TAG, "Tracciato presente, passaggio a visualizzazione mappa");
            TransferDataIntent.getInstance().setTracciato(sentiero.getTracciato());
            Intent i = new Intent(SentieroActivity.this, OSMapActivity.class);
            i.putExtra("Tracciato", true);
            startActivity(i);
            dialog.dismiss();
        }else {
            showError("Errore del tracciato");
            Log.e(TAG, "Tracciato non presente, impossibile visualizzarlo");
        }
    }

    public SentieroPresenter informationFragmentToPresenter(SentieroInformazioniFragment sentieroInformazioniFragment){
        sentieroPresenter.setSentieroInformazioniFragment(sentieroInformazioniFragment);
        return sentieroPresenter;
    }

    public SentieroPresenter informationFragmentToPresenter(SentieroFotoFragment sentieroFotoFragment) {
        sentieroPresenter.setSentieroFotoFragment(sentieroFotoFragment);
        return sentieroPresenter;
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == 80){
            Log.d(TAG, "Ho ricevuto il sentiero aggiornato");
            Intent i = result.getData();
            Sentiero sentiero = (Sentiero) i.getSerializableExtra("result");
            Log.d(TAG, "Sentiero ricevuto: " + sentiero.toString());
            sentieroPresenter.setSentiero(sentiero);
            txtNome.setText(sentiero.getNome());
            sentieroPresenter.updateView(sentiero);
        }else{
            Log.d(TAG, "Non ho ricevuto niente");
        }
    });



    private void callEdit() {
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra("Sentiero", sentiero);
        activityResultLauncher.launch(i);
    }


}