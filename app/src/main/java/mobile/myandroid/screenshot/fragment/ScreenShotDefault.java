package mobile.myandroid.screenshot.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mobile.myandroid.R;

/**
 * My Android
 * mobile.myandroid.screenshot.fragment
 * Created by beou on 28/10/2015.
 */
public class ScreenShotDefault extends Fragment {
    private View view;
    public static ScreenShotDefault newInstance () {
        return new ScreenShotDefault();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_screen_shot, container, false);
        ImageView imgScreenShot = (ImageView) view.findViewById(R.id.img_screen_shot);
        imgScreenShot.setImageDrawable(getResources().getDrawable(R.drawable.screenshot_option_power_vol_down));
        return view;
    }
}
