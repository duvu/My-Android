package mobile.myandroid.apps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * My-Android
 * mobile.myandroid.apps
 * Created by beou on 08/01/2016.
 */
public class PhoneAppsPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> items;
    public PhoneAppsPagerAdapter(FragmentManager fm, List<Fragment> lst) {
        super(fm);
        items = lst;
    }
    public PhoneAppsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
