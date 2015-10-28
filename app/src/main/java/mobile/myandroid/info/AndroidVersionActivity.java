package mobile.myandroid.info;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class AndroidVersionActivity extends BaseActivity {

    private Drawer result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_version);
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

        TextView txtAndroidVersionName = (TextView) findViewById(R.id.txt_android_version_name);
        TextView txtAndroidVersionNumber = (TextView) findViewById(R.id.txt_android_version_number);

        ImageView imgAndroidVersion = (ImageView) findViewById(R.id.img_android_version);
        String androidVersion = getCurrentVersion();
        txtAndroidVersionName.setText(androidVersion);
        txtAndroidVersionNumber.setText(Build.VERSION.RELEASE);
        imgAndroidVersion.setImageDrawable(getResources().getDrawable(R.drawable.icon_app_version));
    }

    private String getCurrentVersion () {
        switch (Build.VERSION.SDK_INT) {
            case Build.VERSION_CODES.BASE:
                return "Base";
            case Build.VERSION_CODES.BASE_1_1:
                return "Base 1.1";
            case Build.VERSION_CODES.CUPCAKE:
                return "Cup Cake";
            case Build.VERSION_CODES.DONUT:
                return "Donut";
            case Build.VERSION_CODES.ECLAIR:
                return "Eclair";
            case Build.VERSION_CODES.ECLAIR_0_1:
                return "Eclair 0.1";
            case Build.VERSION_CODES.ECLAIR_MR1:
                return "Eclair Mr1";
            case Build.VERSION_CODES.FROYO:
                return "Froyo";
            case Build.VERSION_CODES.GINGERBREAD:
                return "GingerBread";
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                return "GingerBread";
            case Build.VERSION_CODES.HONEYCOMB:
            case Build.VERSION_CODES.HONEYCOMB_MR1:
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                return "HoneyComb";
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                return "IceCreamSandwich";
            case Build.VERSION_CODES.JELLY_BEAN:
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                return "JellyBean";
            case Build.VERSION_CODES.KITKAT:
            case Build.VERSION_CODES.KITKAT_WATCH:
                return "Kitkat";
            case Build.VERSION_CODES.LOLLIPOP:
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                return "Lollipop";
            case Build.VERSION_CODES.M:
                return "M";
        }
        return "Unknown";
    }
}
