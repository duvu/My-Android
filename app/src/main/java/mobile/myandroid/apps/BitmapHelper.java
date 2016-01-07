package mobile.myandroid.apps;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * My-Android
 * mobile.myandroid.apps
 * Created by beou on 08/01/2016.
 */
public class BitmapHelper {
    private static HashMap<String, Drawable> bitmap = null;
    public static HashMap<String, Drawable> getCache() {
        if (bitmap == null) {
            bitmap = new HashMap<>();
        }
        return bitmap;
    }
    public static void put(String k, Drawable v) {
        getCache().put(k, v);
    }
    public static Drawable get(String k) {
        return getCache().get(k);
    }
}
