package mobile.myandroid.info;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.widget.TextView;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class ScreenSizeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_size);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtDisplaySize = (TextView) findViewById(R.id.txt_screen_size_pixels);
        Point size = getSize();
        StringBuffer sb = new StringBuffer();
        sb.append(size.y).append(" x ").append(size.x).append(" pixels");
        txtDisplaySize.setText(sb.toString());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        //int width = size.x;
        //int height = size.y;
        return size;
    }
}
