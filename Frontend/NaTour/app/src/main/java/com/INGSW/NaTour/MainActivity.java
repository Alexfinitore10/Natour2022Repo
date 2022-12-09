package com.INGSW.NaTour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.INGSW.NaTour.Extra.Constants;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.INGSW.NaTour.View.Activity.BottomNavigationActivity;
import com.INGSW.NaTour.View.Activity.LogInActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAnalytics analytics;

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreata");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.maintenanceTextView);
        imageView = findViewById(R.id.maintenanceImageView);

        try {
            if(isInternetAvailable()){
                Log.d(TAG,"Internet disponibile e backend attivo");
                switchActivity();
            } else {
                textView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
                Log.e(TAG,"Internet non disponibile oppure backend non attivo");
            }
        } catch (Exception e) {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Log.e(TAG,e.toString());
        }

    }

    public void switchActivity(){
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

    public boolean isInternetAvailable() throws IOException, InterruptedException {
        AtomicReference<String> value = new AtomicReference<>("");
        Thread t = new Thread(() -> {
            try {
                System.out.println("http://" + Constants.IP_ADDRESS + ":" + Constants.PORT);
                URL url = new URL("http://" + Constants.IP_ADDRESS + ":" + Constants.PORT);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.setReadTimeout(30);
                //urlConnection.setConnectTimeout(30);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                urlConnection.disconnect();
                Scanner s = new Scanner(in).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                value.set(result);
            }catch (Exception e){
                System.out.println(e);
            }
        });
        t.start();
        t.join();

        if(value.toString().equals("")){
            return false;
        }else {
            return true;
        }

    }

}