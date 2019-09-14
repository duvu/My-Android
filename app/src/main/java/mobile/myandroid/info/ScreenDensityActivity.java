package mobile.myandroid.info;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;
import mobile.myandroid.util.ScreenInfo;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        TextView txtScreenDensity = (TextView) findViewById(R.id.txt_screen_density_info);
        ScreenInfo sci = ScreenInfo.getInstance(this);

        String txt = "Density: " + sci.getDensity();
        txt += "\n";
        txt += "X: " + sci.getXdpi();
        txt += "\n";
        txt += "Y: " + sci.getYdpi();
        txt += "\n";
        txt += "DPI: " + sci.getDensityDPI();

        txtScreenDensity.setText(txt);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
