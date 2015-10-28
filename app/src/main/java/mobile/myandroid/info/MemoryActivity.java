package mobile.myandroid.info;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;
import mobile.myandroid.storage.StorageInfo;
import mobile.myandroid.storage.StorageUtils;
import mobile.myandroid.util.StringTool;

/**
 * My Android
 * ${PACKAGE_NAME}
 * Created by beou on 26/10/2015.
 */
public class MemoryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //--
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //--

        ProgressBar pbPhone = (ProgressBar) findViewById(R.id.pb_memory_phone);
        ProgressBar pbSdcard = (ProgressBar) findViewById(R.id.pb_memory_sdcard);

        TextView txtPhoneMemInfo = (TextView) findViewById(R.id.txt_phone_memory_info);
        TextView txtSdCardInfo = (TextView) findViewById(R.id.txt_sdcard_info);

        List<StorageInfo> lStorage = StorageUtils.getStorageList();
        long totalInternal = 0;
        long availInternal = 0;
        long totalExternal = 0;
        long availExternal = 0;
        for (StorageInfo storageInfo : lStorage) {
            Log.i("MemoryActivity", storageInfo.getDisplayName());
            Log.i("MemoryActivity", storageInfo.path);

            if (storageInfo.internal) {
                totalInternal += storageInfo.getTotalSize();
                availInternal += storageInfo.getAvailSize();
            } else {
                totalExternal += storageInfo.getTotalSize();
                availExternal += storageInfo.getAvailSize();
            }
        }

        pbPhone.setMax((int) StringTool.formatSizeGB(totalInternal));
        pbPhone.setProgress((int) StringTool.formatSizeGB(availInternal));
        txtPhoneMemInfo.setText(StringTool.formatSize(totalInternal));
        if (totalExternal > 0) {
            pbSdcard.setMax((int) StringTool.formatSizeGB(totalExternal));
            pbSdcard.setProgress((int) StringTool.formatSizeGB(availExternal));
            txtSdCardInfo.setText(StringTool.formatSize(totalExternal));
        } else {
            pbSdcard.setMax(0);
            pbSdcard.setProgress(0);
            txtSdCardInfo.setText(getString(R.string.no_sdcard));
        }
    }


}
