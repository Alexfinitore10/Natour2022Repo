package com.INGSW.NaTour.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SentieroAdapter extends RecyclerView.Adapter<SentieroAdapter.ItinerarioHolder> {

    private static final String TAG = "SentieroAdapter";
    private Context context2;
    private ArrayList<Sentiero> itinerari;

    public SentieroAdapter(Context context2, ArrayList<Sentiero> itinerari) {
        this.context2 = context2;
        this.itinerari = itinerari;
    }

    class ItinerarioHolder extends  RecyclerView.ViewHolder{
        private TextView txtNameCard,txtSubnameCard,txtDescriptionCard, txtDifficultCard, txtTimeCard, txtDisabileCard;
        private AppCompatImageView map, propic;

        ItinerarioHolder(View itemView){
            super(itemView);
            txtNameCard = itemView.findViewById(R.id.NameCard);
            txtSubnameCard = itemView.findViewById(R.id.SubnameCard);
            txtDescriptionCard = itemView.findViewById(R.id.DescriptionCard);
            txtDifficultCard = itemView.findViewById(R.id.difficultyFragment);
            txtTimeCard = itemView.findViewById(R.id.timeFragment);
            txtDisabileCard = itemView.findViewById(R.id.disableFragment);
            map = itemView.findViewById(R.id.map);
            propic = itemView.findViewById(R.id.propic);
        }

        void setDetails(Sentiero sentiero){
            Log.d(TAG, "setDetail : " + sentiero.toString());
            convertDataSentieroToUi(sentiero.getDifficolta(), sentiero.getDurata());
            txtSubnameCard.setText(sentiero.getLocalità());
            txtDescriptionCard.setText(sentiero.getNome());
            txtNameCard.setText(sentiero.getUtente().getUsername());
            if(sentiero.isDisabile() == false){
                txtDisabileCard.setVisibility(View.INVISIBLE);
            }else {
                txtDisabileCard.setVisibility(View.VISIBLE);
            }

            if(sentiero.getDifficolta()==1){
                txtDifficultCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_facile, 0, 0, 0);
            }else if(sentiero.getDifficolta()==2){
                txtDifficultCard.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_medio, 0, 0, 0);
            }


            Glide.with(context2).load(sentiero.getImmagine()).into(map);
            Glide.with(context2).load(sentiero.getUtente().getPropic()).circleCrop().into(propic);
        }

        void convertDataSentieroToUi(int difficolta, int durata){
            String format, diff;

            switch (difficolta){
                case 1:
                    diff = "Facile";
                    break;
                case 2:
                    diff = "Medio";
                    break;
                case 3:
                    diff = "Difficile";
                    break;
                default: diff = null;
            }
            txtDifficultCard.setText(diff);

            if(durata>0){
                int hour = durata/60;
                int minute = durata - (hour*60);
                format = String.format("%02d:%02d", hour, minute);
            }else format=null;

            txtTimeCard.setText(format);
        }
    }


    @NonNull
    @Override
    public ItinerarioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context2).inflate(R.layout.card,parent,false);
        return new ItinerarioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItinerarioHolder holder, int position) {
        Sentiero itinerario = itinerari.get(position);
        holder.setDetails(itinerario);
    }

    @Override
    public int getItemCount() {
        return itinerari.size();
    }

    private String convertDifficulty(int diff){
        switch (diff){
            case 1:
                return "Facile";
            case 2:
                return "Medio";
            case 3:
                return "Difficile";
        }
        return null;
    }

    private String converTime(int time){
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(time));
        long remainMinutes = time - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, remainMinutes);
    }

}
