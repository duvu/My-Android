package mobile.myandroid.storage;

import android.os.StatFs;

/**
 * Created by beou on 27/10/2015.
 */
public class StorageInfo {

    public final String path;
    public final boolean internal;
    public final boolean readonly;
    public final int display_number;

    StorageInfo(String path, boolean internal, boolean readonly, int display_number) {
        this.path = path;
        this.internal = internal;
        this.readonly = readonly;
        this.display_number = display_number;
    }

    public long getAvailSize () {
        StatFs statFs = new StatFs(path);
        long blockSize = 0;
        long availBlock = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            availBlock = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            availBlock = statFs.getAvailableBlocks();
        }
        return blockSize*availBlock;
    }
    public long getTotalSize () {
        StatFs statFs = new StatFs(path);
        long blockSize = 0;
        long totalBlock = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalBlock = statFs.getBlockCountLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlock = statFs.getBlockCount();
        }
        return blockSize * totalBlock;
    }

    public String getDisplayName() {
        StringBuilder res = new StringBuilder();
        if (internal) {
            res.append("Internal SD card");
        } else if (display_number > 1) {
            res.append("SD card " + display_number);
        } else {
            res.append("SD card");
        }
        if (readonly) {
            res.append(" (Read only)");
        }
        return res.toString();
    }
}
