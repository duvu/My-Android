package mobile.myandroid.storage;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by beou on 27/10/2015.
 * http://stackoverflow.com/questions/5694933/find-an-external-sd-card-location
 */
public class StorageTool {
    private static final String TAG = "StorageUtils";

    public static List<StorageInfo> getStorageList() {
        List<StorageInfo> list = new ArrayList<StorageInfo>();
        String def_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        boolean def_path_internal = !Environment.isExternalStorageRemovable();
        String def_path_state = Environment.getExternalStorageState();

        boolean def_path_available = def_path_state.equals(Environment.MEDIA_MOUNTED)
                || def_path_state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean def_path_readonly = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
        BufferedReader buf_reader = null;
        try {
            HashSet<String> paths = new HashSet<String>();
            buf_reader = new BufferedReader(new FileReader("/proc/mounts"));
            String line;
            int cur_display_number = 1;
            Log.d(TAG, "/proc/mounts");
            while ((line = buf_reader.readLine()) != null) {
                Log.d(TAG, line);
                if (line.contains("vfat") ||
                        line.contains("/mnt") ||
                        line.contains("/storage")) {
                    StringTokenizer tokens = new StringTokenizer(line, " ");
                    String unused = tokens.nextToken(); //device
                    String mount_point = tokens.nextToken(); //mount point
                    if (isSymlink(mount_point) || paths.contains(mount_point)) {
                        continue;
                    }
                    unused = tokens.nextToken(); //file system
                    List<String> flags = Arrays.asList(tokens.nextToken().split(",")); //flags
                    boolean readonly = flags.contains("ro");



                    if (mount_point.equals(def_path)) {
                        paths.add(def_path);
                        list.add(0, new StorageInfo(def_path, def_path_internal, readonly, -1));
                    } else if (isBlockDevice(line)) {
                        paths.add(mount_point);
                        list.add(new StorageInfo(mount_point, false, readonly, cur_display_number++));
                    }
                }
            }

            if (!paths.contains(def_path) && def_path_available) {
                list.add(0, new StorageInfo(def_path, def_path_internal, def_path_readonly, -1));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (buf_reader != null) {
                try {
                    buf_reader.close();
                } catch (IOException ex) {}
            }
        }
        return list;
    }

    private static boolean isBlockDevice(String devConf) {
        Log.i("StorageTool", devConf);
        if (devConf.contains("/dev/block/vold")
                || devConf.contains("/dev/fuse"))
        {
            if (!devConf.contains("/mnt/secure")
                && !devConf.contains("/mnt/asec")
                && !devConf.contains("/mnt/obb")
                && !devConf.contains("/mnt/media_rw") //system
                && !devConf.contains("/storage/emulated") //system link
                && !devConf.contains("/mnt/shell") //system link
                && !devConf.contains("/dev/mapper")
                && !devConf.contains("tmpfs"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isSymlink(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return isSymlink(file);
        }
        return false;
    }

    public static boolean isSymlink(File file) throws IOException {
        File canon;
        if (file.getParent() == null) {
            canon = file;
        } else {
            File canonDir = file.getParentFile().getCanonicalFile();
            canon = new File(canonDir, file.getName());
        }
        return !canon.getCanonicalFile().equals(canon.getAbsoluteFile());
    }
}
