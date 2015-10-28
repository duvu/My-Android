package mobile.myandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobile.myandroid.info.AndroidVersionActivity;
import mobile.myandroid.info.CallLogActivity;
import mobile.myandroid.info.CameraInformationActivity;
import mobile.myandroid.info.InternetActivity;
import mobile.myandroid.info.ManufacturerAndModelActivity;
import mobile.myandroid.info.MemoryActivity;
import mobile.myandroid.info.PhoneAppsActivity;
import mobile.myandroid.info.RamActivity;
import mobile.myandroid.info.ScreenDensityActivity;
import mobile.myandroid.info.ScreenSizeActivity;
import mobile.myandroid.screenshot.ScreenshotActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<MainItem> items = new ArrayList<>();
        items.add(new MainItem(R.string.my_phone_apps           , getResources().getDrawable(R.drawable.icon_app_search)));
        items.add(new MainItem(R.string.memory                  , getResources().getDrawable(R.drawable.icon_memory)));
        items.add(new MainItem(R.string.screenshot              , getResources().getDrawable(R.drawable.icon_screenshot)));
        items.add(new MainItem(R.string.call_history            , getResources().getDrawable(R.drawable.icon_call_log)));
        items.add(new MainItem(R.string.camera_information      , getResources().getDrawable(R.drawable.icon_camera)));
        items.add(new MainItem(R.string.internet                , getResources().getDrawable(R.drawable.icon_network)));
        items.add(new MainItem(R.string.screen_size             , getResources().getDrawable(R.drawable.icon_screen_size)));
        items.add(new MainItem(R.string.screen_density          , getResources().getDrawable(R.drawable.icon_density)));
        items.add(new MainItem(R.string.ram                     , getResources().getDrawable(R.drawable.icon_ram)));
        items.add(new MainItem(R.string.android_version         , getResources().getDrawable(R.drawable.icon_android_version)));
        items.add(new MainItem(R.string.manufacturer_and_model  , getResources().getDrawable(R.drawable.icon_manufacturer_model)));

        ListView listView = (ListView) findViewById(R.id.list_information_main);
        MainListAdapter adapter = new MainListAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainItem item = (MainItem)parent.getItemAtPosition(position);
                switch (item.getTextId()) {
                    case R.string.my_phone_apps:
                        //Toast.makeText(MainActivity.this, "Move to new MyPhoneApp Activity", Toast.LENGTH_LONG).show();
                        startActivity(PhoneAppsActivity.class);
                        break;
                    case R.string.memory:
                        startActivity(MemoryActivity.class);
                        break;
                    case R.string.screenshot:
                        startActivity(ScreenshotActivity.class);
                        break;
                    case R.string.call_history:
                        startActivity(CallLogActivity.class);
                        break;
                    case R.string.camera_information:
                        startActivity(CameraInformationActivity.class);
                        break;
                    case R.string.internet:
                        startActivity(InternetActivity.class);
                        break;
                    case R.string.screen_size:
                        startActivity(ScreenSizeActivity.class);
                        break;
                    case R.string.screen_density:
                        startActivity(ScreenDensityActivity.class);
                        break;
                    case R.string.ram:
                        startActivity(RamActivity.class);
                        break;
                    case R.string.android_version:
                        startActivity(AndroidVersionActivity.class);
                        break;
                    case R.string.manufacturer_and_model:
                        startActivity(ManufacturerAndModelActivity.class);
                        break;
                }
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    //-- start new activity
    private void startActivity(Class cls) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Intent intent = new Intent(MainActivity.this, cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MainItem {
        int text;
        Drawable icon;

        public MainItem(int text) {
            this.text = text;
        }

        public MainItem(int text, Drawable icon) {
            this.text = text;
            this.icon = icon;
        }

        public int getTextId() {
            return text;
        }
        public String getText() {
            return getString(text);
        }

        public void setText(int text) {
            this.text = text;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }
    }

    private class MainListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<MainItem> items;

        public MainListAdapter(Context context, List<MainItem> items) {
            this.mInflater = LayoutInflater.from(context);
            this.items = items;
        }

        public MainListAdapter() {
        }


        @Override
        public int getCount() {
            return items != null ? items.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return items != null ? items.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_item_main, null);
                holder.textView = (TextView) convertView.findViewById(R.id.txt_view);
                holder.imageView = (ImageView) convertView.findViewById(R.id.img_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(items.get(position).getText());
            holder.imageView.setImageDrawable(items.get(position).getIcon());

            return convertView;
        }
    }

    private class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
