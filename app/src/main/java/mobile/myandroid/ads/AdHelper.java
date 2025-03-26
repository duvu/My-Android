package mobile.myandroid.ads;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import mobile.myandroid.R;
import mobile.myandroid.consent.ConsentManager;

/**
 * Helper class for managing ad loading and display with proper consent handling
 */
public class AdHelper {
    private static final String TAG = "AdHelper";
    
    private final Activity activity;
    private final ConsentManager consentManager;
    private InterstitialAd interstitialAd;
    private boolean isConsentDetermined = false;
    private boolean canShowAds = false;
    
    /**
     * Creates a new AdHelper instance
     * @param activity The activity where ads will be shown
     */
    public AdHelper(Activity activity) {
        this.activity = activity;
        this.consentManager = new ConsentManager(activity);
        
        // Initialize the Mobile Ads SDK
        MobileAds.initialize(activity, initializationStatus -> {
            // Use a completely null-safe approach to log the status
            try {
                Log.d(TAG, "MobileAds initialization completed");
                
                // Check if privacy options should be shown - do this separately to avoid 
                // any issues with the initializationStatus 
                try {
                    if (activity != null && ConsentManager.shouldShowPrivacyOptionsForm(activity)) {
                        ConsentManager.showPrivacyOptionsForm(activity);
                    }
                } catch (Exception e) {
                    // Don't let privacy options issues block initialization
                    Log.e(TAG, "Error checking privacy options: " + safeGetMessage(e));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in MobileAds initialization callback: " + safeGetMessage(e));
            }
        });
        
        // Gather consent before showing ads
        boolean isDebug = false; // Set to true for testing, false for production
        try {
            consentManager.gatherConsent(canShow -> {
                try {
                    // Update consent status
                    isConsentDetermined = true;
                    canShowAds = canShow;
                    
                    // Preload ad if consent is given
                    if (canShowAds) {
                        try {
                            preloadInterstitialAd();
                        } catch (Exception e) {
                            Log.e(TAG, "Error preloading ad after consent: " + safeGetMessage(e));
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in consent callback: " + safeGetMessage(e));
                }
            }, isDebug);
        } catch (Exception e) {
            Log.e(TAG, "Error gathering consent: " + safeGetMessage(e));
        }
    }
    
    /**
     * Load a banner ad into the specified AdView
     * @param adView The AdView to load the ad into
     */
    public void loadBannerAd(AdView adView) {
        if (!canShowAds || adView == null) {
            return;
        }
        
        try {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Error loading banner ad: " + errorMessage);
        }
    }
    
    /**
     * Preloads an interstitial ad for later display
     */
    private void preloadInterstitialAd() {
        if (!canShowAds) {
            return;
        }
        
        if (activity == null) {
            Log.e(TAG, "Cannot load ads: activity is null");
            return;
        }
        
        try {
            final AdRequest adRequest;
            try {
                adRequest = new AdRequest.Builder().build();
                if (adRequest == null) {
                    Log.e(TAG, "Failed to build AdRequest");
                    return;
                }
            } catch (Exception e) {
                Log.e(TAG, "Error building AdRequest: " + safeGetMessage(e));
                return;
            }
            
            final String adUnitId;
            try {
                if (activity != null && activity.getResources() != null) {
                    adUnitId = activity.getString(R.string.interstitial_ad_unit_id);
                    
                    // Check if the ad unit ID is valid
                    if (adUnitId == null || adUnitId.isEmpty()) {
                        Log.e(TAG, "Invalid ad unit ID (null or empty)");
                        return;
                    }
                } else {
                    Log.e(TAG, "Activity or resources are null");
                    return;
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to get ad unit ID from resources: " + safeGetMessage(e));
                return;
            }
            
            // Create a safe callback instance
            final InterstitialAdLoadCallback safeCallback = new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(InterstitialAd ad) {
                    try {
                        Log.d(TAG, "Interstitial ad loaded successfully");
                        if (ad != null) {
                            interstitialAd = ad;
                            setupInterstitialAdCallbacks();
                        } else {
                            Log.e(TAG, "Received null InterstitialAd in onAdLoaded");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error in onAdLoaded: " + safeGetMessage(e));
                    }
                }
                
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    try {
                        String errorMessage = "unknown error";
                        try {
                            if (loadAdError != null) {
                                String message = loadAdError.getMessage();
                                if (message != null && !message.isEmpty()) {
                                    errorMessage = message;
                                }
                                int errorCode = loadAdError.getCode();
                                errorMessage += " (code: " + errorCode + ")";
                            }
                        } catch (Exception innerE) {
                            Log.e(TAG, "Error getting error details: " + safeGetMessage(innerE));
                        }
                        
                        Log.e(TAG, "Interstitial ad failed to load: " + errorMessage);
                        interstitialAd = null;
                    } catch (Exception e) {
                        Log.e(TAG, "Error in onAdFailedToLoad: " + safeGetMessage(e));
                        interstitialAd = null;
                    }
                }
            };
            
            try {
                // Load the ad safely
                if (activity != null && adUnitId != null && !adUnitId.isEmpty() && adRequest != null) {
                    InterstitialAd.load(activity, adUnitId, adRequest, safeCallback);
                } else {
                    Log.e(TAG, "Cannot load ad: missing parameters");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error initiating ad load: " + safeGetMessage(e));
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error preloading interstitial ad: " + safeGetMessage(e));
        }
    }
    
    /**
     * Safe method to get message from an exception
     */
    private String safeGetMessage(Exception e) {
        if (e == null) {
            return "null exception";
        }
        
        try {
            String message = e.getMessage();
            return message != null ? message : e.getClass().getSimpleName();
        } catch (Exception inner) {
            return "cannot access exception message";
        }
    }
    
    /**
     * Sets up callbacks for the interstitial ad
     */
    private void setupInterstitialAdCallbacks() {
        if (interstitialAd == null) {
            Log.e(TAG, "Cannot setup callbacks: interstitialAd is null");
            return;
        }
        
        try {
            // Create a safe callback object
            FullScreenContentCallback safeCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    try {
                        Log.d(TAG, "Interstitial ad dismissed");
                        interstitialAd = null;
                        
                        // Safely preload another ad for next time
                        try {
                            preloadInterstitialAd();
                        } catch (Exception e) {
                            Log.e(TAG, "Error preloading next ad: " + safeGetMessage(e));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error in onAdDismissedFullScreenContent: " + safeGetMessage(e));
                        interstitialAd = null;
                    }
                }
                
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    try {
                        // Get error details safely
                        String errorMessage = "unknown error";
                        try {
                            if (adError != null) {
                                String message = adError.getMessage();
                                if (message != null && !message.isEmpty()) {
                                    errorMessage = message;
                                }
                                // Include error code for better diagnostics
                                int errorCode = adError.getCode();
                                errorMessage += " (code: " + errorCode + ")";
                            }
                        } catch (Exception innerE) {
                            Log.e(TAG, "Error getting ad error details: " + safeGetMessage(innerE));
                        }
                        
                        Log.e(TAG, "Interstitial ad failed to show: " + errorMessage);
                        interstitialAd = null;
                    } catch (Exception e) {
                        Log.e(TAG, "Error in onAdFailedToShowFullScreenContent: " + safeGetMessage(e));
                        interstitialAd = null;
                    }
                }
                
                @Override
                public void onAdShowedFullScreenContent() {
                    try {
                        Log.d(TAG, "Interstitial ad showed successfully");
                    } catch (Exception e) {
                        Log.e(TAG, "Error in onAdShowedFullScreenContent: " + safeGetMessage(e));
                    }
                }
            };
            
            // Set the callback safely
            try {
                if (interstitialAd != null) {
                    interstitialAd.setFullScreenContentCallback(safeCallback);
                } else {
                    Log.e(TAG, "InterstitialAd became null before setting callback");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error setting full screen content callback: " + safeGetMessage(e));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in setupInterstitialAdCallbacks: " + safeGetMessage(e));
        }
    }
    
    /**
     * Shows the preloaded interstitial ad if available
     * @return True if ad was shown, false otherwise
     */
    public boolean showInterstitialAd() {
        if (interstitialAd != null && canShowAds && activity != null) {
            try {
                interstitialAd.show(activity);
                return true;
            } catch (Exception e) {
                String errorMessage = "unknown error";
                if (e != null && e.getMessage() != null) {
                    errorMessage = e.getMessage();
                }
                Log.e(TAG, "Error showing interstitial ad: " + errorMessage);
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * Add a privacy policy menu item to the given menu
     * @param menu The menu to add the item to
     * @return True if the menu was modified
     */
    public boolean addPrivacyPolicyMenuItem(Menu menu) {
        if (menu == null) {
            Log.e(TAG, "Cannot add menu items: menu is null");
            return false;
        }
        
        try {
            menu.add(Menu.NONE, R.id.action_privacy_policy, Menu.NONE, R.string.privacy_policy)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                    
            // If consent has been determined, add privacy options menu item
            if (isConsentDetermined) {
                menu.add(Menu.NONE, R.id.action_privacy_options, Menu.NONE, R.string.privacy_options)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            }
            
            return true;
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Error adding menu items: " + errorMessage);
            return false;
        }
    }
    
    /**
     * Handle menu item selections
     * @param itemId The ID of the selected menu item
     * @return True if the item was handled, false otherwise
     */
    public boolean handleMenuItemSelected(int itemId) {
        if (activity == null) {
            Log.e(TAG, "Cannot handle menu selection: activity is null");
            return false;
        }
        
        try {
            if (itemId == R.id.action_privacy_options) {
                ConsentManager.showPrivacyOptionsForm(activity);
                return true;
            }
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Error handling menu item: " + errorMessage);
        }
        
        return false;
    }
} 