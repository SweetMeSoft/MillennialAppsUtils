package co.com.millennialapps.utils.tools;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Erick Velasco on 12/12/2017.
 */

public class ShareContent {

    public static void shareText(Activity activity, String msg){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }
}
