package mobile.myandroid.info;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;
import mobile.myandroid.util.RamUtils;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView txtRamCap = (TextView) findViewById(R.id.txt_ram_info);
        txtRamCap.setText(StringTool.formatSize(RamUtils.getRAMTotal()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
