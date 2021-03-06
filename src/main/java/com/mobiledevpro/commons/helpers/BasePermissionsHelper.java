package com.mobiledevpro.commons.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.mobiledevpro.commons.R;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


/**
 * Helper for runtime permissions.
 * <p>
 * Created by Dmitriy V. Chernysh
 *
 * https://instagr.am/mobiledevpro
 * #MobileDevPro
 */

public class BasePermissionsHelper {

    /**
     * Check local storage permission
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isStoragePermissionGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check camera permission
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isCameraPermissionGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check device Accounts permission
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isReadAccountsPermissionGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check permission for read contacts
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isContactsPermissionGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check SMS sending permission
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isSMSSendingPermissionGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /**
     * Check Record Audio permission
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isRecordAudioPermissionGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check permissions for taking video
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isCaptureVideoPermissionsGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check permissions for taking photo
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isCapturePhotoPermissionsGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }


    /**
     * Check Location permissions
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isLocationPermissionsGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check SMS receive and read permissions
     *
     * @param context Context
     * @return True - permission granted
     */
    public static boolean isReadSmsPermissionsGranted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return true;
        }
    }

    /**
     * Check several permissions
     *
     * @param context          Context
     * @param permissionsArray Permissions array
     * @return True - permission granted
     */
    public static boolean isPermissionsGranted(@NonNull Context context, String[] permissionsArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissionsArray) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Request Storage permission from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestStoragePermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestCode);
    }

    /**
     * Request Storage permission from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestStoragePermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                requestCode);
    }

    /**
     * Request device Accounts permission from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestReadAccountsPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{Manifest.permission.GET_ACCOUNTS},
                requestCode);
    }

    /**
     * Request device Accounts permission from activity
     *
     * @param activity    Fragment
     * @param requestCode Request code
     */
    public static void requestReadAccountsPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.GET_ACCOUNTS},
                requestCode);
    }

    /**
     * Request Camera permission from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestCameraPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{Manifest.permission.CAMERA},
                requestCode);
    }

    /**
     * Request Camera permission from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestCameraPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.CAMERA},
                requestCode);
    }

    /**
     * Request permissions for taking video (camera, microphone, storage) from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestCaptureVideoPermissions(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                requestCode);
    }

    /**
     * Request permissions for taking video (camera, microphone, storage) from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestCaptureVideoPermissions(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                requestCode);
    }

    /**
     * Request permissions for taking photo (camera, storage) from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestCapturePhotoPermissions(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                requestCode);
    }

    /**
     * Request permissions for taking photo (camera, storage) from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestCapturePhotoPermissions(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                requestCode);
    }

    /**
     * Request permission for reading user's contacts book from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestReadContactsPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{Manifest.permission.READ_CONTACTS},
                requestCode);
    }

    /**
     * Request permission for reading user's contacts book from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestReadContactsPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.READ_CONTACTS},
                requestCode);
    }

    /**
     * Request permission for SMS sending from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestSMSSendingPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                requestCode);
    }

    /**
     * Request permission for SMS sending from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestSMSSendingPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.SEND_SMS},
                requestCode);
    }

    /**
     * Request permission for Audio recording from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestRecordAudioPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                requestCode);
    }

    /**
     * Request permission for Audio recording from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestRecordAudioPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                requestCode);
    }

    /**
     * Request permission for location from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestLocationPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                requestCode);
    }


    /**
     * Request permission for location from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestLocationPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                requestCode);
    }

    /**
     * Request permission for read sms
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestReadSmsPermission(Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS
                },
                requestCode);
    }

    /**
     * Request permission for read sms
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestReadSmsPermission(Fragment fragment, int requestCode) {
        fragment.requestPermissions(
                new String[]{
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS
                },
                requestCode);
    }

    /**
     * Request several permissions  from activity
     *
     * @param activity    Activity
     * @param requestCode Request code
     */
    public static void requestPermissions(Activity activity, int requestCode, String[] permissionsArray) {
        ActivityCompat.requestPermissions(
                activity,
                permissionsArray,
                requestCode);
    }

    /**
     * Request several permissions from fragment
     *
     * @param fragment    Fragment
     * @param requestCode Request code
     */
    public static void requestPermissions(Fragment fragment, int requestCode, String[] permissionsArray) {
        fragment.requestPermissions(
                permissionsArray,
                requestCode);
    }

    /**
     * Show explanation dialog if user click "deny" on permission request
     *
     * @param activity     Activity
     * @param messageResId Message string id
     */
    public static void showExplanationDialog(@NonNull final Activity activity,
                                             @StringRes int messageResId,
                                             @StyleRes int styleResId) {

        createExplanationDialog(activity, messageResId, styleResId)
                .setNegativeButton(activity.getResources().getString(R.string.commons_button_deny), null)
                .show();
    }

    /**
     * Show explanation dialog if user click "deny" on permission request
     *
     * @param activity       Activity
     * @param messageResId   Message string id
     * @param cancelListener DialogInterface.OnCancelListener
     */
    public static void showExplanationDialog(@NonNull final Activity activity,
                                             @StringRes int messageResId,
                                             @StyleRes int styleResId,
                                             DialogInterface.OnClickListener cancelListener) {

        createExplanationDialog(activity, messageResId, styleResId)
                .setNegativeButton(activity.getResources().getString(R.string.commons_button_deny), cancelListener)
                .show();
    }

    private static AlertDialog.Builder createExplanationDialog(@NonNull final Activity activity,
                                                               @StringRes int messageResId,
                                                               @StyleRes int styleResId) {

        Resources resources = activity.getResources();

        return new AlertDialog.Builder(activity, styleResId != 0 ? styleResId : R.style.CommonAppTheme_AlertDialog)
                .setMessage(messageResId)
                .setPositiveButton(resources.getString(R.string.commons_button_allow), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // go to the current app settings
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        activity.startActivity(intent);
                    }
                })
                .setCancelable(false);
    }
}
