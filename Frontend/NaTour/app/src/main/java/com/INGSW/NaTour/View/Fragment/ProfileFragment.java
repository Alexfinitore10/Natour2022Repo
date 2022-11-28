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
                                Log.i(TAG, "Signed out globally");
                                safeLogOut();
                            },
                            error -> Log.e(TAG, error.toString())
                    );
        }

    }

    private void safeLogOut(){
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }


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