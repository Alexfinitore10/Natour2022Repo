package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.INGSW.NaTour.Extra.TransferDataIntent;
import com.INGSW.NaTour.Model.MapPoint;
import com.INGSW.NaTour.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.Serializable;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class OSMapActivity extends AppCompatActivity {

    private static final String TAG = "OSMapActivity";

    private MapView map = null;
    private GeoPoint currentPosition = null;
    private MyLocationNewOverlay locationOverlay;
    private FloatingActionButton btnCurrentPosition;
    private FloatingActionButton btnConfirm;
    private Polyline line = null;
    private Boolean insertMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osmap);

        Context ctx = this.getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        if (!isLocationPermissionGranted())
            requestLocationPermissions();

        if (isPermissionEnable()) {
            Log.d(TAG, "I permessi di localizazzione sono attivi");
        } else {
            Log.e(TAG, "I permessi di localizazzione NON sono attivi");

        }

        btnCurrentPosition = findViewById(R.id.fabCurrent);
        btnConfirm = findViewById(R.id.fabConfirm);
        btnConfirm.setVisibility(View.INVISIBLE);

        map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setClickable(true);
        map.getController().setZoom(18.0);

        map.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        map.setMultiTouchControls(true);

        GeoPoint point = new GeoPoint(40.839317, 14.185151); //Università Federico II Monte Sant'Angelo
        map.getController().setCenter(point);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            Boolean result = (Boolean) bundle.get("Tracciato");
            Log.d(TAG, "Passare in map view? " + result);
            Log.d(TAG, "Passaggio mappa in modalità visualizzazzione");
            insertMode = false;
            new Thread(() -> tracciatoToPolyline(TransferDataIntent.getInstance().getTracciato())).run();
        }

        if(insertMode){
            btnConfirm.setVisibility(View.VISIBLE);

            MapEventsReceiver mReceive = getListener();
            MapEventsOverlay evOverlay = new MapEventsOverlay(mReceive);
            map.getOverlays().add(evOverlay);
        }

        btnCurrentPosition.setOnClickListener(view -> getCurrentLocation());

        btnConfirm.setOnClickListener(view -> {
            if(line.getActualPoints().size() >= 2){
                Intent i = new Intent();
                i.putExtra("result", (Serializable) line.getActualPoints());
                setResult(80, i);
                Log.d(TAG, "Sto per chiudere l'activity");
                finish();
            }else {
                showError("Devi inserire almeno 2 punti");
            }

        });

    }

    public void showError(String error) {
        runOnUiThread(() ->
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(error)
                        .show());
    }

    private MapEventsReceiver getListener(){
        line = new Polyline(map);
        return new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                Marker newMarker = new Marker(map);
                newMarker.setInfoWindow(null);
                newMarker.setPosition(p);

                map.getOverlays().add(newMarker);
                map.getController().setCenter(p);

                line.addPoint(p);

                Log.d(TAG, line.toString());
                Log.d(TAG, line.getActualPoints().toString());
                map.getOverlays().add(line);
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {return false;}
        };
    }

    private void requestLocationPermissions(){
        EasyPermissions.requestPermissions(this,
                "Dammi i permessi",
                1,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE);
    }

    private boolean isLocationPermissionGranted() {
        return EasyPermissions.hasPermissions(this,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE);
    }

    private boolean isPermissionEnable(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    private void getCurrentLocation(){
        Log.d(TAG, "getCurrentLocation");
        if(currentPosition == null){
            Log.d(TAG, "Ricerca posizione corrent");
            locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
            locationOverlay.enableMyLocation();
            locationOverlay.enableFollowLocation();

            locationOverlay.runOnFirstFix(() -> {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Log.d(TAG, "Partito thread runOnFirstFix");
                    currentPosition = locationOverlay.getMyLocation();
                    Log.d(TAG, "La posizione attuale è: " + currentPosition.toString());
                    map.getController().setCenter(currentPosition);
                    map.getOverlays().add(locationOverlay);
                });
            });
        }else {
            Log.d(TAG, "posizione già trovato, centramento in corso");
            map.getController().setCenter(locationOverlay.getMyLocation());
        }
    }

    public void tracciatoToPolyline(List<MapPoint> tracciato){
        Log.d(TAG, "Conversione in corso");
        line = new Polyline(map);
        for (MapPoint p: tracciato){
            GeoPoint g = new GeoPoint(p.getLatitude(), p.getLongitude());

            Marker newMarker = new Marker(map);
            newMarker.setInfoWindow(null);
            newMarker.setPosition(g);
            line.addPoint(g);

            map.getOverlays().add(newMarker);
        }
        Log.d(TAG, "Polyline: " + line.getActualPoints().toString());
        map.getOverlays().add(line);
    }

}