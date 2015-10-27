package mobile.myandroid.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.MyApplication;
import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class ManufacturerAndModelActivity extends AppCompatActivity {
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_and_model);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myApplication = MyApplication.getInstance();

        TextView txtManufacturer = (TextView) findViewById(R.id.txt_manufacturer_info);
        TextView txtModel = (TextView) findViewById(R.id.txt_model_info);
        txtManufacturer.setText(myApplication.getManufacturer());
        txtModel.setText(myApplication.getModel());
    }
}
