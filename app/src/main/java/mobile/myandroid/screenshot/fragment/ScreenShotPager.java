package mobile.myandroid.screenshot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobile.myandroid.R;
import mobile.myandroid.util.PagerAdapter;

/**
 * My Android
 * mobile.myandroid.screenshot.fragment
 * Created by beou on 28/10/2015.
 */
public class ScreenShotPager extends Fragment {
    private ViewPager mViewPager;
    private View view;
    public static ScreenShotPager newInstance() {
        return new ScreenShotPager();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_screen_shot_pager, container, false);
        mViewPager = (ViewPager)view.findViewById(R.id.frgment_view_pager_screen_shot);
        List<Fragment> fragments = getFragments();
        PagerAdapter mPageAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mPageAdapter);
        return view;
    }
    private List<Fragment> getFragments() {
        List<Fragment> frags = new ArrayList<>();
        frags.add(ScreenShotFragment.newInstance(1));
        frags.add(ScreenShotFragment.newInstance(2));
        frags.add(ScreenShotFragment.newInstance(3));
        frags.add(ScreenShotFragment.newInstance(4));
        return frags;
    }
}
