package mobile.myandroid.info;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class MemoryActivity extends AppCompatActivity {
    private static final String ERROR = "ERROR";
    private static int blockSize = 0;
    private static StatFs internalStat = null;
    private static StatFs externalStat = null;
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

        pbPhone.setMax(getTotalInternalMemorySizeInGB());
        pbPhone.setProgress(getAvailInternalMemorySizeInGB());
        txtPhoneMemInfo.setText(getTotalInternalMemorySize());

        if (hasExternalMemory()) {
            pbSdcard.setMax(getTotalExternalMemorySizeGB());
            pbSdcard.setProgress(getAvailableExternalMemorySizeGB());
            txtSdCardInfo.setText(getTotalExternalMemorySize());
        } else {
            pbSdcard.setMax(0);
            pbSdcard.setProgress(0);
            txtSdCardInfo.setText(R.string.no_sdcard);
        }
    }

    private StatFs getInternalStat() {
        if (internalStat == null) {
            File path = Environment.getDataDirectory();
            internalStat = new StatFs(path.getPath());
        }
        return internalStat;
    }
    private StatFs getExternalStat() {
        if (externalStat == null) {
            File path = Environment.getExternalStorageDirectory();
            externalStat = new StatFs(path.getPath());
        }
        return externalStat;
    }

    private int getAvailInternalMemorySizeInGB() {
        StatFs statFs = getInternalStat();
        long blockSize = 0;
        long availBlock = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            availBlock = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            availBlock = statFs.getAvailableBlocks();
        }
        return formatGB(blockSize*availBlock);
    }
    private int getTotalInternalMemorySizeInGB() {
        StatFs statFs = getInternalStat();
        long blockSize = 0;
        long totalBlocks = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
        }
        return formatGB(blockSize*totalBlocks);
    }

    private int getAvailableExternalMemorySizeGB() {
        StatFs statFs = getExternalStat();
        long blockSize = 0;
        long availBlock = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            availBlock = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            availBlock = statFs.getAvailableBlocks();
        }
        return formatGB(blockSize*availBlock);
    }
    private int getTotalExternalMemorySizeGB() {
        StatFs statFs = getExternalStat();
        long blockSize = 0;
        long totalBlocks = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
        }
        return formatGB(blockSize * totalBlocks);
    }

    public boolean hasExternalMemory() {
        return Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public String getAvailableExternalMemorySize() {
        if (hasExternalMemory()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public String getTotalExternalMemorySize() {
        if (hasExternalMemory()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return ERROR;
        }
    }

    public int formatGB(long size) {
        return (int)(size/=(1024*1024*1024));
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /=1024;
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
