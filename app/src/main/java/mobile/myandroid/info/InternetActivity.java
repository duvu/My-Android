package mobile.myandroid.info;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class InternetActivity extends BaseActivity {
    private static final String TYPE_UNKNOWN            = "UNKNOWN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
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

        NetworkInfo ni = getConnectionInfo();
        TextView txtInternetType = (TextView) findViewById(R.id.txt_internet_type);
        ImageView imgInternetIcon = (ImageView) findViewById(R.id.img_internet_icon);
        TextView txtInternetExtra = (TextView) findViewById(R.id.txt_internet_extra);

        NetInfo netInfo = getNetworkName(ni);

        txtInternetType.setText(netInfo.getType());
        imgInternetIcon.setImageDrawable(netInfo.getIcon());
        txtInternetExtra.setText(netInfo.getName());
    }

    private NetworkInfo getConnectionInfo() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni;
    }

    private String getNetworkType(NetworkInfo ni) {
        if (ni.isConnected()) {
            return ni.getTypeName();
        }
        return TYPE_UNKNOWN;
    }
    private NetInfo getNetworkName(NetworkInfo ni) {
        String type = (ni.isConnected() ? ni.getTypeName() : TYPE_UNKNOWN);
        String name = (ni.isConnected() ? ni.getExtraInfo() : getString(R.string.no_name));
        Drawable icon = null;
        switch (ni.getType()) {
            case ConnectivityManager.TYPE_BLUETOOTH:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_DUMMY:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_ETHERNET:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_MOBILE:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_MOBILE_DUN:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_VPN:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_WIFI:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
            case ConnectivityManager.TYPE_WIMAX:
                icon = getResources().getDrawable(R.drawable.internet_wifi);
                break;
        }
        name.replaceAll("[^a-zA-Z1-9_. ]+","");
        NetInfo netInfo = new NetInfo(type, name, icon);
        return netInfo;
    }

    private class NetInfo {
        private String type;
        private String name;
        private Drawable icon;

        public NetInfo(String name) {
            this.name = name;
        }

        public NetInfo(String name, Drawable icon) {
            this.name = name;
            this.icon = icon;
        }

        public NetInfo(String type, String name, Drawable icon) {
            this.type = type;
            this.name = name;
            this.icon = icon;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }
}
