package mobile.myandroid;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/**
 * My Android
 * ${PACKAGE_NAME}
 * Created by beou on 27/10/2015.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize MobileAds
        MobileAds.initialize(this, initializationStatus -> {
            // Log the initialization status
            Log.d(TAG, "MobileAds initialization complete: " + initializationStatus);
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
