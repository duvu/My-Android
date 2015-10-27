package mobile.myandroid.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class ScreenDensityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_density);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        TextView txtScreenDensity = (TextView) findViewById(R.id.txt_screen_density_info);

        String txt = "Density: " + metrics.density;
        txt += "\n";
        txt += "X: " + metrics.xdpi;
        txt += "\n";
        txt += "Y: " + metrics.ydpi;
        txt += "\n";
        txt += "DPI: " + metrics.densityDpi;

        txtScreenDensity.setText(txt);

    }
}
