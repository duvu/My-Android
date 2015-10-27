package mobile.myandroid.info;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class InternetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NetworkInfo ni = getConnectionInfo();
        TextView txtInternetType = (TextView) findViewById(R.id.txt_internet_type);
        TextView txtInternetExtra = (TextView) findViewById(R.id.txt_internet_extra);
        txtInternetType.setText(getNetworkType(ni));
        txtInternetExtra.setText(getNetworkName(ni));
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
        return "UNKNOWN";
    }
    private String getNetworkName(NetworkInfo ni) {
        String name = "UNKNOWN";
        switch (ni.getType()) {
            case ConnectivityManager.TYPE_BLUETOOTH:
                break;
            case ConnectivityManager.TYPE_DUMMY:
                break;
            case ConnectivityManager.TYPE_ETHERNET:
                break;
            case ConnectivityManager.TYPE_MOBILE:
                break;
            case ConnectivityManager.TYPE_MOBILE_DUN:
                break;
            case ConnectivityManager.TYPE_VPN:
                break;
            case ConnectivityManager.TYPE_WIFI:
                WifiManager wifiMgr = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                name = wifiInfo.getSSID();
                break;
            case ConnectivityManager.TYPE_WIMAX:
                break;
        }
        return name.replaceAll("[^a-zA-Z1-9_. ]+","");
    }
}
