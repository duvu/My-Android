package mobile.myandroid.info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;
import mobile.myandroid.ram.RamUtils;
import mobile.myandroid.util.StringTool;

/**
 * Created by beou on 26/10/2015.
 */
public class RamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtRamCap = (TextView) findViewById(R.id.txt_ram_info);
        txtRamCap.setText(StringTool.formatSize(RamUtils.getRAMTotal()));
    }
}
