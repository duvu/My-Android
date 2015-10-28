package mobile.myandroid.screenshot;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;
import mobile.myandroid.screenshot.fragment.ScreenShotDefault;
import mobile.myandroid.screenshot.fragment.ScreenShotFragment;
import mobile.myandroid.screenshot.fragment.ScreenShotPager;

/**
 * Created by beou on 26/10/2015.
 */
public class ScreenshotActivity extends BaseActivity implements View.OnClickListener,
        ScreenShotPager.ScreenshotPagerCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
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

        TextView txtScreenshotGuide = (TextView) findViewById(R.id.txt_screen_shot_guide);
        txtScreenshotGuide.setText(R.string.screenshot_guide);

        Button btnMoreOptions = (Button) findViewById(R.id.btn_screen_shot_more_options);
        btnMoreOptions.setOnClickListener(this);

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frg_screen_shot_container, ScreenShotDefault.newInstance()).commit();
    }

    @Override
    public void onClick(View v) {
        View viewBottom = findViewById(R.id.layout_screen_shot_bottom);
        viewBottom.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frg_screen_shot_container, ScreenShotPager.newInstance()).commit();
    }

    @Override
    public void onWorked(int option) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frg_screen_shot_container, ScreenShotFragment.newInstance(option)).commit();
    }
}
