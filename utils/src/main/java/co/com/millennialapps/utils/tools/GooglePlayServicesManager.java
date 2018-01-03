package co.com.millennialapps.utils.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import co.com.millennialapps.utils.R;
import co.com.millennialapps.utils.firebase.FAuthManager;

/**
 * Created by Erick Velasco on 2/1/2018.
 */

public class GooglePlayServicesManager {

    public static final int RESULT_GOOGLE_SIGN_IN = 813;
    private GoogleApiClient mGoogleApiClient;

    public void signInGoogle(final Activity activity, int defaultWebClientId) {
        initGoogleApiClient(activity, defaultWebClientId);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RESULT_GOOGLE_SIGN_IN);
    }

    public void logoutGoogle(Activity activity, final ResultCallback resultCallback, int defaultWebClientId) {
        initGoogleApiClient(activity, defaultWebClientId);
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                try {
                    FAuthManager.getInstance().signOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(resultCallback);
                    mGoogleApiClient.unregisterConnectionCallbacks(this);
                    //mGoogleApiClient.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
            }
        });
    }

    private void initGoogleApiClient(final Activity activity, int defaultWebClientId) {
        if (mGoogleApiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(defaultWebClientId))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage((FragmentActivity) activity, 1,
                            connectionResult -> Snackbar.make(activity.getWindow().getDecorView().getRootView(),
                                    R.string.msg_cannot_connect_api_google, Snackbar.LENGTH_LONG).show())
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }
}
