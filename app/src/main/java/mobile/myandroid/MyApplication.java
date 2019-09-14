package mobile.myandroid;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * My Android
 * ${PACKAGE_NAME}
 * Created by beou on 27/10/2015.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, "ca-app-pub-7036763487648714~7050910156");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
