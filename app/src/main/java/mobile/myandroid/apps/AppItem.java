package mobile.myandroid.apps;

import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * My-Android
 * mobile.myandroid.apps
 * Created by beou on 08/01/2016.
 */
public class AppItem implements Parcelable {
    private PackageInfo packageInfo;
    private String packageName;
    private String appName;
    private Date installedDate;

    public AppItem(String appName) {
        this.appName = appName;
    }

    public AppItem(String appName, Date installedDate) {
        this.appName = appName;
        this.installedDate = installedDate;
    }

    public static final Creator<AppItem> CREATOR = new Creator<AppItem>() {
        @Override
        public AppItem createFromParcel(Parcel in) {
            return new AppItem(in);
        }

        @Override
        public AppItem[] newArray(int size) {
            return new AppItem[size];
        }
    };

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
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

    public Date getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(Date installedDate) {
        this.installedDate = installedDate;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(packageInfo);
        dest.writeString(packageName);
        dest.writeString(appName);
        dest.writeValue(installedDate);
    }
    protected AppItem(Parcel in) {
        packageInfo = in.readParcelable(PackageInfo.class.getClassLoader());
        packageName = in.readString();
        appName = in.readString();
    }
}
