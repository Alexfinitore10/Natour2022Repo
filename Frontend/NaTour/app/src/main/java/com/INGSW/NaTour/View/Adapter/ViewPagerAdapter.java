package com.INGSW.NaTour.View.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.INGSW.NaTour.View.Fragment.SentieroFotoFragment;
import com.INGSW.NaTour.View.Fragment.SentieroInformazioniFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "ViewPagerAdapter";

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                Log.d(TAG, "Ho ricreato il percorso");
                return new SentieroInformazioniFragment();
            case 1:
                return new SentieroFotoFragment();
            default:
                Log.d(TAG, "Ho ricreato il percorso");
                return new SentieroInformazioniFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
