
package com.INGSW.NaTour.View.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.rx.RxAmplify;
import com.bumptech.glide.Glide;
import com.INGSW.NaTour.MainActivity;
import com.INGSW.NaTour.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Homepage1Activity extends AppCompatActivity {

    private static final String TAG = "Homepage1Activity";

    TextView ciao;
    EditText AnticoCappato_EditTextsottolaImgView;
    ImageView fotografia;
    ShimmerFrameLayout shimmer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage1);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();



        Button Upload = findViewById(R.id.button9);
        Button Foto = findViewById(R.id.button10);
        AnticoCappato_EditTextsottolaImgView = findViewById(R.id.editTextTextPersonName);
        fotografia = findViewById(R.id.imageView);
        shimmer1= (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);


        ciao = findViewById(R.id.textView);

        Foto.setOnClickListener(v -> {
            GetImage();
        });





        findViewById(R.id.button6).setOnClickListener(v -> {
            RxAmplify.Auth.signOut(AuthSignOutOptions.builder().globalSignOut(true).build())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.i("AuthQuickstart", "Signed out globally");
                            startActivity(new Intent(this, MainActivity.class));
                        },
                        error -> Log.e("AuthQuickstart", error.toString())
                );
        });


        Upload.setOnClickListener(v -> {
            PieckPhoto();
            //uploadFile();
        });
    }

    public void GetImage()
    {
        String S3Photo = AnticoCappato_EditTextsottolaImgView.getText().toString();
        Amplify.Storage.getUrl(
                S3Photo,
                result -> {
                    Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
                    Picassocall(result.getUrl());
                    } ,
                error -> Log.e("MyAmplifyApp", "URL generation failure", error)
        );
    }

    private void Picassocall(URL image) {
        String url = "https://natourfoto131415-astudio.s3.us-east-2.amazonaws.com/public/ExampleKey?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEAAaCXVzLWVhc3QtMiJGMEQCIDRaqjkOb8forAIUKxRPZMzdUMfcZEIUNBoCyofl9NwZAiBCa%2FOh3ENYhZU%2FNdork7DVUfGyOIsnZ1yH%2BVHN062jRirEBAhJEAEaDDYyODQ0ODIyODYwNSIMwm%2FLpjQHJpa1q5KKKqEEtdTT%2FMnB%2Fq0valFPyiaWO0Gpxb8iKaajFjXBywN4z0a0iy55YfLt4ZJVOxgkAK6fjalNuha5pNviDUbq5LUnwVeWovIrS%2FL3%2Buc%2F6Jci%2BYplD%2FoH8VtIq1diPVulwbroqcdnhwz8o1VE7hLdcwTGtOX%2Bp8Sd6Y7L%2FJYd%2BSDkrUZzH6d99xTAyI50%2F7pYjPMB9bAn2KbGXt55GtxRZxNH%2B54lutotaOyYeZd9jtB00X6mnqTq%2B9r1LWDC0Tm9zsVJBDJOjJMN8BkAlz6ZxlgQgugYWvYb30rqlw%2B7X66G%2BQHBodUiA9tvbjZwZZx1eIb4tK8iQOsVxv7vELBhsm10oIsRyRrfeUTmbCpg2up2SpBrW63hVJ1gbrOYXaavcXCDUkCs08ZcpIr5754tvCQj4PNs5WJyQFeZzbD404Q49o8YS7XRy%2FkirSJTAWc%2Fved5wU8apmP7E1tdC2qRX78UgqAXg4zutT7j83aA17Fb9Q%2BaiI4E8rIZQo144uigkUl1l%2FPpgYIMAkzZoh%2B7Shp246pjphIlOXbWTFoIrKxCdouPS2qeSUgQuXKHn1HtUyLTu4TYpcw1lPZ9fi68WCp9gcQdSIBeRD%2B1%2FMmQwwSslPlhsoVmAcoDbQZu50CIig%2BE48zmsyHqck%2FDgKW2p3YZB8w2lKLHWDWZdOxdeWP0djXMcWSaoRmRNjWQkZa3KyeAuna9mMAlK7KFPhIz2CJAEgcw7pmakAY6hgLvkp9Sta8yikc5qzBO1m39uJ%2Fh%2B3Ca3lRtXqOvU%2B2FYUHAnmbL877iwUl1tO%2B4iReu7k5HgUWQ1Dse9vcribnwJAk04J%2BgWqpt9%2BvPXqgVf%2FRB0CGcbIPx5aEweIZrZRPgc3vR397uIMTqWEIpKr855IeIE%2FjoKGov2zXyvVN8T00byNOPzcNrH50L2Zm%2BR1EdglcwIlhPj9n4Waju0J1rl4JX1MqYqFbZpr6FhpYJb%2BpqGb8k5mygZjAa87G4ks9PR%2F3wJ7MCd8fOFIGBa%2FJh5gvz2dLbsIU0o9TMeTF4%2FxIxickZyNXg63NIDrx99UEoU%2BT41LOgdgTEumo44CC1GuB3t4O8&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220211T162110Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604799&X-Amz-Credential=ASIAZEUTO6T6TRKR7F2R%2F20220211%2Fus-east-2%2Fs3%2Faws4_request&X-Amz-Signature=0526879837eb8aef7a8c8c111f69deb536b825a0d1d6aaf15578307086b54ba6.png";
        runOnUiThread(() -> {
            //shimmer1.startShimmerAnimation();
            //Picasso.with(getApplicationContext()).load(image.toString()).into(fotografia);
            Glide.with(getApplicationContext()).load(image.toString()).into(fotografia);
            //shimmer1.stopShimmerAnimation();
        });

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri imageSelected = data.getData();
                    uploadInputStream(imageSelected);
                }
            });

    private void uploadInputStream(Uri uri) {

        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(uri);
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
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
            Log.d("NomeFoto", nameFoto);

            String imageUpload = enc + nameFoto;

            Amplify.Storage.uploadInputStream(
                    imageUpload,
                    exampleInputStream,
                    result ->{
                        Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());
                        pDialog.setTitleText("Loading Complete");
                        pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    } ,
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }  catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }
    }

    public void PieckPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //Intent intent = new Intent(this, SomeActivity.class);
        someActivityResultLauncher.launch(intent);

    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "Tasto Back premuto");
        new MaterialAlertDialogBuilder(this)
               .setCancelable(false)
               .setTitle("Uscire dall'App?")
               .setMessage("Vuoi uscire dall'App?")
               .setPositiveButton("INVIA", (dialogInterface, i) -> {
                   Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                   homeIntent.addCategory( Intent.CATEGORY_HOME );
                   homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(homeIntent);
               })
               .setNegativeButton("NO", (dialogInterface, i) -> {})
               .show();
    }
}

