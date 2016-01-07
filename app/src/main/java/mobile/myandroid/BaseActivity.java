package mobile.myandroid;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import mobile.myandroid.info.AndroidVersionActivity;
import mobile.myandroid.info.CallLogActivity;
import mobile.myandroid.info.CameraInformationActivity;
import mobile.myandroid.info.InternetActivity;
import mobile.myandroid.info.ManufacturerAndModelActivity;
import mobile.myandroid.info.MemoryActivity;
import mobile.myandroid.apps.PhoneAppsActivity;
import mobile.myandroid.info.RamActivity;
import mobile.myandroid.info.ScreenDensityActivity;
import mobile.myandroid.info.ScreenSizeActivity;
import mobile.myandroid.screenshot.ScreenshotActivity;

/**
 * My Android
 * mobile.myandroid
 * Created by beou on 28/10/2015.
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //-- start new activity
    private void startActivity(Class cls, boolean back) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Intent intent = new Intent(this, cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_app_search:
                startActivity(PhoneAppsActivity.class, false);
                break;
            case R.id.nav_memory:
                startActivity(MemoryActivity.class, false);
                break;
            case R.id.nav_screenshot:
                startActivity(ScreenshotActivity.class, false);
                break;
            case R.id.nav_call_history:
                startActivity(CallLogActivity.class, false);
                break;
            case R.id.nav_camera_information:
                startActivity(CameraInformationActivity.class, false);
                break;
            case R.id.nav_internet:
                startActivity(InternetActivity.class, false);
                break;
            case R.id.nav_screen_size:
                startActivity(ScreenSizeActivity.class, false);
                break;
            case R.id.nav_screen_density:
                startActivity(ScreenDensityActivity.class, false);
                break;
            case R.id.nav_ram:
                startActivity(RamActivity.class, false);
                break;
            case R.id.nav_android_version:
                startActivity(AndroidVersionActivity.class, false);
                break;
            case R.id.nav_manufacturer_model:
                startActivity(ManufacturerAndModelActivity.class, false);
                break;
            case R.id.nav_share:
                //--TODO
                break;
            case R.id.nav_send:
                //--TODO
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
