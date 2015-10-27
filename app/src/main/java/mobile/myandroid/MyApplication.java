package mobile.myandroid;

import android.app.Application;
import android.os.Build;

/**
 * My Android
 * ${PACKAGE_NAME}
 * Created by beou on 27/10/2015.
 */
public class MyApplication extends Application {
    private static MyApplication instance = null;

    private String manufacturer;
    private String model;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //-- init some information about the device
        //-- Manufacturer and Model
        this.manufacturer = Build.MANUFACTURER;
        this.model = Build.MODEL;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //--getter/setter
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
