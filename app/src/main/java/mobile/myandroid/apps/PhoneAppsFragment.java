package mobile.myandroid.apps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
 * PhoneAppsFragment displays a list of installed applications
 * with their icons, names, and installation dates.
 */
public class PhoneAppsFragment extends ListFragment {
    private static final String TAG = "PhoneAppsFragment";
    private static final String ARG_APP_LIST = "lst";

    private List<AppItem> listApps;
    private View view;

    /**
     * Creates a new instance of PhoneAppsFragment with pre-populated app list
     * @param listApps List of applications to display
     * @return A new instance of PhoneAppsFragment
     */
    public static PhoneAppsFragment newInstance(ArrayList<AppItem> listApps) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_APP_LIST, listApps);
        PhoneAppsFragment fragment = new PhoneAppsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            listApps = args.getParcelableArrayList(ARG_APP_LIST);
        } else {
            listApps = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pager_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new PhoneAppsAdapter(requireActivity(), listApps));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "Item clicked: " + id);
        AppItem app = (AppItem)l.getItemAtPosition(position);
        openApplication(requireActivity(), app.getPackageName());
    }

    /**
     * Open an application by its package name
     * @param context Application context
     * @param packageName Package name of the app to open
     */
    private void openApplication(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent i = pm.getLaunchIntentForPackage(packageName);
        if (i == null) {
            Toast.makeText(context, R.string.cannot_open_app, Toast.LENGTH_SHORT).show();
            return;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
    }

    /**
     * Uninstall an application
     * @param item AppItem representing the app to uninstall
     */
    private void uninstallApp(AppItem item) {
        if (isSystemPackage(item.getPackageInfo())) {
            Toast.makeText(getActivity(), getString(R.string.cannot_remove_cause_system_app), Toast.LENGTH_LONG).show();
        } else {
            // Uninstall app through the system uninstaller
            Uri packageURI = Uri.parse("package:" + item.getPackageName());
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);
        }
    }

    /**
     * Check if an app is a system package
     * @param pkgInfo Package information
     * @return true if system package, false otherwise
     */
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags &
                ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * View holder pattern for app list items
     */
    private static class AppViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView appInstalled;
        ImageButton btnAppDel;
    }

    /**
     * Adapter for displaying app information in a list
     */
    private class PhoneAppsAdapter extends BaseAdapter {
        private final LayoutInflater mInflater;
        private final List<AppItem> items;

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
            AppViewHolder holder;
            if (convertView == null) {
                holder = new AppViewHolder();
                convertView = mInflater.inflate(R.layout.list_item_phone_app, null);

                holder.appIcon = convertView.findViewById(R.id.img_app_icon);
                holder.appName = convertView.findViewById(R.id.txt_app_name);
                holder.appInstalled = convertView.findViewById(R.id.txt_app_installed);
                holder.btnAppDel = convertView.findViewById(R.id.btn_app_del);
                convertView.setTag(holder);
            } else {
                holder = (AppViewHolder)convertView.getTag();
            }

            final AppItem item = getItem(position);
            if (item != null) {
                holder.appIcon.setImageDrawable(BitmapHelper.get(item.getAppName()));
                holder.appName.setText(item.getAppName());
                holder.appInstalled.setText(item.getInstalledDate().toString());
                holder.btnAppDel.setOnClickListener(v -> uninstallApp(item));
                
                // Set content description for accessibility
                holder.btnAppDel.setContentDescription(
                        getString(R.string.uninstall_app, item.getAppName()));
            }

            return convertView;
        }
    }
}
