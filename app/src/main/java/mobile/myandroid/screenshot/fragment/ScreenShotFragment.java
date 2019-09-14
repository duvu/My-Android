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
public class ScreenShotFragment extends Fragment {
    private View view;

    public static ScreenShotFragment newInstance(int option) {
        ScreenShotFragment ssf = new ScreenShotFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Option", option);
        ssf.setArguments(bundle);
        return ssf;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_screen_shot, container, false);
        Bundle args = getArguments();
        int option = args.getInt("Option");
        ImageView imgScreenShot = (ImageView) view.findViewById(R.id.img_screen_shot);

        switch (option) {
            case 0:
                imgScreenShot.setImageDrawable(getResources().getDrawable(R.drawable.screenshot_option_power_vol_down));
                break;
            case 1:
                imgScreenShot.setImageDrawable(getResources().getDrawable(R.drawable.screenshot_option_power_vol_up));
                break;
            case 2:
                imgScreenShot.setImageDrawable(getResources().getDrawable(R.drawable.screenshot_option_home_vol_down));
                break;
            case 3:
                imgScreenShot.setImageDrawable(getResources().getDrawable(R.drawable.screenshot_option_power_home));
                break;
        }
        return view;
    }
}
