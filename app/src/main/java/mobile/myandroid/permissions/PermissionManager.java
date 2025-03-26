package mobile.myandroid.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import mobile.myandroid.R;

/**
 * Utility class to handle runtime permissions in a consistent way across the app
 */
public class PermissionManager {
    private static final String PREFS_NAME = "permission_prefs";
    private static final String PREF_PREFIX_KEY = "has_shown_rationale_";
    
    private final Context context;
    private ActivityResultLauncher<String> permissionLauncher;
    
    /**
     * Interface for permission results callback
     */
    public interface PermissionCallback {
        void onPermissionResult(boolean granted);
    }
    
    /**
     * Create permission manager for an Activity
     * @param activity The activity requesting permissions
     */
    public static PermissionManager withActivity(AppCompatActivity activity) {
        return new PermissionManager(activity);
    }
    
    /**
     * Create permission manager for a Fragment
     * @param fragment The fragment requesting permissions
     */
    public static PermissionManager withFragment(Fragment fragment) {
        return new PermissionManager(fragment.requireContext());
    }
    
    private PermissionManager(Context context) {
        this.context = context;
    }
    
    /**
     * Register a permission launcher - must be called in onCreate for Activities
     * @param activity The activity where the launcher will be registered
     * @param callback Callback for permission result
     */
    public void registerPermissionLauncher(AppCompatActivity activity, PermissionCallback callback) {
        permissionLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> callback.onPermissionResult(isGranted)
        );
    }
    
    /**
     * Register a permission launcher - must be called in onCreate for Fragments
     * @param fragment The fragment where the launcher will be registered
     * @param callback Callback for permission result
     */
    public void registerPermissionLauncher(Fragment fragment, PermissionCallback callback) {
        permissionLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> callback.onPermissionResult(isGranted)
        );
    }
    
    /**
     * Check if a permission is granted
     * @param permission The permission to check
     * @return True if permission is granted
     */
    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) 
                == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * Request a permission with appropriate rationale dialog if needed
     * @param activity The activity requesting the permission
     * @param permission The permission to request
     * @param rationaleTitle Title for the rationale dialog
     * @param rationaleMessage Message explaining why the permission is needed
     * @param callback Callback for permission result
     */
    public void requestPermission(Activity activity, String permission, 
                                 String rationaleTitle, String rationaleMessage,
                                 PermissionCallback callback) {
        // Check if permission is already granted
        if (hasPermission(permission)) {
            callback.onPermissionResult(true);
            return;
        }
        
        // Check if we should show rationale
        boolean shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity, permission);
        
        // Get if we've shown the rationale dialog before
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean hasShownRationaleBefore = prefs.getBoolean(PREF_PREFIX_KEY + permission, false);
        
        if (shouldShowRationale || (!shouldShowRationale && hasShownRationaleBefore)) {
            // User has denied permission at least once, show rationale dialog
            new AlertDialog.Builder(activity)
                    .setTitle(rationaleTitle)
                    .setMessage(rationaleMessage)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        // Mark that we've shown the rationale
                        prefs.edit().putBoolean(PREF_PREFIX_KEY + permission, true).apply();
                        
                        // Request the permission
                        if (permissionLauncher != null) {
                            permissionLauncher.launch(permission);
                        } else {
                            ActivityCompat.requestPermissions(activity, 
                                    new String[]{permission}, 0);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> 
                            callback.onPermissionResult(false))
                    .setCancelable(false)
                    .create()
                    .show();
        } else {
            // First time asking for permission or system won't show rationale
            // Mark that we're showing rationale for future reference
            prefs.edit().putBoolean(PREF_PREFIX_KEY + permission, true).apply();
            
            // Request the permission
            if (permissionLauncher != null) {
                permissionLauncher.launch(permission);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
            }
        }
    }
    
    /**
     * Show a dialog directing the user to app settings when they've permanently denied a permission
     * @param activity The activity context
     * @param title Dialog title
     * @param message Dialog message
     */
    public void showSettingsDialog(Activity activity, String title, String message) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.settings, (dialog, which) -> {
                    // Open app settings
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }
} 