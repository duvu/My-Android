package mobile.myandroid.info;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class AndroidVersionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_version);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtAndroidVersion = (TextView) findViewById(R.id.txt_android_version);
        String androidVersion = getCurrentVersion();
        androidVersion+="\n";
        androidVersion+=Build.VERSION.RELEASE;
        txtAndroidVersion.setText(androidVersion);
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
        return "UNKNOWN";
    }
}
