package mobile.myandroid.info;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class ManufacturerAndModelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_and_model);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtManufacturer = (TextView) findViewById(R.id.txt_manufacturer_info);
        TextView txtModel = (TextView) findViewById(R.id.txt_model_info);
        txtManufacturer.setText(Build.MANUFACTURER);
        txtModel.setText(Build.MODEL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
