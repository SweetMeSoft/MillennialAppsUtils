package co.com.millennialapps.utils.firebase;

import android.app.Activity;

/**
 * Created by Erick Velasco on 12/12/2017.
 */

public class FLinksManager {

    private static FLinksManager fLinksManager;

    public static FLinksManager getInstance() {
        return fLinksManager == null ? fLinksManager = new FLinksManager() : fLinksManager;
    }

    public String generateLink(Activity activity, String serverName, String urlPass) {
        return "https://" + serverName + ".app.goo.gl/?" + "link=" + urlPass +
                "&apn=" + activity.getPackageName() +
                "&utm_source=AndroidApp";
    }

    public void generateShortLink() {
        //TODO generate Shortest link
    }
}
