package com.INGSW.NaTour.Extra;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.amplifyframework.rx.RxAmplify;

public class AmplifyApp extends Application {

    private static final String TAG = "AmplifyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            RxAmplify.addPlugin(new AWSDataStorePlugin());
            RxAmplify.addPlugin(new AWSApiPlugin());
            RxAmplify.addPlugin(new AWSCognitoAuthPlugin());
            RxAmplify.addPlugin(new AWSS3StoragePlugin());
            RxAmplify.configure(getApplicationContext());
            Log.i(TAG, "Configurato");
        } catch (AmplifyException e) {
            Log.e(TAG + "AmplifyException", "Configure failed: " + e.getMessage(), e);
        }
    }
}
