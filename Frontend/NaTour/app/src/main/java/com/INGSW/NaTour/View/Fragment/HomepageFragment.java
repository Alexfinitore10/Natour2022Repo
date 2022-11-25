package com.INGSW.NaTour.View.Fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Presenter.HomepagePresenter;
import com.INGSW.NaTour.R;
import com.INGSW.NaTour.View.Activity.InsertActivity;
import com.INGSW.NaTour.View.Activity.SentieroActivity;
import com.INGSW.NaTour.View.Adapter.RecyclerItemClickListener;
import com.INGSW.NaTour.View.Adapter.SentieroAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomepageFragment extends Fragment {

    private static HomepageFragment homepageFragment;
    HomepagePresenter homepagePresenter;

    private static final String TAG = "HomepageFragment";

    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeContainer;
    private SentieroAdapter adapter;
    private ArrayList<Sentiero> itinerari;
    private Context context;
    private Button buttonSearchBar;
    private MaterialAlertDialogBuilder searchDialog;


    Integer hour, minute;
    String diff, dis, loc;

    private HomepageFragment() {/*Required empty public constructor*/}

    public static HomepageFragment getInstance(){
        if(homepageFragment == null){
            Log.d(TAG, "Home fragment non presente, creazione in corso");
            homepageFragment = new HomepageFragment();
        }

        Log.d(TAG, "Invio homefragment");
        return homepageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homepagePresenter = new HomepagePresenter(homepageFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.recyclerViewHome);
        swipeContainer = getView().findViewById(R.id.swipeContainer);
        buttonSearchBar = getView().findViewById(R.id.SearchButton);
        floatingActionButton = getView().findViewById(R.id.buttonCreate);

        if(Constants.LOGIN==2){
            floatingActionButton.setVisibility(View.INVISIBLE);
        }

        context = getContext();
        itinerari = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new SentieroAdapter(getContext(),itinerari);
        recyclerView.setAdapter(adapter);

        homepagePresenter.getItinerari();


        swipeContainer.setOnRefreshListener(() -> {
            homepagePresenter.getItinerari();
            swipeContainer.setRefreshing(false);
        });

        buttonSearchBar.setOnClickListener(view12 -> {
            searchDialog();
        });

        floatingActionButton.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), InsertActivity.class));
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) throws IOException {
                Log.d(TAG, "Pressed " + itinerari.get(position).toString());
                Intent i = new Intent(getActivity(), SentieroActivity.class);
                i.putExtra("Itinerario",itinerari.get(position));
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if(Constants.LOGIN==3)
                    remove(position);
            }
        }));

    }

    private void remove(int position){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Vuoi cancellare questo sentiero?")
                .setPositiveButton("Si", (dialogInterface, i) -> {
                    Log.d(TAG, "Eliminazione percorso");
                    homepagePresenter.delete(itinerari.get(position).getId());
                    itinerari.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void searchDialog() {
        searchDialog = new MaterialAlertDialogBuilder(getActivity());

        final View layout = View.inflate(context, R.layout.dialog_search, null);
        SearchableSpinner searchableSpinner = layout.findViewById(R.id.searchSpinnerDialog);

        Resources res = getResources();
        String[] comuni = res.getStringArray(R.array.comuni);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,comuni);
        searchableSpinner.setAdapter(arrayAdapter);

        searchableSpinner.setTitle("Località");
        searchableSpinner.setPositiveButton("Ok");

        TextInputEditText timePicker = layout.findViewById(R.id.editTextTimeDialog);

        timePicker.setOnClickListener(view -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setTitleText("Seleziona durata")
                    .build();
            materialTimePicker.show(getActivity().getSupportFragmentManager(),"timepicker");

            materialTimePicker.addOnPositiveButtonClickListener(view1 -> {
                hour = materialTimePicker.getHour();
                minute = materialTimePicker.getMinute();
                timePicker.setText(hour + ":" + minute);
            });
        });

        ChipGroup chipGroupDif = layout.findViewById(R.id.chipGroupDifDialog);
        ChipGroup chipGroupDis = layout.findViewById(R.id.chipGroupDisDialog);


        searchDialog.setView(layout);

        searchDialog.setTitle("Cerca Percorso: ");
        searchDialog.setPositiveButton("Cerca", (dialogInterface, i) -> {
            loc = (String) searchableSpinner.getSelectedItem();
            Chip chipDif = layout.findViewById(chipGroupDif.getCheckedChipId());
            if(chipDif!=null)
                diff = String.valueOf(chipDif.getText());
            Chip chipDis = layout.findViewById(chipGroupDis.getCheckedChipId());
            if(chipDis!=null)
                dis = String.valueOf(chipDis.getText());
            homepagePresenter.search(hour,minute,diff,dis,loc);
            resetValue();
        });
        searchDialog.setNegativeButton("Annulla", (dialogInterface, i) -> {});

        searchDialog.show();
    }


    public void updateRecycler(List<Sentiero> sentieri) {
        itinerari.clear();
        itinerari.addAll(sentieri);
        adapter.notifyDataSetChanged();
        Log.d(TAG, "RecyclerView aggiornata");
    }

    private void resetValue(){
        hour=null;
        minute=null;
        diff=null;
        dis=null;
        loc=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume e update sentieri");
        homepagePresenter.getItinerari();
    }

    public void showError(String text) {
        runOnUiThread(() ->
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(text)
                        .show());
    }

}