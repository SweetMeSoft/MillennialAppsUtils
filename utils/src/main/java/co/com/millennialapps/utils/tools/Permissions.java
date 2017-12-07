package co.com.millennialapps.utils.tools;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;


/**
 * Created by erick on 21/6/2017.
 */

public class Permissions {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 91832;

    public static final String GPS_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String GPS_COARSE = Manifest.permission.ACCESS_FINE_LOCATION;

    public static boolean checkPermission(Fragment fragment, String permission) {
        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(new String[]{permission},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return false;
        }
        return true;
    }

    public static boolean checkPermission(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{permission},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return false;
            }
        }
        return true;
    }
}
