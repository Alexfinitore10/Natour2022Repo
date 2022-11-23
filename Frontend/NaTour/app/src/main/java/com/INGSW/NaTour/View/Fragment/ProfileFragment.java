package com.INGSW.NaTour.View.Fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.INGSW.NaTour.View.Activity.InsertActivity;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.rx.RxAmplify;
import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.MainActivity;
import com.INGSW.NaTour.Model.MapPoint;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.R;
import com.INGSW.NaTour.Retrofit.CallbackInterface.SentieroCallback;
import com.INGSW.NaTour.Retrofit.Request.SentieroRequest;
import com.INGSW.NaTour.View.Activity.LogInActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.ticofab.androidgpxparser.parser.GPXParser;
import io.ticofab.androidgpxparser.parser.domain.Gpx;
import io.ticofab.androidgpxparser.parser.domain.Route;
import io.ticofab.androidgpxparser.parser.domain.RoutePoint;
import io.ticofab.androidgpxparser.parser.domain.TrackPoint;
import io.ticofab.androidgpxparser.parser.domain.WayPoint;

import org.xmlpull.v1.XmlPullParserException;

public class ProfileFragment extends Fragment {

    Button signOutButton;

    private static ProfileFragment profileFragment;
    private static String TAG = "ProfileFragment";
    private SweetAlertDialog pDialog;

    private ProfileFragment() {/* Required empty public constructor*/}

    public static ProfileFragment getInstance(){
        if(profileFragment == null){
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signOutButton = (Button) getView().findViewById(R.id.SignOutButton);

        signOutButton.setOnClickListener(view1 -> {
            if(Constants.LOGIN==2)
                startActivity(new Intent(getActivity(), LogInActivity.class));
            signOut();
        });

        getView().findViewById(R.id.button3).setOnClickListener(view12 -> {
            //testQuery("Milano");
            List<MapPoint> mapPoints = new ArrayList<MapPoint>();
            mapPoints.add(new MapPoint(24.17,43.05));
            //mapPoints.add(new MapPoint(25.17,44.05));

            System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH"+ mapPoints);

            Long id = new Long(2);

            SentieroRequest sentieroRequest = new SentieroRequest();
            sentieroRequest.insertTracciatoSentiero(id, mapPoints, new SentieroCallback() {
                @Override
                public void onSuccessResponse(boolean response) {
                    if(!response){
                        Log.e(TAG,"Errore: non sono riusciuto ad inserire il tracciato");
                    }else{
                        Log.d(TAG,"Inserito con successo!");
                    }
                }

                @Override
                public void onSuccessList(List<Sentiero> sentieri) {

                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e(TAG,"Errore: non sono riusciuto ad inserire il tracciato " + throwable.toString());
                }
            });

        });

        getView().findViewById(R.id.GPX).setOnClickListener(view13 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/octet-stream");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            someActivityResultLauncher.launch(intent);
        });

    }

    private void gpxParser(Uri uri) {
        GPXParser parser = new GPXParser(); // consider injection
        Gpx parsedGpx = null;
        try {
            InputStream in = getContext().getContentResolver().openInputStream(uri);
            parsedGpx = parser.parse(in); // consider using a background thread
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }


        if (parsedGpx == null) {
            // error parsing track
        } else {

            //Log.d("GpxParserRoute: ", parsedGpx.getRoutes().toString());
            //Log.d("GpxParserTrack: ", parsedGpx.getTracks().toString());
            //Log.d("GpxParserWayPointes: ", parsedGpx.getWayPoints().toString());

            //List<TrackPoint> trackPointsList = null;
            List<Route> routeList;
            List<TrackPoint> trackList;
            List<WayPoint> waypointList;



            if(parsedGpx.getRoutes() != null && parsedGpx.getRoutes().size() != 0){
                routeList = parsedGpx.getRoutes();
                System.out.println(routeList);
                List<RoutePoint> routePoints= routeList.get(0).getRoutePoints();
                for(RoutePoint r: routePoints){
                    System.out.println(r.getLatitude() + " " + r.getLongitude());
                }
            }else if(parsedGpx.getTracks() != null && parsedGpx.getTracks().size() != 0){
                trackList = parsedGpx.getTracks().get(0).getTrackSegments().get(0).getTrackPoints();
                for(TrackPoint t: trackList){
                    System.out.println(t.getLatitude() + " " + t.getLongitude());
                }
            }else if(parsedGpx.getWayPoints() != null && parsedGpx.getWayPoints().size() != 0){
                waypointList = parsedGpx.getWayPoints();
                for(WayPoint w: waypointList){
                    System.out.println(w.getLatitude() + " " + w.getLongitude());
                }
            }





        }

    }

    private void testQuery(String milano) {

        SentieroRequest sentieroRequest = new SentieroRequest();
        sentieroRequest.getSentieriByQuery(milano, null, null, null, new SentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {

            }

            @Override
            public void onSuccessList(List<Sentiero> sentieri) {
                Log.d("dfjngdfngkndfkgdf", sentieri.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("fdnkgdfnklgfd", throwable.toString());
            }
        });

    }

    private void signOut() {
        progressDialog();
        if(Constants.LOGIN==2){
            safeLogOut();
        }else{
            RxAmplify.Auth.signOut(AuthSignOutOptions.builder().globalSignOut(true).build())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                Log.i("AuthQuickstart", "Signed out globally");
                                safeLogOut();
                            },
                            error -> Log.e("AuthQuickstart", error.toString())
                    );
        }

    }

    private void safeLogOut(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri gpxSelected = data.getData();
                    gpxParser(gpxSelected);
                }
            });

    public void progressDialog(){
        runOnUiThread(() -> {
            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(false);
            pDialog.show();
        });
    }

}