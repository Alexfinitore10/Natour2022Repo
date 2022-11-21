package com.INGSW.NaTour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.INGSW.NaTour.View.Activity.BottomNavigationActivity;
import com.INGSW.NaTour.View.Activity.LogInActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAnalytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreata");
        super.onCreate(savedInstanceState);

        analytics = FirebaseAnalytics.getInstance(this);

        AuthUser currentUser = Amplify.Auth.getCurrentUser();

        if(currentUser == null) {
            Log.d(TAG,"Utente non loggato, procedere al login");
            startActivity(new Intent(this, LogInActivity.class));
        }else {
            Log.d(TAG,"Utente loggato, procedere all'HomepageFragment");
            startActivity(new Intent(this, BottomNavigationActivity.class));
        }
    }

}