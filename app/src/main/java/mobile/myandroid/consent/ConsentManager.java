package mobile.myandroid.consent;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.FormError;
import com.google.android.ump.UserMessagingPlatform;

/**
 * Manager for handling user consent for ads and data collection
 * as required by GDPR, CCPA, and other privacy regulations.
 */
public class ConsentManager {
    private static final String TAG = "ConsentManager";
    
    // Debug device hash obtained from logcat when running the app with test device
    private static final String DEBUG_DEVICE_HASH = ""; // Insert actual debug device hash here if needed for testing
    
    private ConsentInformation consentInformation;
    private final Activity activity;
    
    public interface ConsentStatusListener {
        void onConsentStatusDetermined(boolean canShowAds);
    }
    
    public ConsentManager(Activity activity) {
        this.activity = activity;
    }
    
    /**
     * Gather user consent according to privacy regulations
     * @param listener Callback for when consent status is determined
     * @param isDebug Whether to use debug settings
     */
    public void gatherConsent(final ConsentStatusListener listener, boolean isDebug) {
        // Create a ConsentRequestParameters object
        ConsentRequestParameters.Builder paramsBuilder = new ConsentRequestParameters.Builder();
        
        // For debugging purposes only - remove in production build
        if (isDebug && DEBUG_DEVICE_HASH != null && !DEBUG_DEVICE_HASH.isEmpty()) {
            ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(activity)
                    .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                    .addTestDeviceHashedId(DEBUG_DEVICE_HASH)
                    .build();
            paramsBuilder.setConsentDebugSettings(debugSettings);
        }
        
        ConsentRequestParameters params = paramsBuilder.build();
        
        // Get the ConsentInformation singleton
        consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        
        // Reset for testing purposes - remove in production builds
        if (isDebug) {
            consentInformation.reset();
        }
        
        // Request consent information update
        consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                () -> {
                    // Consent info update successfully completed
                    if (consentInformation.isConsentFormAvailable()) {
                        loadAndShowConsentForm(listener);
                    } else {
                        // Consent form is not available, but we can move forward with personalized or
                        // non-personalized ads based on consent info status
                        processConsentStatus(listener);
                    }
                },
                formError -> {
                    // Consent info update failed
                    String errorMessage = "unknown error";
                    try {
                        if (formError != null) {
                            String message = formError.getMessage();
                            if (message != null && !message.isEmpty()) {
                                errorMessage = message;
                            }
                        }
                    } catch (Exception e) {
                        // Failsafe for any unexpected issue with the error message
                    }
                    Log.e(TAG, "Error updating consent info: " + errorMessage);
                    // We can proceed with showing ads if we have prior consent
                    processConsentStatus(listener);
                }
        );
    }
    
    /**
     * Process the consent status and call the listener
     * @param listener The listener to inform about consent status
     */
    private void processConsentStatus(ConsentStatusListener listener) {
        if (consentInformation == null) {
            // Safety fallback if consent information is null
            if (listener != null) {
                listener.onConsentStatusDetermined(false);
            }
            return;
        }
        
        boolean canShowAds = consentInformation.canRequestAds();
        if (listener != null) {
            listener.onConsentStatusDetermined(canShowAds);
        }
        
        // Log privacy options for debugging
        Log.d(TAG, "Can request ads: " + canShowAds);
    }
    
    /**
     * Load and show the consent form
     * @param listener The listener to inform about consent status after form completion
     */
    private void loadAndShowConsentForm(final ConsentStatusListener listener) {
        if (activity == null) {
            processConsentStatus(listener);
            return;
        }
        
        try {
            UserMessagingPlatform.loadConsentForm(
                    activity,
                    consentForm -> showForm(consentForm, listener),
                    formError -> {
                        // Error loading the consent form, so we proceed based on existing consent status
                        String errorMessage = "unknown error";
                        try {
                            if (formError != null) {
                                String message = formError.getMessage();
                                if (message != null && !message.isEmpty()) {
                                    errorMessage = message;
                                }
                            }
                        } catch (Exception e) {
                            // Failsafe for any unexpected issue with the error message
                        }
                        Log.e(TAG, "Error loading consent form: " + errorMessage);
                        processConsentStatus(listener);
                    }
            );
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Exception loading consent form: " + errorMessage);
            processConsentStatus(listener);
        }
    }
    
    /**
     * Show the consent form to the user
     * @param consentForm The consent form to show
     * @param listener The listener to inform about consent status after form completion
     */
    private void showForm(ConsentForm consentForm, final ConsentStatusListener listener) {
        if (consentForm == null || activity == null || consentInformation == null) {
            processConsentStatus(listener);
            return;
        }
        
        try {
            if (consentInformation.getConsentStatus() == ConsentInformation.ConsentStatus.REQUIRED) {
                consentForm.show(
                        activity,
                        formError -> {
                            // Error showing the consent form
                            if (formError != null) {
                                String errorMessage = "unknown error";
                                try {
                                    String message = formError.getMessage();
                                    if (message != null && !message.isEmpty()) {
                                        errorMessage = message;
                                    }
                                } catch (Exception e) {
                                    // Failsafe for any unexpected issue with the error message
                                }
                                Log.e(TAG, "Error showing consent form: " + errorMessage);
                            }
                            processConsentStatus(listener);
                        }
                );
            } else {
                // Consent not required or already obtained
                processConsentStatus(listener);
            }
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Exception showing consent form: " + errorMessage);
            processConsentStatus(listener);
        }
    }
    
    /**
     * Check if we need to show the privacy options again to the user
     * @param context The application context
     * @return True if privacy options should be shown
     */
    public static boolean shouldShowPrivacyOptionsForm(Context context) {
        if (context == null) {
            return false;
        }
        
        try {
            ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(context);
            if (consentInformation == null) {
                return false;
            }
            return consentInformation.getPrivacyOptionsRequirementStatus() == 
                    ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED;
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Error checking privacy options requirement: " + errorMessage);
            return false;
        }
    }
    
    /**
     * Show privacy options again if needed
     * @param activity The current activity
     */
    public static void showPrivacyOptionsForm(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Cannot show privacy options: activity is null");
            return;
        }
        
        try {
            UserMessagingPlatform.showPrivacyOptionsForm(
                    activity,
                    formError -> {
                        String errorMessage = "success";
                        try {
                            if (formError != null) {
                                String message = formError.getMessage();
                                if (message != null && !message.isEmpty()) {
                                    errorMessage = message;
                                } else {
                                    errorMessage = "unknown error";
                                }
                            }
                        } catch (Exception e) {
                            errorMessage = "exception processing error";
                        }
                        Log.d(TAG, "Privacy options form dismissed: " + errorMessage);
                    }
            );
        } catch (Exception e) {
            String errorMessage = "unknown error";
            if (e != null && e.getMessage() != null) {
                errorMessage = e.getMessage();
            }
            Log.e(TAG, "Error showing privacy options: " + errorMessage);
        }
    }
} 