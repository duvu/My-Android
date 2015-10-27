package mobile.myandroid.util;

import android.util.Log;

/**
 * Created by beou on 27/10/2015.
 */
public class StringTool {
    private static final String TAG         = "StringTool";
    private static final double KILO        = 1024.0;
    private static final String KILOBYTES   = "KB";
    private static final String MEGABYTES   = "MB";
    private static final String GIGABYTES   = "GB";

    public static boolean isBlank(String s) {
        return ((s == null) || (s.length()==0));
    }
    public static double formatGB(long size) {
        return (double)(size/=(1024.0*1024.0*1024.0));
    }

    public static String formatSize(long size) {
        Log.i(TAG, "Size:" + size);
        double _size = size;
        String suffix = null;
        if (_size >= KILO) {
            suffix = KILOBYTES;
            _size /= KILO;
            if (_size >= KILO) {
                suffix = MEGABYTES;
                _size /= KILO;
                if (_size >= KILO) {
                    suffix = GIGABYTES;
                    _size /=KILO;
                }
            }
        }

        _size = Math.round(_size*100)/100.0;
        StringBuffer sb = new StringBuffer(Double.toString(_size));
        sb.append(suffix);
        return sb.toString();
    }
}
