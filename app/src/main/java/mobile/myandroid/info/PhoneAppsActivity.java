package mobile.myandroid.info;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.myandroid.R;

/**
 * Created by beou on 26/10/2015.
 */
public class PhoneAppsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_apps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<AppItem> items = null;
        try {
            items = getAppsList();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        ListView listPhoneApps = (ListView) findViewById(R.id.list_phone_apps);
        PhoneAppsAdapter adapter = new PhoneAppsAdapter(this, items);
        listPhoneApps.setAdapter(adapter);

        listPhoneApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppItem item = (AppItem)parent.getItemAtPosition(position);
                openApplication(PhoneAppsActivity.this, item.getPackageName());
            }
        });
    }

    private List<AppItem> getAppsList() throws PackageManager.NameNotFoundException {
        List<AppItem> listApps = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo ai : packages) {
            String appName = pm.getApplicationLabel(ai).toString();
            Drawable appIcon = pm.getApplicationIcon(ai);
            long lastUpdateTime = pm.getPackageInfo(ai.packageName, PackageManager.GET_META_DATA).lastUpdateTime;
            Date lastUpdated = new Date(lastUpdateTime);
            AppItem item = new AppItem(appName, appIcon, lastUpdated);

            item.setPackageName(ai.packageName);

            listApps.add(item);
        }
        return listApps;
    }

    private void openApplication(Context context, String packageName) {
        PackageManager pm = getPackageManager();
        Intent i = pm.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return;
    }

    private class AppItem {
        private String packageName;
        private String appName;
        private Drawable appIcon;
        private Date installedDate;

        public AppItem(String appName) {
            this.appName = appName;
        }

        public AppItem(String appName, Drawable appIcon) {
            this.appName = appName;
            this.appIcon = appIcon;
        }

        public AppItem(String appName, Drawable appIcon, Date installedDate) {
            this.appName = appName;
            this.appIcon = appIcon;
            this.installedDate = installedDate;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public Drawable getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(Drawable appIcon) {
            this.appIcon = appIcon;
        }

        public Date getInstalledDate() {
            return installedDate;
        }

        public void setInstalledDate(Date installedDate) {
            this.installedDate = installedDate;
        }
    }
    private class PhoneAppsAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<AppItem> items;

        public PhoneAppsAdapter(Context context, List<AppItem> items) {
            this.mInflater = LayoutInflater.from(context);
            this.items = items;
        }

        @Override
        public int getCount() {
            return items != null ? items.size() : 0;
        }

        @Override
        public AppItem getItem(int position) {
            return items != null ? items.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppViewHolder holder = null;
            if (convertView == null) {
                holder = new AppViewHolder();
                convertView = mInflater.inflate(R.layout.list_item_phone_app, null);

                holder.appIcon = (ImageView) convertView.findViewById(R.id.img_app_icon);
                holder.appName =  (TextView) convertView.findViewById(R.id.txt_app_name);
                holder.appInstalled = (TextView) convertView.findViewById(R.id.txt_app_installed);
                //holder.btnAppDel = (ImageButton) convertView.findViewById(R.id.btn_app_del);
                convertView.setTag(holder);
            } else {
                holder = (AppViewHolder)convertView.getTag();
            }
            //TODO: add info to holder here
            holder.appIcon.setImageDrawable(getItem(position).getAppIcon());
            holder.appName.setText(getItem(position).getAppName());
            holder.appInstalled.setText(getItem(position).getInstalledDate().toString());

            return convertView;
        }
    }

    private class AppViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appInstalled;
        ImageButton btnAppDel;
    }
}
