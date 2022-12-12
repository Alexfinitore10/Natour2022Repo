/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.INGSW.NaTour.Model.FotoPercorso;
import com.INGSW.NaTour.R;

import java.net.SocketException;
import java.util.ArrayList;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.FotoHolder> {

    private Context context;
    private ArrayList<FotoPercorso> foto;

    private final static String TAG = "FotoAdapter";

    public FotoAdapter(Context context, ArrayList<FotoPercorso> foto) {
        this.context = context;
        this.foto = foto;
    }

    public class FotoHolder extends RecyclerView.ViewHolder {
        private ImageView imageRecyclerView;

        FotoHolder(@NonNull View itemView) {
            super(itemView);
            imageRecyclerView = itemView.findViewById(R.id.imageViewRecycler);
        }

        void setDetails(FotoPercorso fotoPercorso){
            Log.d(TAG,"Glide sta per scaricare questa foto: " + fotoPercorso.getUrl());

            new Handler().post(() -> {
                try{
                    Glide.with(context).load(fotoPercorso.getUrl()).into(imageRecyclerView);
                }catch (Exception e){
                    Log.e(TAG, "Glide Socket Exception" + e.toString());
                }
            });
        }

    }

    @NonNull
    @Override
    public FotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo,parent,false);
        return new FotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoHolder holder, int position) {
        FotoPercorso fotoPercorso = foto.get(position);
        holder.setDetails(fotoPercorso);
    }

    @Override
    public int getItemCount() {
        return foto.size();
    }


}
