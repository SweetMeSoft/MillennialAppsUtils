package co.com.millennialapps.utils.tools;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import co.com.millennialapps.utils.R;


/**
 * Created by erick on 21/6/2017.
 */

public class Permissions {

    private static final int PERMISSIONS_REQUEST = 91832;

    public static final String GPS_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String GPS_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String MODIFY_SETTING = Manifest.permission.WRITE_SETTINGS;
    public static final String WRITE_EXTERNAL = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static boolean checkPermission(Fragment fragment, String permission) {
        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(new String[]{permission},
                    PERMISSIONS_REQUEST);
            return false;
        }
        return true;
    }

    public static boolean checkPermission(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{permission},
                        PERMISSIONS_REQUEST);
                return false;
            }
        }
        return true;
    }

    public static boolean checkModifySettings(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(activity)) {
                DialogManager.showConfirmationDialog(activity, R.string.permissions,
                        "En estos momentos no tenemos acceso a modificar las configuraciones de tu teléfono. " +
                                "¿Quieres ir al administrador y concedernos los permisos?",
                        (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                            intent.setData(Uri.parse("package:" + activity.getPackageName()));
                            activity.startActivity(intent);
                        }, (dialog, which) -> {
                        });
            }
        }
        return true;
    }
}
