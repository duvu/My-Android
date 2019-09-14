package mobile.myandroid.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by beou on 01/06/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
