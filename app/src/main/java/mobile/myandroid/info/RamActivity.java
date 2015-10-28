package mobile.myandroid.info;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;
import mobile.myandroid.util.RamUtils;
import mobile.myandroid.util.StringTool;

/**
 * Created by beou on 26/10/2015.
 */
public class RamActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ram);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //--
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //--


        TextView txtRamCap = (TextView) findViewById(R.id.txt_ram_info);
        txtRamCap.setText(StringTool.formatSize(RamUtils.getRAMTotal()));
    }
}
