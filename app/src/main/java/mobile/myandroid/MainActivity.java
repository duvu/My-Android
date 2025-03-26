package mobile.myandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import mobile.myandroid.ads.AdHelper;
import mobile.myandroid.apps.PhoneAppsActivity;
import mobile.myandroid.info.AndroidVersionActivity;
import mobile.myandroid.info.CallLogActivity;
import mobile.myandroid.info.CameraInformationActivity;
import mobile.myandroid.info.InternetActivity;
import mobile.myandroid.info.ManufacturerAndModelActivity;
import mobile.myandroid.info.MemoryActivity;
import mobile.myandroid.info.RamActivity;
import mobile.myandroid.info.ScreenDensityActivity;
import mobile.myandroid.info.ScreenSizeActivity;
import mobile.myandroid.privacy.PrivacyPolicyActivity;
import mobile.myandroid.screenshot.ScreenshotActivity;

/**
 * Main entry point of the app that displays a list of available features
 */
public class MainActivity extends AppCompatActivity {
    private AdHelper adHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        
        try {
            // Initialize AdHelper
            adHelper = new AdHelper(this);
            
            setupMainList();
        } catch (Exception e) {
            Log.e("MainActivity", "Error during initialization: " + e.getMessage());
        }
    }
    
    private void setupMainList() {
        List<MainItem> items = new ArrayList<>();
        items.add(new MainItem(R.string.my_phone_apps, getDrawable(R.drawable.icon_app_search)));
        items.add(new MainItem(R.string.memory, getDrawable(R.drawable.icon_memory)));
        items.add(new MainItem(R.string.screenshot, getDrawable(R.drawable.icon_screenshot)));
        items.add(new MainItem(R.string.call_history, getDrawable(R.drawable.icon_call_log)));
        items.add(new MainItem(R.string.camera_information, getDrawable(R.drawable.icon_camera)));
        items.add(new MainItem(R.string.internet, getDrawable(R.drawable.icon_network)));
        items.add(new MainItem(R.string.screen_size, getDrawable(R.drawable.icon_screen_size)));
        items.add(new MainItem(R.string.screen_density, getDrawable(R.drawable.icon_density)));
        items.add(new MainItem(R.string.ram, getDrawable(R.drawable.icon_ram)));
        items.add(new MainItem(R.string.android_version, getDrawable(R.drawable.icon_android_version)));
        items.add(new MainItem(R.string.manufacturer_and_model, getDrawable(R.drawable.icon_manufacturer_model)));
        items.add(new MainItem(R.string.privacy_policy, getDrawable(R.drawable.icon_privacy_policy)));

        MainListAdapter adapter = new MainListAdapter(this, items);
        ListView mainListView = findViewById(R.id.list_information_main);
        mainListView.setAdapter(adapter);
        
        mainListView.setOnItemClickListener((parent, view, position, id) -> {
            MainItem item = (MainItem) parent.getItemAtPosition(position);
            navigateToFeature(item.getTextId());
        });
        
        // Load the AdView
        AdView adView = findViewById(R.id.adView);
        if (adView != null) {
            adHelper.loadBannerAd(adView);
        }
    }
    
    /**
     * Navigate to the appropriate feature screen based on the selected item
     * @param featureId The resource ID of the selected feature
     */
    private void navigateToFeature(int featureId) {
        Intent intent = null;
        
        switch (featureId) {
            case R.string.my_phone_apps:
                intent = new Intent(this, PhoneAppsActivity.class);
                break;
            case R.string.memory:
                intent = new Intent(this, MemoryActivity.class);
                break;
            case R.string.screenshot:
                intent = new Intent(this, ScreenshotActivity.class);
                break;
            case R.string.call_history:
                intent = new Intent(this, CallLogActivity.class);
                break;
            case R.string.camera_information:
                intent = new Intent(this, CameraInformationActivity.class);
                break;
            case R.string.internet:
                intent = new Intent(this, InternetActivity.class);
                break;
            case R.string.screen_size:
                intent = new Intent(this, ScreenSizeActivity.class);
                break;
            case R.string.screen_density:
                intent = new Intent(this, ScreenDensityActivity.class);
                break;
            case R.string.ram:
                intent = new Intent(this, RamActivity.class);
                break;
            case R.string.android_version:
                intent = new Intent(this, AndroidVersionActivity.class);
                break;
            case R.string.manufacturer_and_model:
                intent = new Intent(this, ManufacturerAndModelActivity.class);
                break;
            case R.string.privacy_policy:
                intent = new Intent(this, PrivacyPolicyActivity.class);
                break;
        }
        
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add the privacy policy menu items
        adHelper.addPrivacyPolicyMenuItem(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        // Handle privacy options
        if (adHelper.handleMenuItemSelected(id)) {
            return true;
        }
        
        // Handle privacy policy
        if (id == R.id.action_privacy_policy) {
            startActivity(new Intent(this, PrivacyPolicyActivity.class));
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    /**
     * Model class for items in the main menu
     */
    private static class MainItem {
        private final int textResourceId;
        private final Drawable icon;

        public MainItem(int textResourceId, Drawable icon) {
            this.textResourceId = textResourceId;
            this.icon = icon;
        }

        public int getTextId() {
            // Ensure we return a valid resource ID
            return textResourceId > 0 ? textResourceId : 0;
        }
        
        public Drawable getIcon() {
            return icon;
        }
    }

    /**
     * Adapter for the main menu list
     */
    private class MainListAdapter extends BaseAdapter {
        private final List<MainItem> mItems;
        private final LayoutInflater mInflater;

        public MainListAdapter(Context context, List<MainItem> items) {
            this.mInflater = LayoutInflater.from(context);
            this.mItems = items != null ? items : new ArrayList<>();
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public MainItem getItem(int position) {
            if (position >= 0 && position < mItems.size()) {
                return mItems.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Use a very minimal implementation to avoid any complexity
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_main, parent, false);
            }
            
            try {
                // Get views
                TextView textView = convertView.findViewById(R.id.txt_view);
                ImageView imageView = convertView.findViewById(R.id.img_view);
                
                // Get item
                MainItem item = getItem(position);
                
                // Set data with null checks
                if (item != null) {
                    // Set text
                    if (textView != null) {
                        try {
                            int textId = item.getTextId();
                            String text = getString(textId);
                            if (text != null) {
                                textView.setText(text);
                                // Also safely set content description
                                if (convertView != null) {
                                    convertView.setContentDescription(text);
                                }
                            } else {
                                // If text is null, set empty string to avoid null pointer
                                textView.setText("");
                            }
                        } catch (Exception e) {
                            Log.e("MainActivity", "Error setting text");
                        }
                    }
                    
                    // Set image
                    if (imageView != null && item.getIcon() != null) {
                        try {
                            imageView.setImageDrawable(item.getIcon());
                        } catch (Exception e) {
                            Log.e("MainActivity", "Error setting image");
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("MainActivity", "Error in getView");
            }
            
            return convertView;
        }
    }
}
