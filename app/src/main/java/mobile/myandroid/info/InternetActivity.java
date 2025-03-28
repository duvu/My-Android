package mobile.myandroid.info;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class InternetActivity extends AppCompatActivity {
    private static final String TYPE_UNKNOWN            = "UNKNOWN";
    private static final String TYPE_NO_CONNECTION      = "NO CONNECTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NetworkInfo ni = getConnectionInfo();
        TextView txtInternetType = (TextView) findViewById(R.id.txt_internet_type);
        ImageView imgInternetIcon = (ImageView) findViewById(R.id.img_internet_icon);
        TextView txtInternetExtra = (TextView) findViewById(R.id.txt_internet_extra);

        NetInfo netInfo = getNetworkName(ni);

        txtInternetType.setText(netInfo.getType());
        imgInternetIcon.setImageDrawable(netInfo.getIcon());
        txtInternetExtra.setText(netInfo.getName());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
        if (ni == null) {
            String type = TYPE_NO_CONNECTION;
            String name = getString(R.string.no_name);
            Drawable icon = getResources().getDrawable(R.drawable.ic_warning_black_48dp);
            return new NetInfo(type, name, icon);
        }
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
