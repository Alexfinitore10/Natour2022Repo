package com.INGSW.NaTour.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Presenter.BottomNavigationPresenter;
import com.INGSW.NaTour.R;
import com.INGSW.NaTour.View.Fragment.EmailFragment;
import com.INGSW.NaTour.View.Fragment.HomepageFragment;

import com.INGSW.NaTour.View.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class BottomNavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;//giusto
    BottomNavigationPresenter bottomNavigationPresenter;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment current = HomepageFragment.getInstance();

    private final static String TAG = "BottomNavigationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bottomNavigationPresenter = new BottomNavigationPresenter(this);

        if(Constants.LOGIN!=2){
            new Handler().postDelayed(() -> bottomNavigationPresenter.getUserByUsername(), 1000 );
        }

        fragmentManager.beginTransaction().add(R.id.frameLayout, EmailFragment.getInstance(), "3").hide(EmailFragment.getInstance()).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, ProfileFragment.getInstance(), "2").hide(ProfileFragment.getInstance()).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, HomepageFragment.getInstance(), "1").commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.getMenu().findItem(R.id.item5_email).setVisible(false);

        new Handler().postDelayed((Runnable) () -> {
            if(Constants.LOGIN==3)//Admin
                bottomNavigationView.getMenu().findItem(R.id.item5_email).setVisible(true);
        },1500);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d(TAG, "Ha premuto sull'item: "+ item.getItemId());
            switch (item.getItemId()){
                case R.id.item1_home:
                    Log.d(TAG,"Passaggio HomeFragment");
                    fragmentManager.beginTransaction().hide(current).show(HomepageFragment.getInstance()).commit();
                    current = HomepageFragment.getInstance();
                    break;
                case R.id.item2_gps:
                    Log.d(TAG,"Passaggio GPSFragment");
                    //replaceFragment();
                    break;
                case R.id.item3_message:
                    Log.d(TAG,"Passaggio MessageFragment");
                    //replaceFragment();
                    break;
                case R.id.item4_profile:
                    Log.d(TAG,"Passaggio ProfileFragment");
                    fragmentManager.beginTransaction().hide(current).show(ProfileFragment.getInstance()).commit();
                    current = ProfileFragment.getInstance();
                    break;
                case R.id.item5_email:
                    Log.d(TAG,"Passaggio EmailFragment");
                    fragmentManager.beginTransaction().hide(current).show(EmailFragment.getInstance()).commit();
                    current = EmailFragment.getInstance();
                    break;
            }
            return true;
        });


    }
/*
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

       // if(current == null){
        //    fragmentTransaction.replace(R.id.frameLayout, fragment);
        //    fragmentTransaction.commit();
         //   current = fragment;
       // }else{
            //Log.d("Fragment", "RICCHIONE SONO SU: " + current + " E VOGLIO -> " +fragment);
            fragmentTransaction.hide(current).show(fragment).commit();
        //}


        //fragmentTransaction.hide(current).show(fragment).commit();

        //fragmentManager.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
    }*/

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Tasto Back premuto");
        new MaterialAlertDialogBuilder(this)
            .setTitle("Uscire dall'App?")
            .setMessage("Vuoi uscire dall'App?")
            .setPositiveButton("SÌ", (dialogInterface, i) -> {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            })
            .setNegativeButton("NO", (dialogInterface, i) -> {})
            .show();
    }


}
