/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Presenter;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.amplifyframework.rx.RxAmplify;
import com.amplifyframework.rx.RxStorageBinding;
import com.amplifyframework.storage.result.StorageUploadInputStreamResult;
import com.INGSW.NaTour.Extra.Constants;
import com.INGSW.NaTour.Model.DTO.FotoPercorsoDTO;
import com.INGSW.NaTour.Model.DTO.OpinioneDTO;
import com.INGSW.NaTour.Model.FotoPercorso;
import com.INGSW.NaTour.Model.MapPoint;
import com.INGSW.NaTour.Model.Sentiero;
import com.INGSW.NaTour.Retrofit.CallbackInterface.FotoPercorsoCallback;
import com.INGSW.NaTour.Retrofit.CallbackInterface.OpinioneCallback;
import com.INGSW.NaTour.Retrofit.CallbackInterface.TracciatoSentieroCallback;
import com.INGSW.NaTour.Retrofit.Request.FotoPercorsoRequest;
import com.INGSW.NaTour.Retrofit.Request.OpinioneRequest;
import com.INGSW.NaTour.Retrofit.Request.SentieroRequest;
import com.INGSW.NaTour.View.Activity.SentieroActivity;
import com.INGSW.NaTour.View.Fragment.SentieroFotoFragment;
import com.INGSW.NaTour.View.Fragment.SentieroInformazioniFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.CRC32;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SentieroPresenter {

    private static final String TAG = "SentieroPresenter";

    private SentieroActivity sentieroActivity;
    private SentieroFotoFragment sentieroFotoFragment;
    private SentieroInformazioniFragment sentieroInformazioniFragment;

    private Sentiero sentiero;

    public SentieroPresenter(SentieroActivity sentieroActivity, Sentiero sentiero) {
        this.sentieroActivity = sentieroActivity;
        this.sentiero = sentiero;
    }

    public SentieroPresenter() {}

    public void getFoto() {
        FotoPercorsoRequest fotoPercorsoRequest = new FotoPercorsoRequest();
        fotoPercorsoRequest.getPhotoBySentiero(sentiero.getId(), new FotoPercorsoCallback() {
            @Override
            public void onSuccessResponse(boolean response) {

            }

            @Override
            public void onSuccessList(List<FotoPercorso> photos) {
                Log.d(TAG, photos.toString());
                GetImage(photos);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG,throwable.toString());
            }
        });
    }

    public void GetImage(List<FotoPercorso> fotos){
        Log.d(TAG, "Get image arrivato");
        sentieroFotoFragment.getFotos().clear();
        for (FotoPercorso foto: fotos){
            String S3Photo = foto.getUrl();
            RxAmplify.Storage.getUrl(S3Photo)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                                Log.i(TAG, "Successfully generated: " + result.getUrl());
                                foto.setUrl(result.getUrl().toString());
                                sentieroFotoFragment.getFotos().add(foto);
                                sentieroFotoFragment.fotoAdapter.notifyDataSetChanged();
                            },
                            error -> Log.e("MyAmplifyApp", "URL generation failure", error)
                    );
        }
        sentieroFotoFragment.fotoAdapter.notifyDataSetChanged();
    }

    public void getTracciato(){
        Log.d(TAG, "Richiesta Tracciato Sentiero " + sentiero.getId());
        SentieroRequest sentieroRequest = new SentieroRequest();
        sentieroRequest.getTracciatoBySentiero(sentiero.getId(), new TracciatoSentieroCallback() {
            @Override
            public void onSuccessResponse(boolean response) {

            }

            @Override
            public void onSuccessList(List<MapPoint> tracciato) {
                Log.d(TAG, "Ricevuto tracciato: ");
                sentiero.setTracciato(tracciato);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "Impossibile recuperare tracciato");
            }
        });
    }

    public void uploadFoto(Uri uri) {
        try {
            InputStream exampleInputStream = sentieroFotoFragment.getContext().getContentResolver().openInputStream(uri);
            SweetAlertDialog pDialog = new SweetAlertDialog(sentieroFotoFragment.getContext(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading ...");
            pDialog.setCancelable(true);
            pDialog.show();
            CRC32 crc = new CRC32();

            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
            String format = s.format(new Date());

            crc.update(format.getBytes());
            String enc = String.format("%08X", crc.getValue());

            String nameFoto = uri.getPath();
            int cut = nameFoto.lastIndexOf('/');
            if (cut != -1) {
                nameFoto = nameFoto.substring(cut + 1);
            }
            Log.d(TAG, "Nome foto: " + nameFoto);

            String imageUpload = enc + nameFoto;

            Log.d(TAG, "Immagine: " + imageUpload + exampleInputStream.toString());

            if(Constants.utente==null){
                Log.e(TAG,"Utente non presente");
                sentieroFotoFragment.showError("Errore, impossibile continuare l'operazione, riprova più tardi");
                return;
            }

            RxStorageBinding.RxProgressAwareSingleOperation<StorageUploadInputStreamResult> rxUploadOperation =
                    RxAmplify.Storage.uploadInputStream(imageUpload, exampleInputStream);

            rxUploadOperation
                    .observeResult()
                    .subscribe(
                            result ->{
                                Log.i(TAG, "Successfully uploaded: " + result.getKey());
                                insertPhoto(result.getKey());
                                pDialog.setTitleText("Loading Complete");
                                pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            },
                            error ->{
                                Log.e(TAG, "Upload failed", error);
                                pDialog.setTitleText("Error");
                                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }
                    );
        }  catch (FileNotFoundException error) {
            Log.e(TAG, "Could not find file to open for input stream.", error);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

    private void insertPhoto(String url){
        FotoPercorsoRequest fotoPercorsoRequest = new FotoPercorsoRequest();
        FotoPercorsoDTO fotoPercorsoDTO = new FotoPercorsoDTO(url, sentiero.getId(), Constants.utente.getId());
        fotoPercorsoRequest.insertPhoto(fotoPercorsoDTO, new FotoPercorsoCallback() {
            @Override
            public void onSuccessResponse(boolean response) {
                Log.d(TAG, "Inserito foto: " + response);
                getFoto();
            }

            @Override
            public void onSuccessList(List<FotoPercorso> photos) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public Sentiero getSentiero() {
        return sentiero;
    }

    public void setSentiero(Sentiero sentiero) {
        this.sentiero = sentiero;
    }

    public void setSentieroFotoFragment(SentieroFotoFragment sentieroFotoFragment) {
        this.sentieroFotoFragment = sentieroFotoFragment;
    }

    public void setSentieroInformazioniFragment(SentieroInformazioniFragment sentieroInformazioniFragment) {
        this.sentieroInformazioniFragment = sentieroInformazioniFragment;
    }

    public void createOpinion(Integer hour, Integer minute, String diff) {
        Integer durata = convertHourAndMinuteToMinute(hour,minute);
        Integer difficolta = convertDisabileToInt(diff);

        Log.d(TAG, "createOpinion: "+ durata + "-" + difficolta);
        if(Constants.utente==null){
            Log.e(TAG,"Utente non presente");
            sentieroInformazioniFragment.showError("Errore, impossibile continuare l'operazione, riprova più tardi");
            return;
        }
        OpinioneDTO opinioneDTO = new OpinioneDTO(difficolta,durata, sentiero.getId(), Constants.utente.getId());
        insertOpinion(opinioneDTO);
    }

    public Integer convertDisabileToInt(String diff){
        switch (diff){
            case "Facile":
                return 1;
            case "Medio":
                return 2;
            case "Difficile":
                return 3;
        }
        return null;
    }

    public Integer convertHourAndMinuteToMinute(Integer hour, Integer minute){
        Integer durata = null;//1

        if(hour!=null && hour >= 0 && minute != null && minute >=0){//2
            if(hour==0 && minute == 0)//3
                throw new IllegalArgumentException();//4
            durata = (hour*60)+minute;//5
        }else if(hour == null && minute==null)//6
            throw new IllegalArgumentException();//7

        if(durata==null){//8
            if(hour == null && minute >= 0){//9
                durata = minute;//10
            }else if(minute == null && hour >= 0){//11
                durata = hour*60;//12
            } else throw new IllegalArgumentException();//13
        }

        return durata;//14
    }

    public void insertOpinion(OpinioneDTO opinioneDTO){
        OpinioneRequest opinioneRequest = new OpinioneRequest();
        sentieroInformazioniFragment.showLoading();
        opinioneRequest.insertOpinione(opinioneDTO, new OpinioneCallback() {
            @Override
            public void onSuccessResponse(boolean response) {

            }

            @Override
            public void onSuccess(Sentiero sentieroUpdated) {
                Log.d(TAG,"Opinione aggiunta con successo, ecco i dati: Difficoltà: " + sentieroUpdated.getDifficolta() + "Durata: " + sentieroUpdated.getDurata());
                sentiero = sentieroUpdated;
                sentieroInformazioniFragment.showSuccess("Opinione inserita con successo, i dati sono stati reilaborati");
                sentieroInformazioniFragment.updateInformation();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "Opinione non aggiunta " + throwable);
                sentieroInformazioniFragment.showError("Errore, la tua opinione non è stata aggiunta, riprova");
            }
        });
    }

    public void updateView(Sentiero sentiero) {

        sentieroInformazioniFragment.updateSentiero(sentiero);
    }


}
