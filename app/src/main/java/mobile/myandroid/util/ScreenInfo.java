package mobile.myandroid.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * My Android
 * mobile.myandroid.util
 * Created by beou on 28/10/2015.
 */
public class ScreenInfo {
    private static ScreenInfo instance = null;
    private static Display display = null;
    private int x; //pixels
    private int y; //pixels
    private float xdpi;
    private float ydpi;
    private float density;
    private int densityDPI;

    public static ScreenInfo getInstance(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (instance == null) {
            instance = new ScreenInfo();
        }
        if (display == null) {
            display = windowManager.getDefaultDisplay();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            display.getSize(size);
            instance.x = size.x;
            instance.y = size.y;
        } else {
            instance.x = display.getWidth();
            instance.y = display.getHeight();
        }
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        instance.xdpi = metrics.xdpi;
        instance.ydpi = metrics.ydpi;
        instance.density = metrics.density;
        instance.densityDPI = metrics.densityDpi;
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getSizeInches () {
        double fx = Math.pow(x/xdpi, 2);
        double fy = Math.pow(y/ydpi, 2);
        double si = Math.sqrt(fx + fy);
        return Math.round(si*10)/10.0;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getXdpi() {
        return xdpi;
    }

    public void setXdpi(float xdpi) {
        this.xdpi = xdpi;
    }

    public float getYdpi() {
        return ydpi;
    }

    public void setYdpi(float ydpi) {
        this.ydpi = ydpi;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDPI() {
        return densityDPI;
    }

    public void setDensityDPI(int densityDPI) {
        this.densityDPI = densityDPI;
    }
}
