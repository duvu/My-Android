package mobile.myandroid.apps;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class PhoneAppsActivity extends BaseActivity {
    private static PackageManager packageManager;

    ArrayList<AppItem> systemApplications = null;
    ArrayList<AppItem> userApplications = null;

    PhoneAppsPagerAdapter mAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_apps);
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


        try {
            getAppsList();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mViewPager = (ViewPager) findViewById(R.id.apps_view_pager);
        List<Fragment> lstFrag = new ArrayList<>();
        lstFrag.add(PhoneAppsFragment.newInstance(userApplications));
        lstFrag.add(PhoneAppsFragment.newInstance(systemApplications));
        mAdapter = new PhoneAppsPagerAdapter(getSupportFragmentManager(), lstFrag);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public PackageManager getPackageManager() {
        if (packageManager == null) {
            packageManager = super.getPackageManager();
        }
        return packageManager;
    }

    private void getAppsList() throws PackageManager.NameNotFoundException {
        if (systemApplications == null) {
            systemApplications = new ArrayList<>();
        }
        if (userApplications == null) {
            userApplications = new ArrayList<>();
        }
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo ai : packages) {
            String appName = pm.getApplicationLabel(ai).toString();
            Drawable appIcon = pm.getApplicationIcon(ai);
            BitmapHelper.put(appName, appIcon);

            PackageInfo packageInfo = pm.getPackageInfo(ai.packageName, PackageManager.GET_META_DATA);
            long lastUpdateTime = packageInfo.lastUpdateTime;
            Date lastUpdated = new Date(lastUpdateTime);
            AppItem item = new AppItem(appName, lastUpdated);
            item.setPackageName(ai.packageName);
            item.setPackageInfo(packageInfo);
            if (isSystemPackage(packageInfo)) {
                systemApplications.add(item);
            } else {
                userApplications.add(item);
            }
        }
    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags &
                ApplicationInfo.FLAG_SYSTEM) != 0;
    }
}
