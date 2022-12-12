/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.View.Fragment;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.FotoPercorso;
import com.INGSW.NaTour.Presenter.SentieroPresenter;
import com.INGSW.NaTour.R;
import com.INGSW.NaTour.View.Activity.SentieroActivity;
import com.INGSW.NaTour.View.Adapter.FotoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SentieroFotoFragment extends Fragment {

    private static final String TAG = "SentieroFotoFragment";
    private SentieroPresenter sentieroPresenter;
    private RecyclerView recyclerView;
    public FotoAdapter fotoAdapter;
    private ArrayList<FotoPercorso> fotos;
    private FloatingActionButton FABFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"onCreateView");
        return inflater.inflate(R.layout.fragment_percorso_foto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated");

        sentieroPresenter = ((SentieroActivity)getActivity()).informationFragmentToPresenter(this);
        Log.d(TAG,"Creato SentieroPresenter" + sentieroPresenter);

        FABFoto = view.findViewById(R.id.floatingActionButtonFoto);
        recyclerView = view.findViewById(R.id.recyclerviewFoto);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        fotos = new ArrayList<>();
        fotoAdapter = new FotoAdapter(getContext(),fotos);
        recyclerView.setAdapter(fotoAdapter);

        if(Constants.LOGIN==2){
            FABFoto.setVisibility(View.INVISIBLE);
        }
        sentieroPresenter.getFoto();

        FABFoto.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            someActivityResultLauncher.launch(intent);
        });

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageSelected = data.getData();
                    sentieroPresenter.uploadFoto(imageSelected);
                }
            });

    public ArrayList<FotoPercorso> getFotos() {
        return fotos;
    }

    public void showError(String text) {
        runOnUiThread(() ->
                new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Errore")
                        .setContentText(text)
                        .show());
    }
}