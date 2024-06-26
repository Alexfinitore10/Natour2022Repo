/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.location.LocationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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
    private FloatingActionButton btnDelete;
    private Polyline line = null;
    private Boolean insertMode = true;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osmap);

        ctx = this.getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        if (!isLocationPermissionGranted()){
            Log.d(TAG,"Permessi non attivi, richiedo permessi");
            requestLocationPermissions();
        }else Log.d(TAG, "Permessi attivi!");


        btnCurrentPosition = findViewById(R.id.fabCurrent);
        btnConfirm = findViewById(R.id.fabConfirm);
        btnConfirm.setVisibility(View.INVISIBLE);
        btnDelete = findViewById(R.id.fabDelete);
        btnDelete.setVisibility(View.INVISIBLE);

        map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setClickable(true);
        map.getController().setZoom(18.0);
        map.setMinZoomLevel(4.0);
        map.setHorizontalMapRepetitionEnabled(false);
        map.setVerticalMapRepetitionEnabled(false);
        map.setScrollableAreaLimitLatitude(MapView.getTileSystem().getMaxLatitude(), MapView.getTileSystem().getMinLatitude(), 0);
        map.setScrollableAreaLimitLongitude(MapView.getTileSystem().getMinLongitude(), MapView.getTileSystem().getMaxLongitude(), 0);

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
            btnDelete.setVisibility(View.VISIBLE);

            MapEventsReceiver mReceive = getListener();
            MapEventsOverlay evOverlay = new MapEventsOverlay(mReceive);
            map.getOverlays().add(evOverlay);
        }

        btnCurrentPosition.setOnClickListener(view -> checkPermissionForLocation());

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

        btnDelete.setOnClickListener(view -> {
            resetLine();
        });

    }

    private void resetLine() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Vuoi davvero tutti i punti che hai tracciato?")
                .setPositiveButton("Si", (dialogInterface, i) -> {
                    if(line!=null)
                        new Handler().post(() -> {
                            line.getActualPoints().clear();
                            map.getOverlays().clear();
                            MapEventsReceiver mReceive = getListener();
                            MapEventsOverlay evOverlay = new MapEventsOverlay(mReceive);
                            map.getOverlays().add(evOverlay);
                        });
                }).show();
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
        line.setInfoWindow(null);
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
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean isLocationPermissionGranted() {
        return EasyPermissions.hasPermissions(this,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private boolean isPermissionEnable(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    private void checkPermissionForLocation(){
        if(!isLocationPermissionGranted()){
            showError("Non hai dato i permessi all'appliccazione, svuota la cache per richiedere i permessi");
            return;
        }
        if (isPermissionEnable()) {
            Log.d(TAG, "La localizazzione sono attivi");
            getCurrentLocation();
        } else {
            Log.e(TAG, "La localizzazzione non è attiva");
            showError("La localizzazzione non è attiva");
        }
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
        line.setInfoWindow(null);
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
        map.getController().setCenter(line.getActualPoints().get(0));
    }



}