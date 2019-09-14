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
public class ScreenSizeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_size);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtPixelSize = (TextView) findViewById(R.id.txt_screen_size_pixels);
        TextView txtInchSize = (TextView) findViewById(R.id.txt_screen_size_inch);

        //-- get informations
        ScreenInfo sci = ScreenInfo.getInstance(this);
        StringBuffer sb = new StringBuffer();
        sb.append(sci.getX()).append(" x ").append(sci.getY()).append(" pixels");
        //-- showing
        txtPixelSize.setText(sb.toString());
        txtInchSize.setText(sci.getSizeInches()+" inches");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
