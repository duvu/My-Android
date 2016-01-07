package mobile.myandroid.info;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.myandroid.BaseActivity;
import mobile.myandroid.R;
import mobile.myandroid.util.StringTool;

/**
 * Created by beou on 26/10/2015.
 */
public class CallLogActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    List<CallLogInfo> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //--
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //--

        items = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        getLoaderManager().initLoader(1, null, CallLogActivity.this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case 1:
                // Returns a new CursorLoader
                return new CursorLoader(
                        this,   // Parent activity context
                        CallLog.Calls.CONTENT_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            case 2: //load contacts list
                return new CursorLoader(
                        this,
                        CallLog.Calls.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case 1:
                int number = data.getColumnIndex(CallLog.Calls.NUMBER);
                int type = data.getColumnIndex(CallLog.Calls.TYPE);
                int date = data.getColumnIndex(CallLog.Calls.DATE);
                int duration = data.getColumnIndex(CallLog.Calls.DURATION);
                int name = data.getColumnIndex(CallLog.Calls.CACHED_NAME);
                //int photoId = data.getColumnIndex(CallLog.Calls.CACHED_PHOTO_ID);
                //int photoUri = data.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI);

                CallLogsAdapter adapter = new CallLogsAdapter(this, items);
                ListView listCallLogs = (ListView) findViewById(R.id.list_call_history);
                listCallLogs.setAdapter(adapter);

                //// TODO: 27/10/2015 update activity here
                while (data.moveToNext()) {
                    String phNumber = data.getString(number);
                    String callType = data.getString(type);
                    String callDate = data.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    long callDuration = data.getLong(duration);
                    //String contactPhotoId = data.getString(photoId);
                    //String contactPhotoUri = data.getString(photoUri);

                    String contactName = data.getString(name);
                    if (StringTool.isBlank(contactName)) contactName = getString(R.string.unknown);

                    String callDirection = null;
                    Drawable callDirectionDrawable = null;

                    int callTypeCode = Integer.parseInt(callType);
                    switch (callTypeCode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            callDirection = "Outgoing";
                            callDirectionDrawable = getResources().getDrawable(R.drawable.ic_call_made_green_48dp);
                            break;

                        case CallLog.Calls.INCOMING_TYPE:
                            callDirection = "Incoming";
                            callDirectionDrawable = getResources().getDrawable(R.drawable.ic_call_received_blue_48dp);
                            break;

                        case CallLog.Calls.MISSED_TYPE:
                            callDirectionDrawable = getResources().getDrawable(R.drawable.ic_call_missed_red_48dp);
                            callDirection = "Missed";
                            break;
                    }

                    CallLogInfo callInfo = new CallLogInfo(phNumber, callDayTime);
                    callInfo.setCallDuration(callDuration);
                    callInfo.setCallDirection(callDirection);
                    callInfo.setCalDirectionDrawable(callDirectionDrawable);
                    callInfo.setContactName(contactName);
                    items.add(callInfo);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //do nothing
    }

    private class CallLogInfo {
        private String phoneNumber;
        private String contactName;
        private String callType;
        private Date callDate;
        private long callDuration;
        private String callDirection;
        private Drawable calDirectionDrawable;

        public CallLogInfo(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public CallLogInfo(String phoneNumber, Date callDate) {
            this.phoneNumber = phoneNumber;
            this.callDate = callDate;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getCallType() {
            return callType;
        }

        public void setCallType(String callType) {
            this.callType = callType;
        }

        public Date getCallDate() {
            return callDate;
        }

        public void setCallDate(Date callDate) {
            this.callDate = callDate;
        }

        public long getCallDuration() {
            return callDuration;
        }

        public void setCallDuration(long callDuration) {
            this.callDuration = callDuration;
        }

        public String getCallDirection() {
            return callDirection;
        }

        public void setCallDirection(String callDirection) {
            this.callDirection = callDirection;
        }

        public Drawable getCalDirectionDrawable() {
            return calDirectionDrawable;
        }

        public void setCalDirectionDrawable(Drawable calDirectionDrawable) {
            this.calDirectionDrawable = calDirectionDrawable;
        }
    }

    private class CallLogsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<CallLogInfo> items;

        public CallLogsAdapter(Context context, List<CallLogInfo> items) {
            this.mInflater = LayoutInflater.from(context);
            this.items = items;
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
            CallLogViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_call_history, null);
                holder = new CallLogViewHolder();

                holder.imgContact = (ImageView)convertView.findViewById(R.id.img_contact);
                holder.txtContactName = (TextView) convertView.findViewById(R.id.txt_contact_name);
                holder.txtContactNumber = (TextView) convertView.findViewById(R.id.txt_contact_number);
                holder.txtCallTime = (TextView) convertView.findViewById(R.id.txt_call_time);
                holder.txtCallDuration = (TextView) convertView.findViewById(R.id.txt_call_duration);
                holder.imgDirection = (ImageView) convertView.findViewById(R.id.img_call_direction);

                convertView.setTag(holder);
            } else {
                holder = (CallLogViewHolder)convertView.getTag();
            }

            CallLogInfo callLogInfo = (CallLogInfo) getItem(position);
            //holder.imgContact.setImageDrawable(callLogInfo.getContactPhoto());
            holder.imgContact.setImageDrawable(getDrawable(R.drawable.ic_account_grey600_48dp));
            holder.txtContactName.setText(callLogInfo.getContactName());
            holder.txtContactNumber.setText(callLogInfo.getPhoneNumber());

            SimpleDateFormat dtFormat = new SimpleDateFormat("EEE, MM/dd/yyyy HH:mm:ss");
            String callTime = dtFormat.format(callLogInfo.getCallDate());
            holder.txtCallTime.setText(callTime);
            holder.txtCallDuration.setText(StringTool.formatDuration(callLogInfo.getCallDuration()));
            holder.imgDirection.setImageDrawable(callLogInfo.getCalDirectionDrawable());

            return convertView;
        }
    }

    private class CallLogViewHolder {
        ImageView imgContact;
        TextView txtContactName;
        TextView txtContactNumber;
        TextView txtCallTime;
        TextView txtCallDuration;
        ImageView imgDirection;
    }
}
