package mobile.myandroid.apps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobile.myandroid.R;

/**
 * My-Android
 * mobile.myandroid.apps
 * Created by beou on 08/01/2016.
 */
public class PhoneAppsFragment extends ListFragment {

    List<AppItem> listApps;
    View view;
    ListView listView;

    public static PhoneAppsFragment newInstance(ArrayList<AppItem> listApps) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("lst", listApps);
        PhoneAppsFragment fragment = new PhoneAppsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            listApps = args.getParcelableArrayList("lst");
        } else {
            listApps = null;
        }
    }
    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pager_list, container, false);
        //listView = (ListView) view.findViewById(R.id.)
        //View tv = v.findViewById(R.id.text);
        //((TextView)tv).setText("Fragment #" + mNum);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new PhoneAppsAdapter(getActivity(), listApps));
        /*setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings));*/
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
        AppItem app = (AppItem)l.getItemAtPosition(position);
        openApplication(getActivity(), app.getPackageName());
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
                holder.btnAppDel = (ImageButton) convertView.findViewById(R.id.btn_app_del);
                convertView.setTag(holder);
            } else {
                holder = (AppViewHolder)convertView.getTag();
            }

            final AppItem item = (AppItem) getItem(position);

            holder.appIcon.setImageDrawable(BitmapHelper.get(item.getAppName()));
            holder.appName.setText(item.getAppName());
            holder.appInstalled.setText(item.getInstalledDate().toString());
            holder.btnAppDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //-- show a dialogAlert
                    //-- delete app
                    uninstallApp(item);
                }
            });

            return convertView;
        }
    }

    private void uninstallApp(AppItem item) {
        if (isSystemPackage(item.getPackageInfo())) {
            Toast.makeText(getActivity(), getString(R.string.cannot_remove_cause_system_app), Toast.LENGTH_LONG).show();
        } else {
            //Uninstall app here
            Uri packageURI = Uri.parse("package:" + item.getPackageName());
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);
        }
    }
    private void openApplication(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent i = pm.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return;
    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags &
                ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    private class AppViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appInstalled;
        ImageButton btnAppDel;
    }
}
