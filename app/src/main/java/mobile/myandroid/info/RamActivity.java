package mobile.myandroid.info;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class RamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtRamCap = (TextView) findViewById(R.id.txt_ram_info);
        txtRamCap.setText(getAvailableMemory()+"GB");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private double getAvailableMemory () {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double totalMem = ((double)mi.totalMem)/(1024*1024*1024);

        return (Math.round(totalMem * 10.0)) / 10.0;
    }
}
