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

        pbPhone.setMax((int) formatGB(totalInternal));
        pbPhone.setProgress((int) formatGB(availInternal));
        txtPhoneMemInfo.setText(formatSize(totalInternal));
        if (totalExternal > 0) {
            pbSdcard.setMax((int) formatGB(totalExternal));
            pbSdcard.setProgress((int) formatGB(availExternal));
            txtSdCardInfo.setText(formatSize(totalExternal));
        } else {
            pbSdcard.setMax(0);
            pbSdcard.setProgress(0);
            txtSdCardInfo.setText(getString(R.string.no_sdcard));
        }
    }

    public double formatGB(long size) {
        return (double)(size/=(1024.0*1024.0*1024.0));
    }

    public static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024.0) {
            suffix = "KB";
            size /= 1024.0;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024.0;
                if (size >= 1024) {
                    suffix = "GB";
                    size /=1024.0;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
}
