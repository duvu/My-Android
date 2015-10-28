package mobile.myandroid.info;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;
import mobile.myandroid.util.ScreenInfo;

/**
 * Created by beou on 26/10/2015.
 */
public class ScreenDensityActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_density);
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
}
