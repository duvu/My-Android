package mobile.myandroid.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import mobile.myandroid.R;
import mobile.myandroid.storage.StorageInfo;
import mobile.myandroid.storage.StorageUtils;
import mobile.myandroid.util.StringTool;

/**
 * My Android
 * ${PACKAGE_NAME}
 * Created by beou on 26/10/2015.
 */
public class MemoryActivity extends AppCompatActivity {
    private static final String ERROR = "ERROR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        pbPhone.setMax((int) StringTool.formatGB(totalInternal));
        pbPhone.setProgress((int) StringTool.formatGB(availInternal));
        txtPhoneMemInfo.setText(StringTool.formatSize(totalInternal));
        if (totalExternal > 0) {
            pbSdcard.setMax((int) StringTool.formatGB(totalExternal));
            pbSdcard.setProgress((int) StringTool.formatGB(availExternal));
            txtSdCardInfo.setText(StringTool.formatSize(totalExternal));
        } else {
            pbSdcard.setMax(0);
            pbSdcard.setProgress(0);
            txtSdCardInfo.setText(getString(R.string.no_sdcard));
        }
    }


}
