package mobile.myandroid.screenshot.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    private int currentItem = 0;
    private Context context;

    private ScreenshotPagerCallback mCallback=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try {
            mCallback = (ScreenshotPagerCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ScreenshotPagerCallback.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupNavigateButton();

        return view;
    }
    private List<Fragment> getFragments() {
        List<Fragment> frags = new ArrayList<>();
        frags.add(ScreenShotFragment.newInstance(0));
        frags.add(ScreenShotFragment.newInstance(1));
        frags.add(ScreenShotFragment.newInstance(2));
        frags.add(ScreenShotFragment.newInstance(3));
        return frags;
    }

    private void setupNavigateButton() {
        Button btnNotWorked = (Button) view.findViewById(R.id.btn_screen_shot_not_worked);
        Button btnWorked = (Button) view.findViewById(R.id.btn_screen_shot_worked);

        btnNotWorked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem >= mViewPager.getAdapter().getCount()-1) {
                    //-- show dialog to ask user want to try-again
                    showContextDialog();
                } else {
                    mViewPager.setCurrentItem(++currentItem, true);
                }
            }
        });

        btnWorked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hidden the bottom bar and talk user that he/she is great ;)
                Toast.makeText(getActivity(), R.string.you_great, Toast.LENGTH_LONG).show();
                mCallback.onWorked(currentItem);
            }
        });
    }

    private void showContextDialog() {
        new AlertDialog.Builder(context)
                .setTitle(R.string.no_more_suggestion)
                .setMessage(R.string.you_may_try_again)
                .setCancelable(true)
                .setPositiveButton(R.string.retry_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // retry --> reset pager
                        currentItem = 0;
                        mViewPager.setCurrentItem(currentItem, true);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancel
                        dialog.cancel();
                    }
                }).show();
    }

    //-- for callback
    public interface ScreenshotPagerCallback {
        void onWorked(int option);
    }
}
